package com.capgemini.stockmarket.simulation;

import com.capgemini.stockmarket.common.DateAware;

public interface SimulationLogger extends DateAware {

	public void logState();
	
}
