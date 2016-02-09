package com.capgemini.stockmarket.initializer.settings;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public final class GameSettings {
	private static final double DEFAULT_BASE_BALANCE = 10_000d;
	private static final String DEFAULT_CURRENCY = "PLN";
	private static final double DEFAULT_BO_PROVISION_RATE = 0.005d;
	private static final double DEFAULT_MINIMAL_BO_PROVISION = 5.0d;
	private static final String DEFAULT_PLAYER_NAME = "Player";
	
	private static final double DEFAULT_MIN_SELL_AVAILABILITY = 0.8d;
	private static final double DEFAULT_MAX_SELL_AVAILABILITY = 1.0d;
	private static final double DEFAULT_MIN_BUY_AVAILABILITY = 0.8d;
	private static final double DEFAULT_MAX_BUY_AVAILABILITY = 1.0d;
	private static final double DEFAULT_MIN_BUY_OFFER = 0.98d;
	private static final double DEFAULT_MAX_BUY_OFFER = 1.0d;
	private static final double DEFAULT_MIN_SELL_OFFER = 1.0d;
	private static final double DEFAULT_MAX_SELL_OFFER = 1.02d;
	
	private double baseBalance = DEFAULT_BASE_BALANCE;
	private String currency = DEFAULT_CURRENCY;
	private double boProvisionRate = DEFAULT_BO_PROVISION_RATE;
	private double minimalBoProvision = DEFAULT_MINIMAL_BO_PROVISION;
	private String playerName = DEFAULT_PLAYER_NAME;
	
	private double minSellAvailability = DEFAULT_MIN_SELL_AVAILABILITY;
	private double maxSellAvailability = DEFAULT_MAX_SELL_AVAILABILITY;
	private double minBuyAvailability = DEFAULT_MIN_BUY_AVAILABILITY;
	private double maxBuyAvailability = DEFAULT_MAX_BUY_AVAILABILITY;
	private double minBuyOffer = DEFAULT_MIN_BUY_OFFER;
	private double maxBuyOffer = DEFAULT_MAX_BUY_OFFER;
	private double minSellOffer = DEFAULT_MIN_SELL_OFFER;
	private double maxSellOffer = DEFAULT_MAX_SELL_OFFER;
	
	private final Map<String, Double> additionalBalances = new HashMap<String, Double>();

	public GameSettings() {
	}

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

	public double getMinSellAvailability() {
		return minSellAvailability;
	}

	public void setMinSellAvailability(double minSellAvailability) {
		this.minSellAvailability = minSellAvailability;
	}

	public double getMaxSellAvailability() {
		return maxSellAvailability;
	}

	public void setMaxSellAvailability(double maxSellAvailability) {
		this.maxSellAvailability = maxSellAvailability;
	}

	public double getMinBuyAvailability() {
		return minBuyAvailability;
	}

	public void setMinBuyAvailability(double minBuyAvailability) {
		this.minBuyAvailability = minBuyAvailability;
	}

	public double getMaxBuyAvailability() {
		return maxBuyAvailability;
	}

	public void setMaxBuyAvailability(double maxBuyAvailability) {
		this.maxBuyAvailability = maxBuyAvailability;
	}

	public double getMinBuyOffer() {
		return minBuyOffer;
	}

	public void setMinBuyOffer(double minBuyOffer) {
		this.minBuyOffer = minBuyOffer;
	}

	public double getMaxBuyOffer() {
		return maxBuyOffer;
	}

	public void setMaxBuyOffer(double maxBuyOffer) {
		this.maxBuyOffer = maxBuyOffer;
	}

	public double getMinSellOffer() {
		return minSellOffer;
	}

	public void setMinSellOffer(double minSellOffer) {
		this.minSellOffer = minSellOffer;
	}

	public double getMaxSellOffer() {
		return maxSellOffer;
	}

	public void setMaxSellOffer(double maxSellOffer) {
		this.maxSellOffer = maxSellOffer;
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
