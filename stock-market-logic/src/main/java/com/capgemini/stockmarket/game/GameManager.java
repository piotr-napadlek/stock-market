package com.capgemini.stockmarket.game;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.game.csv.CSVHandler;
import com.capgemini.stockmarket.game.settings.GameSettings;
import com.capgemini.stockmarket.service.StockPriceRecordService;

@Component
public class GameManager {
	@Inject
	private GameSettings settings;
	@Inject
	private StockPriceRecordService sprService;
	@Inject
	private CSVHandler csvHandler;

	public boolean readCSVHistory(String csvFileContent) throws Exception {
		sprService.saveAll(csvHandler.parseCSV(csvFileContent));
		return true;
	}

	public GameSettings getSettings() {
		return settings;
	}
	
	public void setSettings(GameSettings settings) {
		this.settings = settings;
	}

	public CSVHandler getCsvHandler() {
		return csvHandler;
	}

	public void setCsvHandler(CSVHandler csvHandler) {
		this.csvHandler = csvHandler;
	}
}
