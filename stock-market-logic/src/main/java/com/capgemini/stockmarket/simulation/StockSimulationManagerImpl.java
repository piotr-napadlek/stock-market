package com.capgemini.stockmarket.simulation;

import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.broker.BrokersOffice;
import com.capgemini.stockmarket.common.IllegalRequestException;
import com.capgemini.stockmarket.player.StockMarketPlayer;
import com.capgemini.stockmarket.player.strategy.RequestCompositor;
import com.capgemini.stockmarket.settings.BrokersOfficeSettings;
import com.capgemini.stockmarket.settings.PlayerSettings;
import com.capgemini.stockmarket.simulation.calendar.CalendarManager;
import com.capgemini.stockmarket.simulation.logger.SimulationLogger;
import com.capgemini.stockmarket.simulation.players.PlayersManager;
import com.capgemini.stockmarket.simulation.state.SimulationState;
import com.capgemini.stockmarket.simulation.state.SimulationStateSetter;

@Component
public class StockSimulationManagerImpl implements StockSimulationManager {
	private static final Log LOG = LogFactory.getLog(StockSimulationManagerImpl.class);

	private BrokersOffice defaultBO;
	@SuppressWarnings("unused")
	private Set<BrokersOffice> optionalBOs;

	private PlayersManager playersManager;
	private CalendarManager calendarManager;
	private SimulationStateSetter stateHolder;

	@Inject
	public StockSimulationManagerImpl(PlayersManager playersManager,
			@Qualifier("defaultBrokersOffice") BrokersOffice defaultBO,
			CalendarManager calendarManager, SimulationStateSetter stateHolder,
			SimulationLogger logger) {

		this.defaultBO = defaultBO;
		this.calendarManager = calendarManager;
		this.playersManager = playersManager;
		this.stateHolder = stateHolder;
		playersManager.addDefaultPlayer();
		calendarManager.addDateListeners(playersManager.getAllPlayers());
		calendarManager.addDateListener(logger);
	}

	@Override
	public void addBrokersOffice(BrokersOffice brokersOffice, BrokersOfficeSettings settings) {
		throw new NotImplementedException(
				"Functionality of multiple Brokers Offices is not yet implemented");
	}

	@Override
	public StockMarketPlayer addPlayer(String playerName) {
		StockMarketPlayer newPlayer = playersManager.addPlayer(playerName);
		calendarManager.addDateListener(newPlayer);
		return newPlayer;
	}

	@Override
	public StockMarketPlayer addPlayer(PlayerSettings settings) {
		StockMarketPlayer newPlayer = playersManager.addPlayer(settings, null);
		calendarManager.addDateListener(newPlayer);
		return newPlayer;
	}

	@Override
	public StockMarketPlayer addPlayer(PlayerSettings settings, RequestCompositor strategy) {
		StockMarketPlayer newPlayer = playersManager.addPlayer(settings, strategy);
		calendarManager.addDateListener(newPlayer);
		return newPlayer;
	}

	@Override
	public StockMarketPlayer setPlayerSettings(String playerName, PlayerSettings settings) {
		return playersManager.setPlayerSettings(playerName, settings);
	}

	@Override
	public StockMarketPlayer setPlayerSettings(StockMarketPlayer player,
			PlayerSettings settings) {
		return playersManager.setPlayerSettings(player.getName(), settings);
	}

	@Override
	public boolean setPlayerStrategy(String playerName, RequestCompositor strategy) {
		return playersManager.setPlayerStrategy(playerName, strategy);
	}

	@Override
	public boolean setPlayerStrategy(String playerName, String strategyName) {
		return playersManager.setPlayerStrategy(playerName, strategyName);
	}

	@Override
	public void nextDay() {
		calendarManager.nextDay();
	}

	@Override
	public void skipToDate(DateTime date) {
		calendarManager.skipToDateTime(date);
	}

	@Override
	public void moveToDate(DateTime date) {
		calendarManager.processToDateTimeSkipping(date, 1);
	}

	@Override
	public void moveToDateSkipping(DateTime date, int daysSkip) {
		calendarManager.processToDateTimeSkipping(date, daysSkip);
	}

	@Override
	public void moveToEnd() {
		calendarManager.processToDateTime(calendarManager.getFinishDate().plusDays(1));
	}

	@Override
	public void resetSimulation() {
		calendarManager.reset();
		playersManager.reset();
		defaultBO.applySettings(new BrokersOfficeSettings());
	}

	@Override
	public void start() {
		if (calendarManager.isCalendarSet() == false) {
			throw new IllegalRequestException(
					"Game is not initialized yet. Set the start date and end date");
		}
		if (defaultBO == null) {
			throw new IllegalRequestException("Brokers office is not properly set.");
		}
		if (SimulationState.READY.equals(getGameState())) {
			setGameState(SimulationState.NEW_DAY);
			playersManager.activatePlayers();
		} else {
			throw new IllegalRequestException("Initialize stock database with CSV first.");
		}
	}

	@Override
	public SimulationState getGameState() {
		return stateHolder.getSimulationState();
	}

	@Override
	public void setGameState(SimulationState gameState) {
		this.stateHolder.setSimualtionState(gameState);
		LOG.info("GameState set to: " + gameState.toString());
	}

	@Override
	public void setStartDate(DateTime dateTime) {
		calendarManager.setStartDate(dateTime);
		LOG.info("Start date set to: " + dateTime.toString());
	}

	@Override
	public void setFinishDate(DateTime finishDateTime) {
		calendarManager.setFinishDate(finishDateTime);
		LOG.info("Finish date set to: " + finishDateTime.toString());
	}

	@Override
	public DateTime getCurrentDate() {
		return calendarManager.getCurrentDate();
	}

	@Override
	public Set<String> getAvailableStrategies() {
		return playersManager.getAvailableStrategies();
	}

	@Override
	public StockMarketPlayer addPlayer(String playerName, String strategy) {
		return playersManager.addPlayerWithStrategy(playerName, strategy);
	}

}
