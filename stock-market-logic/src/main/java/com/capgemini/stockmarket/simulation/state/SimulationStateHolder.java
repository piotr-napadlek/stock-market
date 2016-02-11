package com.capgemini.stockmarket.simulation.state;

import org.springframework.stereotype.Component;

@Component
public class SimulationStateHolder implements SimulationStateSetter {
	private SimulationState simulationState = SimulationState.NOT_INITIALIZED;

	@Override
	public SimulationState getSimulationState() {
		return simulationState;
	}

	@Override
	public boolean isSimulationInProgress() {
		return SimulationState.NEW_DAY.equals(simulationState)
				|| SimulationState.DAY_SIMULATING.equals(simulationState)
				|| SimulationState.DAY_FINISHED.equals(simulationState);
	}

	@Override
	public void setSimualtionState(SimulationState state) {
		this.simulationState = state;
	}
}
