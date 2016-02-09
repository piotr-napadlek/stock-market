package com.capgemini.stockmarket.player;


import com.capgemini.stockmarket.banking.BankAccount;
import com.capgemini.stockmarket.broker.BrokersOfficeDesk;
import com.capgemini.stockmarket.common.DateAware;
import com.capgemini.stockmarket.common.DateInfo;
import com.capgemini.stockmarket.settings.PlayerSettings;
import com.capgemini.stockmarket.simulation.PlayersActionListener;

public class StockMarketPlayer implements DateAware {

	private BrokersOfficeDesk brokersOfficeDesk;
	private RequestCompositor compositor;
	private BankAccount account;
	private PlayerSettings settings;
	private PlayerState state;
	private DateInfo dateInfo;
	private PlayersActionListener listener;

	public void setCompositor(RequestCompositor compositor) {
		
	}

	public StockMarketPlayer(BrokersOfficeDesk brokersOfficeDesk, PlayerSettings settings) {
		
	}

	public void applySettings(PlayerSettings settings) {
		
	}

	private final void doTryTransaction() {
		
	}

	public PlayerState getState() {
		return null;
	}

	@Override
	public void notifyDateChanged() {
		// TODO Auto-generated method stub
		
	}

}
