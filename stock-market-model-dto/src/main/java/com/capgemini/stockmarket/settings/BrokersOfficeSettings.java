package com.capgemini.stockmarket.settings;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.dto.Currency;

@Component(value = "defaultBrokersOfficeSettings")
@Scope("prototype")
public class BrokersOfficeSettings {

	private static final double DEFAULT_MIN_SELL_AVAILABILITY = 0.8d;
	private static final double DEFAULT_MAX_SELL_AVAILABILITY = 1.0d;
	private static final double DEFAULT_MIN_BUY_AVAILABILITY = 0.8d;
	private static final double DEFAULT_MAX_BUY_AVAILABILITY = 1.0d;
	private static final double DEFAULT_MIN_BUY_OFFER = 0.98d;
	private static final double DEFAULT_MAX_BUY_OFFER = 1.0d;
	private static final double DEFAULT_MIN_SELL_OFFER = 1.0d;
	private static final double DEFAULT_MAX_SELL_OFFER = 1.02d;
	private static final double DEFAULT_BO_PROVISION = 0.005d;
	private static final Pair<Currency, Double> DEFAULT_MIN_BO_PROVISION = Pair
			.of(Currency.PLN, 5d);

	private double minSellAvailability = DEFAULT_MIN_SELL_AVAILABILITY;
	private double maxSellAvailability = DEFAULT_MAX_SELL_AVAILABILITY;
	private double minBuyAvailability = DEFAULT_MIN_BUY_AVAILABILITY;
	private double maxBuyAvailability = DEFAULT_MAX_BUY_AVAILABILITY;
	private double minBuyOffer = DEFAULT_MIN_BUY_OFFER;
	private double maxBuyOffer = DEFAULT_MAX_BUY_OFFER;
	private double minSellOffer = DEFAULT_MIN_SELL_OFFER;
	private double maxSellOffer = DEFAULT_MAX_SELL_OFFER;
	private double boProvision = DEFAULT_BO_PROVISION;
	private Pair<Currency, Double> minProvision = DEFAULT_MIN_BO_PROVISION;

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

	public double getBoProvision() {
		return boProvision;
	}

	public void setBoProvision(double boProvision) {
		this.boProvision = boProvision;
	}

	public Pair<Currency, Double> getMinProvision() {
		return minProvision;
	}

	public void setMinProvision(Pair<Currency, Double> minProvision) {
		this.minProvision = minProvision;
	}

}
