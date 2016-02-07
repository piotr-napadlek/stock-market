package com.capgemini.stockmarket.game;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.game.settings.GameSettings;

@Component
public class GameManager {
	@Inject
	private GameSettings settings;

	
	
	public GameSettings getSettings() {
		return settings;
	}

	public void setSettings(GameSettings settings) {
		this.settings = settings;
	}
}
