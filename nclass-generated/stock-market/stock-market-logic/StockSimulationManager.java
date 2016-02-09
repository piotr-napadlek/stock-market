package stock-market.stock-market-logic;

import java.io.*;
import java.util.*;

public class StockSimulationManager implements ApplicationContextAware, PlayersActionListener {

	private PriorityQueue<DateAware> dateListeners;
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

}
