package com.capgemini.stockmarket.simulation;

import org.joda.time.DateTime;

import com.capgemini.stockmarket.broker.BrokersOffice;
import com.capgemini.stockmarket.player.StockMarketPlayer;
import com.capgemini.stockmarket.player.strategy.RequestCompositor;
import com.capgemini.stockmarket.settings.BrokersOfficeSettings;
import com.capgemini.stockmarket.settings.PlayerSettings;
import com.capgemini.stockmarket.simulation.state.SimulationState;

public interface StockSimulationManager {

	void addBrokersOffice(BrokersOffice brokersOffice, BrokersOfficeSettings settings);

	StockMarketPlayer addPlayer(String playerName);

	StockMarketPlayer addPlayer(PlayerSettings settings);

	StockMarketPlayer addPlayer(PlayerSettings settings, RequestCompositor strategy);

	StockMarketPlayer setPlayerSettings(String playerName, PlayerSettings settings);

	StockMarketPlayer setPlayerSettings(StockMarketPlayer player, PlayerSettings settings);

	boolean setPlayerStrategy(String playerName, RequestCompositor strategy);

	boolean setPlayerStrategy(String playerName, String strategyName);

	void nextDay();

	void skipToDate(DateTime date);

	void moveToDate(DateTime date);

	void moveToDateSkipping(DateTime date, int daysSkip);

	void moveToEnd();

	void resetSimulation();

	void start();

	SimulationState getGameState();

	void setGameState(SimulationState gameState);

	void setStartDate(DateTime dateTime);

	void setFinishDate(DateTime finishDateTime);

}