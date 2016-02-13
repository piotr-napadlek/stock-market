package com.capgemini.stockmarket.dto.transactions;

import java.util.HashMap;
import java.util.Map;

import com.capgemini.stockmarket.dto.CompanyTo;

public class TxAccept {
	private final Map<CompanyTo, NumPair<Integer, Double>> buyAccepts = new HashMap<>();
	private final Map<CompanyTo, NumPair<Integer, Double>> sellAccepts = new HashMap<>();

	public void addBuyAccept(CompanyTo company, Integer amount, Double price) {
		buyAccepts.put(company, NumPair.of(amount, price));
	}

	public void addSellAccept(CompanyTo company, Integer amount, Double price) {
		sellAccepts.put(company, NumPair.of(amount, price));
	}

	public NumPair<Integer, Double> getBuyAcceptFor(CompanyTo company) {
		return buyAccepts.getOrDefault(company, NumPair.of(0, 0d));
	}

	public NumPair<Integer, Double> getSellAcceptFor(CompanyTo company) {
		return sellAccepts.getOrDefault(company, NumPair.of(0, 0d));
	}

	public Map<CompanyTo, NumPair<Integer, Double>> getBuyAccepts() {
		return buyAccepts;
	}

	public Map<CompanyTo, NumPair<Integer, Double>> getSellAccepts() {
		return sellAccepts;
	}
}
