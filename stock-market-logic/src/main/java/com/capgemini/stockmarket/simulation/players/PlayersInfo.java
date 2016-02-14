package com.capgemini.stockmarket.simulation.players;

import java.util.Collection;

import com.capgemini.stockmarket.player.StockMarketPlayer;

public interface PlayersInfo extends PlayersStateInfo {

	StockMarketPlayer getPlayer(String playerName);
	Collection<StockMarketPlayer> getAllPlayers();

}