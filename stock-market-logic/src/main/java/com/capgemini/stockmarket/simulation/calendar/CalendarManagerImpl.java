package com.capgemini.stockmarket.simulation.calendar;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.common.DateAware;
import com.capgemini.stockmarket.simulation.players.PlayersStateInfo;
import com.capgemini.stockmarket.simulation.state.SimulationState;
import com.capgemini.stockmarket.simulation.state.SimulationStateSetter;

@Component
public class CalendarManagerImpl implements CalendarManager {
	private SimulationCalendar calendar;
	private SimulationStateSetter simulationStateSetter;
	private PlayersStateInfo playersStateInfo;

	private Set<DateAware> dateTimeListeners = new HashSet<>();
	private DateTime nextTargetDate;
	private int daySpan = 1;
	private DateTime startDate;
	private DateTime finishDate;

	@Inject
	public CalendarManagerImpl(SimulationCalendar calendar,
			SimulationStateSetter simulationStateSetter, PlayersStateInfo playerStateinfo) {
		this.calendar = calendar;
		this.simulationStateSetter = simulationStateSetter;
		this.playersStateInfo = playerStateinfo;
	}

	@Override
	public void nextDay() {
		simulationStateSetter.setSimualtionState(SimulationState.NEW_DAY);
		nextTargetDate = currentDate().plusDays(1);
		if (gameStateIsValid()) {
			daySpan = 1;
			moveCalendar();
		}
	}

	@Override
	public void processToDateTime(DateTime dateTime) {
		nextTargetDate = dateTime;
		if (gameStateIsValid()) {
			daySpan = 1;
			moveCalendar();
		}
	}

	@Override
	public void processToDateTimeSkipping(DateTime dateTime, int daySpan) {
		nextTargetDate = dateTime;
		if (gameStateIsValid()) {
			this.daySpan = daySpan;
			moveCalendar();
		}
	}

	@Override
	public void skipToDateTime(DateTime dateTime) {
		nextTargetDate = dateTime;
		if (gameStateIsValid()) {
			this.daySpan = Days.daysBetween(currentDate(), dateTime).getDays();
			moveCalendar();
		}
	}

	private boolean gameStateIsValid() {
		if (currentDate().isAfter(finishDate)) {
			simulationStateSetter.setSimualtionState(SimulationState.SIMULATION_FINISHED);
			return false;
		}
		if (simulationStateSetter.isSimulationInProgress() == false) {
			return false;
		}
		return true;
	}

	private boolean moveCalendar() {
		if (currentDate().isBefore(nextTargetDate)) {
			calendar.plusDays(daySpan);
			if (currentDate().getDayOfWeek() == DateTimeConstants.SATURDAY) {
				calendar.plusDays(2);
			}
			if (currentDate().getDayOfWeek() == DateTimeConstants.SUNDAY) {
				calendar.plusDays(1);
			}
			notifyListenersDateChanged();
			simulationStateSetter.setSimualtionState(SimulationState.DAY_FINISHED);
			return true;
		}
		return false;
	}

	@Override
	public boolean isCalendarSet() {
		return startDate != null && finishDate != null;
	}

	private DateTime currentDate() {
		return new DateTime(calendar.getCurrentDate());
	}

	@Override
	public DateTime getStartDate() {
		return startDate;
	}

	@Override
	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
		if (startDate != null) {
			this.calendar.setCurrentDate(startDate.minusDays(1));
		} else {
			this.calendar.setCurrentDate(null);
		}
	}

	@Override
	public DateTime getFinishDate() {
		return finishDate;
	}

	@Override
	public void setFinishDate(DateTime finishDate) {
		this.finishDate = finishDate;
	}

	@Override
	public void playerStateChanged() {
		if (playersStateInfo.allPlayersAreReady()) {
			moveCalendar();
		}
	}

	@Override
	public void addDateListener(DateAware listener) {
		this.dateTimeListeners.add(listener);
	}

	@Override
	public void addDateListeners(Collection<? extends DateAware> listeners) {
		this.dateTimeListeners.addAll(listeners);
	}

	private void notifyListenersDateChanged() {
		dateTimeListeners.forEach(listener -> listener.dateChanged());
	}

	@Override
	public void reset() {
		setFinishDate(null);
		setStartDate(null);
		simulationStateSetter.setSimualtionState(SimulationState.NOT_INITIALIZED);
	}

	@Override
	public DateTime getCurrentDate() {
		return new DateTime(calendar.getCurrentDate().getTime());
	}
}
