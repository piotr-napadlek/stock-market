package com.capgemini.stockmarket.player;

import com.capgemini.stockmarket.common.DateAware;
import com.capgemini.stockmarket.player.strategy.PlayerState;
import com.capgemini.stockmarket.player.strategy.RequestCompositor;
import com.capgemini.stockmarket.settings.PlayerSettings;

public interface StockMarketPlayer extends DateAware {

	void setCompositor(RequestCompositor compositor);

	StockMarketPlayer setSettings(PlayerSettings settings);

	PlayerState getState();

	void setState(PlayerState state);

	String getName();
	
	double estimateDefaultCurrencyWorth();

}