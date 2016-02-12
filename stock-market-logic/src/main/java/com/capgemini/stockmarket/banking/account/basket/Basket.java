package com.capgemini.stockmarket.banking.account.basket;

import java.util.Collection;
import java.util.List;

import com.capgemini.stockmarket.broker.Stock;
import com.capgemini.stockmarket.broker.StockInfo;
import com.capgemini.stockmarket.dto.CompanyTo;

public interface Basket {

	List<StockInfo> getStockInfos(CompanyTo company);

	boolean putStocks(Collection<Stock> stock);

	List<Stock> extractStocks(Collection<StockInfo> stockInfos);
	
	List<Stock> extractStock(CompanyTo company, int amount);
	
	Collection<CompanyTo> getAvailableCompanies();
	
	public void clearBasket();

}