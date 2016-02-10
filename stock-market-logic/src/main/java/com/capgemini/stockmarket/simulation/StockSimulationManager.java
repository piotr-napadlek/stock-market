package com.capgemini.stockmarket.simulation;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.broker.BrokersOffice;
import com.capgemini.stockmarket.broker.BrokersOfficeDesk;
import com.capgemini.stockmarket.common.DateAware;
import com.capgemini.stockmarket.player.RequestCompositor;
import com.capgemini.stockmarket.player.StockMarketPlayer;
import com.capgemini.stockmarket.settings.BrokersOfficeSettings;

@Component
public class StockSimulationManager implements ApplicationContextAware, PlayersActionListener {

	private List<DateAware> dateTimeListeners;
	private Set<StockMarketPlayer> players = new HashSet<>();
	
	private BrokersOffice defaultBO;
	@Inject
	private SimulationCalendar calendar;
	

	private Set<BrokersOffice> optionalBOs;
	
	private DateTime startDate;
	private DateTime finishDate;
	private GameState gameState = GameState.NOT_INITIALIZED;
	
	@Inject
	public StockSimulationManager(StockMarketPlayer defaultPlayer, BrokersOffice defaultBO) {
		this.defaultBO = defaultBO;
		players.add(defaultPlayer);
		dateTimeListeners.add(defaultBO);
		dateTimeListeners.add(defaultPlayer);
	}

	public void addBrokersOffice(BrokersOffice brokersOffice, BrokersOfficeSettings settings) {
		
	}

	public void setDefaultBrokersOfficeSettings(BrokersOfficeSettings settings) {
		
	}

	public void addPlayer(BrokersOfficeDesk office) {
		
	}

	public void addDefaultBOPlayer() {
		
	}

	public void addDefaultPlayer() {
		
	}

	public void nextDay() {
		if (GameState.SIMULATION_FINISHED.equals(gameState)) {
			throw new IllegalRequestException("Cannot proccess; game has finished.");
		}
		calendar.nextDay();
		dateTimeListeners.forEach(listener -> listener.dateChanged());
	}

	public void processToDateTime(DateTime DateTime) {
		
	}

	public void skipToDateTime(DateTime DateTime) {
		
	}

	public void setPlayerStrategy(StockMarketPlayer player, RequestCompositor compositor) {
		
	}

	public void resetSimulation() {
		
	}

	public void start() {
		
	}

	@Override
	public void notifyStateChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	
	public void setStartDate(DateTime DateTime) {
		this.startDate = DateTime;
	}

	public void setFinishDate(DateTime finishDateTime) {
		this.finishDate = finishDateTime;
	}

}
