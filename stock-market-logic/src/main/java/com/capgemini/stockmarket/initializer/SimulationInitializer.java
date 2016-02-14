package com.capgemini.stockmarket.initializer;

public interface SimulationInitializer {

	void initializeSimulation(String csv) throws Exception;
	void resetSimulation();
}