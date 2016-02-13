package com.capgemini.stockmarket.dto.transactions;

import java.util.HashMap;
import java.util.Map;

import com.capgemini.stockmarket.dto.CompanyTo;

public class TxOffer {
	private final Map<CompanyTo, NumPair<Integer, Double>> buyOffers = new HashMap<>();
	private final Map<CompanyTo, NumPair<Integer, Double>> sellOffers = new HashMap<>();

	public void addBuyOffer(CompanyTo company, Integer amount, Double price) {
		buyOffers.put(company, NumPair.of(amount, price));
	}

	public void addSellOffer(CompanyTo company, Integer amount, Double price) {
		sellOffers.put(company, NumPair.of(amount, price));
	}

	public NumPair<Integer, Double> getBuyOfferFor(CompanyTo company) {
		return buyOffers.getOrDefault(company, NumPair.of(0, 0d));
	}

	public NumPair<Integer, Double> getSellOfferFor(CompanyTo company) {
		return sellOffers.getOrDefault(company, NumPair.of(0, 0d));
	}

	public Map<CompanyTo, NumPair<Integer, Double>> getBuyOffers() {
		return buyOffers;
	}

	public Map<CompanyTo, NumPair<Integer, Double>> getSellOffers() {
		return sellOffers;
	}
}
