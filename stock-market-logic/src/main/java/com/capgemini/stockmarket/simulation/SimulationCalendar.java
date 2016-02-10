package com.capgemini.stockmarket.simulation;

import org.joda.time.DateTime;

import com.capgemini.stockmarket.common.DateInfo;

interface SimulationCalendar extends DateInfo {

	void nextDay();
	void reset();
	void setCurrentDate(DateTime date);
}