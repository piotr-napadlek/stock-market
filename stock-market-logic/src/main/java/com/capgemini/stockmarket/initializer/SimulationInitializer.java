package com.capgemini.stockmarket.initializer;


import javax.inject.Inject;

import com.capgemini.stockmarket.initializer.csv.CSVHandler;
import com.capgemini.stockmarket.service.StockPriceRecordService;
import com.capgemini.stockmarket.simulation.StockSimulationManager;

public class SimulationInitializer {

	@Inject
	private CSVHandler csvHandler;
	@Inject
	private StockSimulationManager simulationManager;
	@Inject
	private StockPriceRecordService sprService;

	public void initializeStockData(String csv) throws Exception {
		sprService.saveAll(csvHandler.parseCSV(csv));
	}
}
