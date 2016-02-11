package com.capgemini.stockmarket.simulation.state;

public interface SimulationStateInfo {
	SimulationState getSimulationState();
	boolean isSimulationInProgress();
}
