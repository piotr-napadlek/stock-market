package com.capgemini.stockmarket.dto.transactions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.Money;

public class TxFromBO {
	private final List<Money> moneyForSoldStocks = new ArrayList<>();
	private final Map<CompanyTo, List<Money>> moneyFor = new HashMap<>();
	private final List<Stock> stocksBought = new ArrayList<>();
	private String signature;
	
	public void addMoneyForDisposal(Money money) {
		moneyForSoldStocks.add(money);
	}
	
	public void addMoneyForDisposal(Money money, CompanyTo ofCompany) {
		moneyForSoldStocks.add(money);
		if(moneyFor.containsKey(ofCompany) == false) {
			moneyFor.put(ofCompany, new ArrayList<>());
		}
		moneyFor.get(ofCompany).add(money);
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

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public List<Money> getMoneyFor(CompanyTo company) {
		if (moneyFor.containsKey(company) == false) {
			return new ArrayList<>();
		}
		return moneyFor.get(company);
	}
}
