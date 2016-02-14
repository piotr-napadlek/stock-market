package com.capgemini.stockmarket.rest;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.stockmarket.initializer.SimulationInitializer;
import com.capgemini.stockmarket.initializer.csv.CSVHandler;
import com.capgemini.stockmarket.rest.simulationstate.SimulationInfo;
import com.capgemini.stockmarket.simulation.StockSimulationManager;

@RestController
@RequestMapping("/init")
public class InitializationRestController {

	private SimulationInitializer initializer;
	private StockSimulationManager manager;
	private CSVHandler csvHandler;

	@Inject
	public InitializationRestController(SimulationInitializer initializer,
			StockSimulationManager manager, CSVHandler csvHandler) {
		this.initializer = initializer;
		this.manager = manager;
		this.csvHandler = csvHandler;
	}

	@RequestMapping("/reset")
	public SimulationInfo reset() {
		initializer.resetSimulation();
		return new SimulationInfo(manager.getGameState().toString(), manager.getCurrentDate());
	}

	@RequestMapping(method = RequestMethod.POST)
	public SimulationInfo initialize(@RequestBody CsvPack csv) throws Exception {
		initializer.resetSimulation();
		if (csv.getDelimeter() != null) {
			csvHandler.setDelimeter(csv.getDelimeter());
		}
		if (csv.getHeaders() != null) {
			csvHandler.setHeaders(csv.getHeaders());
		}
		if (csv.getDateFormat() != null) {
			csvHandler.setDateFormat(csv.getDateFormat());
		}
		initializer.initializeSimulation(csv.getCsv());
		return new SimulationInfo(manager.getGameState().toString(), manager.getCurrentDate());
	}

}
