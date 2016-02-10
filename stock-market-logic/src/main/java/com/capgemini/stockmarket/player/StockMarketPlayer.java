package com.capgemini.stockmarket.player;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.banking.BankAccount;
import com.capgemini.stockmarket.broker.BrokersOfficeDesk;
import com.capgemini.stockmarket.common.DateAware;
import com.capgemini.stockmarket.common.DateInfo;
import com.capgemini.stockmarket.settings.PlayerSettings;
import com.capgemini.stockmarket.simulation.PlayersActionListener;

@Component
@Scope(scopeName = "prototype")
public class StockMarketPlayer implements DateAware {

	@Inject
	@Qualifier(value = "defaultStrategy")
	private RequestCompositor compositor;
	@Inject
	private BankAccount account;
	
	private BrokersOfficeDesk brokersOfficeDesk;
	private PlayerSettings settings;
	
	private PlayerState state;
	private DateInfo dateInfo;
	private List<PlayersActionListener> listeners = new ArrayList<>();

	public void setCompositor(RequestCompositor compositor) {

	}

	@Inject
	public StockMarketPlayer(BrokersOfficeDesk brokersOfficeDesk, PlayerSettings settings,
			PlayersActionListener listener) {
		this.brokersOfficeDesk = brokersOfficeDesk;
		this.settings = settings;
		listeners.add(listener);
	}

	public void setSettings(PlayerSettings settings) {
		this.settings = settings;
	}

	private final void doTryTransaction() {

	}

	public PlayerState getState() {
		return state;
	}

	@Override
	public void dateChanged() {
		this.state = PlayerState.THINKING;
		notifyStateChanged();
		// actual transaction logic
		doTryTransaction();
		this.state = PlayerState.READY;
		notifyStateChanged();
	}

	private void notifyStateChanged() {
		listeners.forEach(listener -> listener.notifyStateChanged());
	}
	
}
