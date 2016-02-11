package com.capgemini.stockmarket.simulation.calendar;


import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
class SimulationCalendarImpl implements SimulationCalendar {

	private DateTime currentDate = new DateTime(0L);

	@Override
	public void setCurrentDate(DateTime date) {
		this.currentDate = date;
	}

	@Override
	public void nextDay() {
		currentDate = currentDate.plusDays(1);
	}

	@Override
	public void reset() {
		this.currentDate = new DateTime(0L);
	}

	@Override
	public Date getCurrentDate() {
		return currentDate.toDate();
	}

	@Override
	public void plusDays(int days) {
		currentDate = currentDate.plusDays(days);
	}
}
