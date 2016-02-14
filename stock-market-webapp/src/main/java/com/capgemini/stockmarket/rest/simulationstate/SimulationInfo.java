package com.capgemini.stockmarket.rest.simulationstate;

import org.joda.time.DateTime;

public class SimulationInfo {

	private String simulationState;
	private DateTime date;
	
	public SimulationInfo(String simulationState, DateTime date) {
		this.simulationState = simulationState;
		this.date = date;
	}

	public SimulationInfo() {
	}

	public String getSimulationState() {
		return simulationState;
	}

	public void setSimulationState(String simulationState) {
		this.simulationState = simulationState;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

}
