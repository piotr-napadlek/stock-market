package com.capgemini.stockmarket.simulation.logger;

import com.capgemini.stockmarket.common.DateAware;

public interface SimulationLogger extends DateAware {

	public void logState();
	
}
