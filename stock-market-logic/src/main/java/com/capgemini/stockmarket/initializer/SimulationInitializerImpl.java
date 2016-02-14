package com.capgemini.stockmarket.initializer;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.StockPriceRecordTo;
import com.capgemini.stockmarket.initializer.csv.CSVHandler;
import com.capgemini.stockmarket.service.CompanyService;
import com.capgemini.stockmarket.service.StockPriceRecordService;
import com.capgemini.stockmarket.simulation.StockSimulationManager;
import com.capgemini.stockmarket.simulation.state.SimulationState;

@Component
public class SimulationInitializerImpl implements SimulationInitializer {
	private CSVHandler csvHandler;
	private StockSimulationManager simulationManager;
	private StockPriceRecordService sprService;
	private CompanyService companyService;

	@Inject
	public SimulationInitializerImpl(CSVHandler csvHandler,
			StockSimulationManager simulationManager, StockPriceRecordService sprService,
			CompanyService companyService) {
		this.csvHandler = csvHandler;
		this.simulationManager = simulationManager;
		this.sprService = sprService;
		this.companyService = companyService;
	}

	@Override
	public void initializeSimulation(String csv) throws Exception {
		List<StockPriceRecordTo> sprTos = insertAndVerifyDBData(csv);
		calculateSimulationBoundaryDates(sprTos);
		simulationManager.setGameState(SimulationState.READY);
	}

	@Override
	public void resetSimulation() {
		sprService.deleteAll();
		simulationManager.resetSimulation();
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
			if (spr.getDate() == null || spr.getCompany() == null) {
				throw new IllegalArgumentException(
						"Something is wrong with input stock data. Missing some fields.");
			}
		});
		parsedSprs.forEach(spr -> {
			List<CompanyTo> companies = companyService
					.findCompaniesByName(spr.getCompany().getName());
			if (companies.size() == 0) {
				spr.setCompany(companyService.save(spr.getCompany()));
			} else if (companies.size() == 1) {
				spr.setCompany(companies.get(0));
			} else {
				throw new InitializationException("Something went wrong with data storage");
			}
		});
		List<StockPriceRecordTo> sprTos = sprService.saveAll(parsedSprs);
		return sprTos;
	}

}
