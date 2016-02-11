package com.capgemini.stockmarket.simulation;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import static org.mockito.Matchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.capgemini.stockmarket.common.DateAware;

@RunWith(MockitoJUnitRunner.class)
public class CalendarManagerTest {
	@InjectMocks
	private CalendarManager manager;
	@Spy
	private SimulationCalendar calendar = new SimulationCalendarImpl();
	@Mock
	private SimulationStateInfo stateInfo;
	@Mock
	private PlayersStateInfo playerStateInfo;

	@Before
	public void setUp() {
		when(playerStateInfo.allPlayersAreReady()).thenReturn(true);
		when(stateInfo.getSimulationState()).thenReturn(SimulationState.READY);
		when(stateInfo.isSimulationInProgress()).thenReturn(true);
		DateAware listener = Mockito.mock(DateAware.class);
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				manager.playerStateChanged();
				return null;
			}
		}).when(listener).dateChanged();
	}

	@Test
	public void testShouldProcessToNextDay() {
		// given
		DateTime startDate = DateTime.parse("20150101", DateTimeFormat.forPattern("yyyyMMdd"));
		DateTime finishDate = DateTime.parse("20150105", DateTimeFormat.forPattern("yyyyMMdd"));
		manager.setStartDate(startDate);
		manager.setFinishDate(finishDate);
		// when
		manager.nextDay();
		// then
		assertEquals(startDate.toDate(), calendar.getCurrentDate());
		// when
		manager.nextDay();
		manager.nextDay();
		manager.nextDay();
		manager.nextDay();
		// then
		assertEquals(finishDate.toDate(), calendar.getCurrentDate());
	}

}
