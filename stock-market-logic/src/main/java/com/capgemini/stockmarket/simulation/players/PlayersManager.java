package com.capgemini.stockmarket.simulation.players;

import com.capgemini.stockmarket.player.StockMarketPlayer;
import com.capgemini.stockmarket.player.strategy.RequestCompositor;
import com.capgemini.stockmarket.settings.PlayerSettings;

public interface PlayersManager extends PlayersInfo {

	StockMarketPlayer addDefaultPlayer();
	StockMarketPlayer addPlayer(PlayerSettings settings, RequestCompositor strategy);
	StockMarketPlayer addPlayer(String playerName);
	boolean setPlayerStrategy(String playerName, RequestCompositor compositor);
	boolean setPlayerStrategy(String playerName, String strategyName);
	StockMarketPlayer setPlayerSettings(String playerName, PlayerSettings settings);
	boolean allPlayersAreReady();
	void reset();
	void activatePlayers();
}