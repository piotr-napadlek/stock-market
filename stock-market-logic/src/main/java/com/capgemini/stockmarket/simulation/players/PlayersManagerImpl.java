package com.capgemini.stockmarket.simulation.players;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.common.IllegalRequestException;
import com.capgemini.stockmarket.player.StockMarketPlayer;
import com.capgemini.stockmarket.player.strategy.PlayerState;
import com.capgemini.stockmarket.player.strategy.RequestCompositor;
import com.capgemini.stockmarket.settings.PlayerSettings;
import com.capgemini.stockmarket.simulation.state.SimulationStateInfo;

@Component
public class PlayersManagerImpl implements PlayersManager {
	private Map<String, StockMarketPlayer> players = new HashMap<>();
	private ApplicationContext applicationContext;
	private SimulationStateInfo stateInfo;

	@Inject
	public PlayersManagerImpl(SimulationStateInfo stateInfo, ApplicationContext appContext) {
		this.stateInfo = stateInfo;
		this.applicationContext = appContext;
	}
	
	@Override
	public StockMarketPlayer addDefaultPlayer() {
		return addPlayer(null, null);
	}

	@Override
	public StockMarketPlayer addPlayer(PlayerSettings settings, RequestCompositor strategy) {
		validateSimulationState();
		StockMarketPlayer newPlayer = applicationContext.getBean(StockMarketPlayer.class);
		if (settings != null) {
			newPlayer.setSettings(settings);
		}
		if (strategy != null) {
			newPlayer.setCompositor(strategy);
		}
		if (players.containsKey(newPlayer.getName())) {
			throw new IllegalRequestException("Player with name " + newPlayer.getName()
					+ " already exists. Choose unique player name.");
		}
		players.put(newPlayer.getName(), newPlayer);
		return newPlayer;
	}
	
	@Override
	public StockMarketPlayer addPlayer(String playerName) {
		PlayerSettings settings = applicationContext.getBean(PlayerSettings.class);
		settings.setPlayerName(playerName);
		return addPlayer(settings, null);
	}

	@Override
	public boolean setPlayerStrategy(String playerName, RequestCompositor compositor) {
		if (compositor == null) {
			compositor = applicationContext.getBean(RequestCompositor.class);
		}
		getPlayer(playerName).setCompositor(compositor);
		return true;
	}

	@Override
	public boolean setPlayerStrategy(String playerName, String strategyName) {
		Map<String, RequestCompositor> strategies = applicationContext
				.getBeansOfType(RequestCompositor.class);
		if (strategies.containsKey(strategyName) == false) {
			throw new IllegalArgumentException("No such strategy exists!");
		}
		return setPlayerStrategy(playerName, strategies.get(strategyName));
	}

	@Override
	public StockMarketPlayer setPlayerSettings(String playerName, PlayerSettings settings) {
		validateSimulationState();
		if (settings == null) {
			settings = applicationContext.getBean(PlayerSettings.class);
		}
		StockMarketPlayer playerChanged = getPlayer(playerName).setSettings(settings);
		players.remove(playerName);
		players.put(playerChanged.getName(), playerChanged);
		return playerChanged;
	}

	@Override
	public StockMarketPlayer getPlayer(String playerName) {
		checkPlayerExistence(playerName);
		return players.get(playerName);
	}

	@Override
	public Collection<StockMarketPlayer> getAllPlayers() {
		return players.values();
	}

	@Override
	public boolean allPlayersAreReady() {
		return players.values().stream()
				.allMatch(player -> PlayerState.READY.equals(player.getState()));
	}

	private void checkPlayerExistence(String playerName) {
		if (players.containsKey(playerName) == false) {
			throw new IllegalArgumentException(
					"Player with a name " + playerName + " does not exist.");
		}
	}

	private void validateSimulationState() {
		if (stateInfo.isSimulationInProgress()) {
			throw new IllegalRequestException(
					"You cannot add new player during the simulation. "
							+ "Add one before or after.");
		}
	}

	@Override
	public void reset() {
		this.players.clear();
		addDefaultPlayer();
	}

	@Override
	public void activatePlayers() {
		players.values().forEach(player -> player.setState(PlayerState.READY));
	}
}
