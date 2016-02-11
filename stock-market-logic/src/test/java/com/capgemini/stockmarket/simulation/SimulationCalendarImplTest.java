package com.capgemini.stockmarket.simulation;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;


public class SimulationCalendarImplTest {

	private SimulationCalendar calendar = new SimulationCalendarImpl();
	
	@Test
	public void shiuldAddOneDay() {
		// given 
		DateTime date = new DateTime(2000, 1, 1, 0, 0, 0);
		DateTime datePlusOne = date.plusDays(1);
		// when
		calendar.setCurrentDate(date);
		calendar.nextDay();
		// then
		Assert.assertEquals(datePlusOne.toDate(), calendar.getCurrentDate());
	}

	@Test
	public void shouldReset() {
		// given 
		DateTime date = new DateTime(2000, 1, 1, 0, 0, 0);
		DateTime datePlusOne = date.plusDays(1);
		// when
		calendar.setCurrentDate(date);
		calendar.nextDay();
		// then
		Assert.assertEquals(datePlusOne.toDate(), calendar.getCurrentDate());
		// when
		calendar.reset();
		//
		Assert.assertEquals(0L, calendar.getCurrentDate().getTime());
	}
}
