package com.capgemini.stockmarket.banking.account.basket;

import java.util.Collection;
import java.util.List;

import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.transactions.Stock;
import com.capgemini.stockmarket.dto.transactions.StockInfo;

public interface Basket {

	List<StockInfo> getStockInfos(CompanyTo company);

	boolean putStocks(Collection<Stock> stock);

	List<Stock> extractStocks(Collection<StockInfo> stockInfos);
	
	List<Stock> extractStock(CompanyTo company, int amount);
	
	List<CompanyTo> getAvailableCompanies();
	
	public void clearBasket();

}