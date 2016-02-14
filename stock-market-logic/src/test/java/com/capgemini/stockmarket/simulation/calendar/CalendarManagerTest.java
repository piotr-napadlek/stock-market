package com.capgemini.stockmarket.simulation.calendar;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.capgemini.stockmarket.common.DateAware;
import com.capgemini.stockmarket.simulation.calendar.CalendarManagerImpl;
import com.capgemini.stockmarket.simulation.calendar.SimulationCalendar;
import com.capgemini.stockmarket.simulation.calendar.SimulationCalendarImpl;
import com.capgemini.stockmarket.simulation.players.PlayersStateInfo;
import com.capgemini.stockmarket.simulation.state.SimulationState;
import com.capgemini.stockmarket.simulation.state.SimulationStateSetter;

@RunWith(MockitoJUnitRunner.class)
public class CalendarManagerTest {
	@InjectMocks
	private CalendarManagerImpl manager;
	@Spy
	private SimulationCalendar calendar = new SimulationCalendarImpl();
	@Mock
	private SimulationStateSetter stateInfo;
	@Mock
	private PlayersStateInfo playerStateInfo;
	@Mock
	private DateAware listener;

	@Before
	public void setUp() {
		when(playerStateInfo.allPlayersAreReady()).thenReturn(true);
		when(stateInfo.getSimulationState()).thenReturn(SimulationState.READY);
		when(stateInfo.isSimulationInProgress()).thenReturn(true);
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				manager.playerStateChanged();
				return null;
			}
		}).when(listener).dateChanged();
		manager.addDateListener(listener);
	}

	@Test
	public void testShouldProcessToNextDay() {
		// given
		DateTime startDate = DateTime.parse("20150101", DateTimeFormat.forPattern("yyyyMMdd"));
		DateTime finishDate = DateTime.parse("20150106", DateTimeFormat.forPattern("yyyyMMdd"));
		manager.setStartDate(startDate);
		manager.setFinishDate(finishDate);
		// when
		manager.nextDay();
		// then
		assertEquals(startDate.toDate(), calendar.getCurrentDate());
		verify(calendar, times(1)).plusDays(1);
		// when
		manager.nextDay();
		manager.nextDay();
		manager.nextDay();
		// then
		assertEquals(finishDate.toDate(), calendar.getCurrentDate());
		verify(calendar, times(4)).plusDays(1);
		verify(calendar, times(1)).plusDays(2); //on friday
		verify(listener, times(4)).dateChanged();
	}
	
	@Test
	public void testShouldProcessToDateSkipping() {
		// given
		DateTime startDate = DateTime.parse("20150101", DateTimeFormat.forPattern("yyyyMMdd"));
		DateTime finishDate = DateTime.parse("20150107", DateTimeFormat.forPattern("yyyyMMdd"));
		manager.setStartDate(startDate);
		manager.setFinishDate(finishDate);
		// when
		manager.processToDateTimeSkipping(finishDate, 2);
		// then
		assertEquals(finishDate.toDate(), calendar.getCurrentDate());
		verify(calendar, times(3)).plusDays(2);
		verify(listener, times(3)).dateChanged();
	}
}
