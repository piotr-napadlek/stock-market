package com.capgemini.stockmarket.simulation.calendar;

import org.joda.time.DateTime;

import com.capgemini.stockmarket.common.DateInfo;

interface SimulationCalendar extends DateInfo {

	void nextDay();
	void plusDays(int days);
	void reset();
	void setCurrentDate(DateTime date);
}