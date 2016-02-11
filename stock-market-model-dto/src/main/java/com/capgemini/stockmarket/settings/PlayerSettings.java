package com.capgemini.stockmarket.settings;


import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component ("defaultPlayerSettings")
@Scope("prototype")
public class PlayerSettings {
	private static final double DEFAULT_BASE_BALANCE = 10_000d;
	private static final String DEFAULT_CURRENCY = "PLN";
	private static final double DEFAULT_BO_PROVISION_RATE = 0.005d;
	private static final double DEFAULT_MINIMAL_BO_PROVISION = 5.0d;
	private static final String DEFAULT_PLAYER_NAME = "DefaultPlayer";
	
	private double baseBalance = DEFAULT_BASE_BALANCE;
	private String currency = DEFAULT_CURRENCY;
	private double boProvisionRate = DEFAULT_BO_PROVISION_RATE;
	private double minimalBoProvision = DEFAULT_MINIMAL_BO_PROVISION;
	private String playerName = DEFAULT_PLAYER_NAME;

	private final Map<String, Double> additionalBalances = new HashMap<String, Double>();

	public double getBaseBalance() {
		return baseBalance;
	}

	public void setBaseBalance(double baseBalance) {
		this.baseBalance = baseBalance;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getBoProvisionRate() {
		return boProvisionRate;
	}

	public void setBoProvisionRate(double boProvisionRate) {
		this.boProvisionRate = boProvisionRate;
	}

	public double getMinimalBoProvision() {
		return minimalBoProvision;
	}

	public void setMinimalBoProvision(double minimalBoProvision) {
		this.minimalBoProvision = minimalBoProvision;
	}

	public Map<String, Double> getAdditionalBalances() {
		return additionalBalances;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
}
