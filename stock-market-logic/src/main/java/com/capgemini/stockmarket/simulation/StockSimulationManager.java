package com.capgemini.stockmarket.simulation;


import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.capgemini.stockmarket.broker.BrokersOffice;
import com.capgemini.stockmarket.broker.BrokersOfficeDesk;
import com.capgemini.stockmarket.common.DateAware;
import com.capgemini.stockmarket.player.RequestCompositor;
import com.capgemini.stockmarket.player.StockMarketPlayer;
import com.capgemini.stockmarket.settings.BrokersOfficeSettings;

public class StockSimulationManager implements ApplicationContextAware, PlayersActionListener {

	private List<DateAware> dateListeners;
	private Set<StockMarketPlayer> players;
	private BrokersOffice defaultBO;
	private Set<BrokersOffice> optionalBOs;
	private Date startDate;
	private Date currentDate;
	private SimulationCalendar calendar;

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
		
	}

	public void processToDate(Date date) {
		
	}

	public void skipToDate(Date date) {
		
	}

	public void setPlayerStrategy(StockMarketPlayer player, RequestCompositor compositor) {
		
	}

	public void resetSimulation() {
		
	}

	public void start() {
		
	}

	void setStartDate(Date date) {
		
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

}
