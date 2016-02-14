package com.capgemini.stockmarket.simulation.calendar;

import java.util.Collection;

import org.joda.time.DateTime;

import com.capgemini.stockmarket.common.DateAware;

public interface CalendarManager extends PlayersActionListener {

	void nextDay();

	void processToDateTime(DateTime dateTime);

	void processToDateTimeSkipping(DateTime dateTime, int daySpan);

	void skipToDateTime(DateTime dateTime);

	boolean isCalendarSet();

	DateTime getStartDate();

	void setStartDate(DateTime startDate);

	DateTime getFinishDate();

	void setFinishDate(DateTime finishDate);

	void playerStateChanged();

	void addDateListener(DateAware listener);

	void addDateListeners(Collection<? extends DateAware> listeners);

	void reset();

}