package com.capgemini.stockmarket.dto.transactions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.Money;

public class TxFromPlayer {
	private final Map<CompanyTo, List<Stock>> soldStocks = new HashMap<>();
	private final Map<CompanyTo, Pair<Integer, Money>> moneyToBuyStocks = new HashMap<>();
	private Money transactionFee;

	public void addStockToSell(Stock stock) {
		if (soldStocks.containsKey(stock.getCompany()) == false) {
			soldStocks.put(stock.getCompany(), new ArrayList<>());
		}
		soldStocks.get(stock.getCompany()).add(stock);
	}

	public void addAllStocksToSell(Collection<Stock> stocks) {
		stocks.forEach(s -> addStockToSell(s));
	}

	public void putMoneyToBuy(CompanyTo company, Integer amount, Money money) {
		moneyToBuyStocks.put(company, Pair.of(amount, money));
	}

	public Map<CompanyTo, List<Stock>> getSoldStocks() {
		return soldStocks;
	}

	public Map<CompanyTo, Pair<Integer, Money>> getMoneyToBuyStocks() {
		return moneyToBuyStocks;
	}

	public List<Stock> getAllSoldStocks() {
		return soldStocks.values().stream().flatMap(stocks -> stocks.stream())
				.collect(Collectors.toList());
	}
	
	public void addTransactionFee(Money money) {
		this.transactionFee = money;
	}

	public Money getTransactionFee() {
		return transactionFee;
	}
}
