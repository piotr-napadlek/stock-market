package com.capgemini.stockmarket.simulation.calendar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.common.DateAware;
import com.capgemini.stockmarket.common.IllegalRequestException;
import com.capgemini.stockmarket.simulation.PlayersStateInfo;
import com.capgemini.stockmarket.simulation.state.SimulationState;
import com.capgemini.stockmarket.simulation.state.SimulationStateSetter;

@Component
public class CalendarManager implements PlayersActionListener {
	private List<DateAware> dateTimeListeners = new ArrayList<>();
	private SimulationCalendar calendar;
	private SimulationStateSetter simulationStateSetter;
	private PlayersStateInfo playersStateInfo;

	private DateTime nextTargetDate;
	private int daySpan = 1;
	private DateTime startDate;
	private DateTime finishDate;

	@Inject
	public CalendarManager(SimulationCalendar calendar,
			SimulationStateSetter simulationStateSetter, PlayersStateInfo playerStateinfo) {
		this.calendar = calendar;
		this.simulationStateSetter = simulationStateSetter;
		this.playersStateInfo = playerStateinfo;
	}

	public void nextDay() {
		nextTargetDate = currentDate().plusDays(1);
		checkGameStateValidity();
		daySpan = 1;
		moveCalendar();
	}

	public void processToDateTime(DateTime dateTime) {
		nextTargetDate = dateTime;
		checkGameStateValidity();
		daySpan = 1;
		moveCalendar();
	}

	public void processToDateTimeSkipping(DateTime dateTime, int daySpan) {
		nextTargetDate = dateTime;
		checkGameStateValidity();
		this.daySpan = daySpan;
		moveCalendar();
	}

	public void skipToDateTime(DateTime dateTime) {
		nextTargetDate = dateTime;
		checkGameStateValidity();
		this.daySpan = Days.daysBetween(currentDate(), dateTime).getDays();
		moveCalendar();
	}

	private void checkGameStateValidity() {
		if (currentDate().isAfter(finishDate)) {
			simulationStateSetter.setSimualtionState(SimulationState.SIMULATION_FINISHED);
			throw new IllegalRequestException("Simulation has finished.");
		}
		if (simulationStateSetter.isSimulationInProgress() == false) {
			throw new IllegalRequestException("Cannot proccess; game state is "
					+ simulationStateSetter.getSimulationState().toString());
		}
	}

	private boolean moveCalendar() {
		if (currentDate().isBefore(nextTargetDate)) {
			calendar.plusDays(daySpan);
			notifyListenersDateChanged();
			return true;
		}
		return false;
	}

	public boolean isCalendarSet() {
		return startDate != null && finishDate != null;
	}

	private DateTime currentDate() {
		return new DateTime(calendar.getCurrentDate());
	}

	public DateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
		if (startDate != null) {
			this.calendar.setCurrentDate(startDate.minusDays(1));
		} else {
			this.calendar.setCurrentDate(null);
		}
	}

	public DateTime getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(DateTime finishDate) {
		this.finishDate = finishDate;
	}

	@Override
	public void playerStateChanged() {
		if (playersStateInfo.allPlayersAreReady()) {
			moveCalendar();
		}
	}

	public void addDateListener(DateAware listener) {
		this.dateTimeListeners.add(listener);
	}

	public void addDateListeners(Collection<? extends DateAware> listeners) {
		this.dateTimeListeners.addAll(listeners);
	}

	private void notifyListenersDateChanged() {
		dateTimeListeners.forEach(listener -> listener.dateChanged());
	}

	public void reset() {
		setFinishDate(null);
		setStartDate(null);
		simulationStateSetter.setSimualtionState(SimulationState.NOT_INITIALIZED);
	}
}