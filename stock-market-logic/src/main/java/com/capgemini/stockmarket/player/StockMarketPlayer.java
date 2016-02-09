package com.capgemini.stockmarket.player;


import java.io.*;
import java.util.*;

public class StockMarketPlayer implements DateAware {

	private BrokersOfficeDesk brokersOfficeDesk;
	private RequestCompositor compositor;
	private Wallet wallet;
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
		
	}

}
