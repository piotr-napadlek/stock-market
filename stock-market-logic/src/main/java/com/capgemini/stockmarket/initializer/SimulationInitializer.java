package com.capgemini.stockmarket.initializer;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.dto.StockPriceRecordTo;
import com.capgemini.stockmarket.initializer.csv.CSVHandler;
import com.capgemini.stockmarket.service.StockPriceRecordService;
import com.capgemini.stockmarket.simulation.SimulationState;
import com.capgemini.stockmarket.simulation.StockSimulationManager;

@Component
public class SimulationInitializer {
	@Inject
	private CSVHandler csvHandler;
	@Inject
	private StockSimulationManager simulationManager;
	@Inject
	private StockPriceRecordService sprService;

	public void initializeGame(String csv) throws Exception {
		List<StockPriceRecordTo> sprTos = insertAndVerifyDBData(csv);
		calculateSimulationBoundaryDates(sprTos);
		simulationManager.setGameState(SimulationState.DB_READY);
	}

	public void reset() {
		sprService.deleteAll();
		simulationManager.setStartDate(null);
		simulationManager.setFinishDate(null);
		simulationManager.setGameState(SimulationState.NOT_INITIALIZED);
	}
	
	private void calculateSimulationBoundaryDates(List<StockPriceRecordTo> sprTos) {
		Date startDate = sprTos.stream()
				.min((spr, sprOther) -> spr.getDate().compareTo(sprOther.getDate()))
				.map(spr -> spr.getDate()).orElse(new Date(0L));
		Date endDate = sprTos.stream()
				.max((spr, sprOther) -> spr.getDate().compareTo(sprOther.getDate()))
				.map(spr -> spr.getDate()).orElse(new Date());
		simulationManager.setStartDate(new DateTime(startDate.getTime()));
		simulationManager.setFinishDate(new DateTime(endDate.getTime()));
	}

	private List<StockPriceRecordTo> insertAndVerifyDBData(String csv) throws Exception {
		List<StockPriceRecordTo> parsedSprs = csvHandler.parseCSV(csv);
		parsedSprs.forEach(spr -> {
			if (spr.getDate() == null || spr.getCompany() == null
					|| spr.getCompany().getId() == null) {
				throw new IllegalArgumentException(
						"Something is wrong with input stock data. Missing some fields.");
			}
		});
		List<StockPriceRecordTo> sprTos = sprService.saveAll(parsedSprs);
		return sprTos;
	}
	
}
