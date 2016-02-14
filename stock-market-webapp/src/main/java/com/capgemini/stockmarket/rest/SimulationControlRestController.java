package com.capgemini.stockmarket.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.stockmarket.rest.simulationstate.SimulationInfo;
import com.capgemini.stockmarket.simulation.StockSimulationManager;

@RestController
@RequestMapping("/simulation")
public class SimulationControlRestController {

	private StockSimulationManager manager;

	public SimulationControlRestController(StockSimulationManager manager) {
		this.manager = manager;
	}

	public SimulationControlRestController() {
	}

	@RequestMapping("/start")
	public SimulationInfo startSimulation() {
		manager.start();
		return new SimulationInfo(manager.getGameState().toString(), manager.getCurrentDate());
	}

	@RequestMapping("/add")
	public void addPlayer(
			@RequestParam(name = "playerName", required = true) String playerName) {
		manager.addPlayer(playerName);
	}

}
