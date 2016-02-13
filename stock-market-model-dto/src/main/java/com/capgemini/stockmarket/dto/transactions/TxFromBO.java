package com.capgemini.stockmarket.dto.transactions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.capgemini.stockmarket.dto.Money;

public class TxFromBO {
	private final List<Money> moneyForSoldStocks = new ArrayList<>();
	private final List<Stock> stocksBought = new ArrayList<>();
	
	public void addMoneyForDisposal(Money money) {
		moneyForSoldStocks.add(money);
	}

	public void addAllMoneyForDisposal(Collection<Money> money) {
		moneyForSoldStocks.addAll(money);
	}
	
	public void addStockBought(Stock stock) {
		stocksBought.add(stock);
	}

	public void addAllStocskBought(Collection<Stock> stocks) {
		stocksBought.addAll(stocks);
	}
	
	public List<Money> getMoneyForSoldStocks() {
		return moneyForSoldStocks;
	}

	public List<Stock> getStocksBought() {
		return stocksBought;
	}
}
