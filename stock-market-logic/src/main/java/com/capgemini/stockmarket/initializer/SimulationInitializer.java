package com.capgemini.stockmarket.initializer;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.dto.StockPriceRecordTo;
import com.capgemini.stockmarket.initializer.csv.CSVHandler;
import com.capgemini.stockmarket.service.StockPriceRecordService;
import com.capgemini.stockmarket.simulation.GameState;
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
		simulationManager.setGameState(GameState.DB_READY);
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
		List<StockPriceRecordTo> sprTos = sprService.saveAll(csvHandler.parseCSV(csv));
		sprTos.forEach(spr -> {
			if (spr.getCompany().getId() == null) {
				throw new InitializationException(
						"Something went wrong with Database storage.");
			}
		});
		return sprTos;
	}
}
