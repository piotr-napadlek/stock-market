package com.capgemini.stockmarket.simulation;

import org.springframework.stereotype.Component;

@Component
public class SimulationStateHolder implements SimulationStateInfo {
	private SimulationState simulationState = SimulationState.NOT_INITIALIZED;

	@Override
	public SimulationState getSimulationState() {
		return simulationState;
	}

	@Override
	public boolean isSimulationInProgress() {
		return SimulationState.NEW_DAY.equals(simulationState)
				|| SimulationState.READY.equals(simulationState)
				|| SimulationState.DAY_FINISHED.equals(simulationState);
	}

	public void setSimualtionState(SimulationState state) {
		this.simulationState = state;
	}
}
