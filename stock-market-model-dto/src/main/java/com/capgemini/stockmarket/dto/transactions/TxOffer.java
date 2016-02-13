package com.capgemini.stockmarket.dto.transactions;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.Currency;

public class TxOffer {
	private final Map<CompanyTo, NumPair<Integer, Double>> buyOffers = new HashMap<>();
	private final Map<CompanyTo, NumPair<Integer, Double>> sellOffers = new HashMap<>();

	public void addBuyAccept(CompanyTo company, Integer amount, Double price) {
		buyOffers.put(company, NumPair.of(amount, price));
	}

	public void addSellAccept(CompanyTo company, Integer amount, Double price) {
		sellOffers.put(company, NumPair.of(amount, price));
	}

	public NumPair<Integer, Double> getBuyAcceptFor(CompanyTo company) {
		return buyOffers.getOrDefault(company, NumPair.of(0, 0d));
	}

	public NumPair<Integer, Double> getSellAcceptOfferFor(CompanyTo company) {
		return sellOffers.getOrDefault(company, NumPair.of(0, 0d));
	}

	public Map<CompanyTo, NumPair<Integer, Double>> getBuyAccepts() {
		return buyOffers;
	}

	public Map<CompanyTo, NumPair<Integer, Double>> getSellAccepts() {
		return sellOffers;
	}
}
