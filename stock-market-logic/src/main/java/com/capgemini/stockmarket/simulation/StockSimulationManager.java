package com.capgemini.stockmarket.simulation;

import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.NotImplementedException;
import org.joda.time.DateTime;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.broker.BrokersOffice;
import com.capgemini.stockmarket.common.IllegalRequestException;
import com.capgemini.stockmarket.player.RequestCompositor;
import com.capgemini.stockmarket.player.StockMarketPlayer;
import com.capgemini.stockmarket.settings.BrokersOfficeSettings;
import com.capgemini.stockmarket.settings.PlayerSettings;

@Component
public class StockSimulationManager implements ApplicationContextAware {

	

	private BrokersOffice defaultBO;
	@SuppressWarnings("unused")
	private Set<BrokersOffice> optionalBOs;

	private PlayersManager playersManager;
	private CalendarManager calendarManager;
	private SimulationStateHolder stateHolder;


	private ApplicationContext applicationContext;

	@Inject
	public StockSimulationManager(
			PlayersManager playersManager,
			@Qualifier("defaultBrokersOffice") BrokersOffice defaultBO,
			CalendarManager calendarManager, 
			SimulationStateHolder stateHolder) {
		
		this.defaultBO = defaultBO;
		this.calendarManager = calendarManager;
		this.playersManager = playersManager;
		this.stateHolder = stateHolder;
		playersManager.addDefaultPlayer();
		calendarManager.addDateListener(defaultBO);
		calendarManager.addDateListeners(playersManager.getAllPlayers());
	}

	public void addBrokersOffice(BrokersOffice brokersOffice, BrokersOfficeSettings settings) {
		throw new NotImplementedException(
				"Functionality of multiple Brokers Offices is not yet implemented");
	}

	public StockMarketPlayer addPlayer(String playerName) {
		return playersManager.addPlayer(playerName);
	}

	public StockMarketPlayer addPlayer(PlayerSettings settings) {
		return playersManager.addPlayer(settings, null);
	}

	public StockMarketPlayer addPlayer(PlayerSettings settings, RequestCompositor strategy) {
		return playersManager.addPlayer(settings, strategy);
	}

	public StockMarketPlayer setPlayerSettings(String playerName, PlayerSettings settings) {
		return playersManager.setPlayerSettings(playerName, settings);
	}

	public StockMarketPlayer setPlayerSettings(StockMarketPlayer player,
			PlayerSettings settings) {
		return playersManager.setPlayerSettings(player.getName(), settings);
	}

	public boolean setPlayerStrategy(String playerName, RequestCompositor strategy) {
		return playersManager.setPlayerStrategy(playerName, strategy);
	}

	public boolean setPlayerStrategy(String playerName, String strategyName) {
		return playersManager.setPlayerStrategy(playerName, strategyName);
	}

	public void nextDay() {
		calendarManager.nextDay();
	}

	public void skipToDate(DateTime date) {
		calendarManager.skipToDateTime(date);
	}

	public void moveToDate(DateTime date) {
		calendarManager.processToDateTime(date, 1);
	}

	public void moveToDateSkipping(DateTime date, int daysSkip) {
		calendarManager.processToDateTime(date, daysSkip);
	}

	public void moveToEnd() {

	}

	private void finalizeSimulation() {

	}

	public void resetSimulation() {

	}

	public void start() {
		if (calendarManager.isCalendarSet()) {
			throw new IllegalRequestException(
					"Game is not initialized yet. Set the start date and end date");
		}
		if (defaultBO == null) {
			throw new IllegalRequestException("Brokers office is not properly set.");
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public SimulationState getGameState() {
		return stateHolder.getSimulationState();
	}

	public void setGameState(SimulationState gameState) {
		this.stateHolder.setSimualtionState(gameState);
	}

	public void setStartDate(DateTime dateTime) {
		calendarManager.setStartDate(dateTime);
	}

	public void setFinishDate(DateTime finishDateTime) {
		calendarManager.setFinishDate(finishDateTime);
	}

}
