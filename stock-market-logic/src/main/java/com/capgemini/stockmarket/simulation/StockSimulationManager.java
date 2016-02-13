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
import com.capgemini.stockmarket.common.DateAware;
import com.capgemini.stockmarket.common.DateInfo;
import com.capgemini.stockmarket.common.IllegalRequestException;
import com.capgemini.stockmarket.player.StockMarketPlayer;
import com.capgemini.stockmarket.player.strategy.RequestCompositor;
import com.capgemini.stockmarket.settings.BrokersOfficeSettings;
import com.capgemini.stockmarket.settings.PlayerSettings;
import com.capgemini.stockmarket.simulation.calendar.CalendarManager;
import com.capgemini.stockmarket.simulation.state.SimulationState;
import com.capgemini.stockmarket.simulation.state.SimulationStateSetter;

@Component
public class StockSimulationManager implements DateAware {
	private static final Log LOG = LogFactory.getLog(StockSimulationManager.class);

	private BrokersOffice defaultBO;
	@SuppressWarnings("unused")
	private Set<BrokersOffice> optionalBOs;

	private PlayersManager playersManager;
	private CalendarManager calendarManager;
	private SimulationStateSetter stateHolder;
	private DateInfo dateInfo;

	@Inject
	public StockSimulationManager(PlayersManager playersManager,
			@Qualifier("defaultBrokersOffice") BrokersOffice defaultBO,
			CalendarManager calendarManager, SimulationStateSetter stateHolder,
			DateInfo dateInfo) {

		this.defaultBO = defaultBO;
		this.calendarManager = calendarManager;
		this.playersManager = playersManager;
		this.stateHolder = stateHolder;
		playersManager.addDefaultPlayer();
		calendarManager.addDateListeners(playersManager.getAllPlayers());
		calendarManager.addDateListener(this);
		this.dateInfo = dateInfo;
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
		calendarManager.processToDateTimeSkipping(date, 1);
	}

	public void moveToDateSkipping(DateTime date, int daysSkip) {
		calendarManager.processToDateTimeSkipping(date, daysSkip);
	}

	public void moveToEnd() {
		calendarManager.processToDateTime(calendarManager.getFinishDate().plusDays(1));
	}

	public void resetSimulation() {
		calendarManager.reset();
		playersManager.reset();
		defaultBO.applySettings(new BrokersOfficeSettings());
	}

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

	public SimulationState getGameState() {
		return stateHolder.getSimulationState();
	}

	public void setGameState(SimulationState gameState) {
		this.stateHolder.setSimualtionState(gameState);
		LOG.info("GameState set to: " + gameState.toString());
	}

	public void setStartDate(DateTime dateTime) {
		calendarManager.setStartDate(dateTime);
		LOG.info("Start date set to: " + dateTime.toString());
	}

	public void setFinishDate(DateTime finishDateTime) {
		calendarManager.setFinishDate(finishDateTime);
		LOG.info("Finish date set to: " + finishDateTime.toString());
	}

	@Override
	public void dateChanged() {
		LOG.info("Current day is: " + dateInfo.getCurrentDate().toString());
		StringBuilder builder = new StringBuilder("Current stock prices are: \n");
		defaultBO.getStockCompanies().forEach(comp -> {
			builder.append(comp.getName() + ": " + defaultBO.getTodaysPriceFor(comp) + ", ");
		});
		LOG.info(builder.toString());
		LOG.info("Obecny stan graczy: \n");
		playersManager.getAllPlayers().forEach(player -> LOG.info(player.toString()));
	}

}
