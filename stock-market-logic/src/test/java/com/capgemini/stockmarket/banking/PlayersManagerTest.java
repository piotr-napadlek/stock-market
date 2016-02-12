package com.capgemini.stockmarket.banking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import com.capgemini.stockmarket.common.IllegalRequestException;
import com.capgemini.stockmarket.player.StockMarketPlayer;
import com.capgemini.stockmarket.player.StockMarketPlayerImpl;
import com.capgemini.stockmarket.player.strategy.RequestCompositor;
import com.capgemini.stockmarket.settings.PlayerSettings;
import com.capgemini.stockmarket.simulation.PlayersManager;
import com.capgemini.stockmarket.simulation.state.SimulationState;
import com.capgemini.stockmarket.simulation.state.SimulationStateInfo;

@RunWith(MockitoJUnitRunner.class)
public class PlayersManagerTest {
	@InjectMocks
	private PlayersManager manager;
	@Mock
	private SimulationStateInfo stateInfo;
	@Mock
	private ApplicationContext context;

	@Before
	public void setUp() {
		when(stateInfo.getSimulationState()).thenReturn(SimulationState.READY);
		when(stateInfo.isSimulationInProgress()).thenReturn(false);
		when(context.getBean(StockMarketPlayer.class)).thenReturn(
				new StockMarketPlayerImpl(null, new PlayerSettings(), null, null, null, null));
		when(context.getBean(PlayerSettings.class)).thenReturn(new PlayerSettings());
		when(context.getBean(RequestCompositor.class)).thenReturn(null);
		when(context.getBeansOfType(RequestCompositor.class)).thenReturn(new HashMap<>());
	}

	@Test
	public void testShouldAddDefaultPlayer() {
		// given when
		manager.addDefaultPlayer();
		// then
		assertEquals(1, manager.getAllPlayers().size());
		assertNotNull(manager.getPlayer("DefaultPlayer"));
	}

	@Test
	public void testShouldAddDefaultPlayerAndAnotherOne() {
		// given when
		manager.addDefaultPlayer();
		// then
		assertEquals(1, manager.getAllPlayers().size());
		// when
		manager.addPlayer("second player");
		// then
		assertEquals(2, manager.getAllPlayers().size());
		assertNotNull(manager.getPlayer("second player"));
	}

	@Test
	public void testShouldChangePlayerName() {
		PlayerSettings settings = new PlayerSettings();
		// given
		settings.setPlayerName("NewName");
		// when
		manager.addDefaultPlayer();
		// then
		assertEquals(1, manager.getAllPlayers().size());
		// when
		manager.setPlayerSettings("DefaultPlayer", settings);
		// then
		assertNotNull(manager.getPlayer("NewName"));
		try {
			manager.getPlayer("DefaultPlayer");
			fail();
		} catch (Throwable e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShouldAddDefaultPlayerAndAnotherOneAndThrowWhenWrongNameIsGiven() {
		// given when
		manager.addDefaultPlayer();
		// then
		assertEquals(1, manager.getAllPlayers().size());
		// when
		manager.addPlayer("second player");
		// then
		assertEquals(2, manager.getAllPlayers().size());
		assertNotNull(manager.getPlayer("second"));
	}

	@Test(expected = IllegalRequestException.class)
	public void shouldThrowOnPlayerOperationsWhenGameIsInProgress() {
		// given
		manager.addDefaultPlayer();
		when(stateInfo.isSimulationInProgress()).thenReturn(true);
		when(stateInfo.getSimulationState()).thenReturn(SimulationState.NEW_DAY);
		// when
		manager.addPlayer("i cannot be added");
	}

	@Test(expected = IllegalRequestException.class)
	public void shouldThrowOnPlayerSettingsWhenGameInProgress() {
		// given
		manager.addDefaultPlayer();
		when(stateInfo.isSimulationInProgress()).thenReturn(true);
		when(stateInfo.getSimulationState()).thenReturn(SimulationState.NEW_DAY);
		// when
		manager.setPlayerSettings("DefaultPlayer", new PlayerSettings());
	}

}
