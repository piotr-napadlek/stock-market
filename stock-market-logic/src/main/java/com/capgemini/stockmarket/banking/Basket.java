package com.capgemini.stockmarket.banking;

import java.util.Collection;

import com.capgemini.stockmarket.broker.Stock;
import com.capgemini.stockmarket.broker.StockInfo;
import com.capgemini.stockmarket.dto.CompanyTo;

public interface Basket {

	Collection<StockInfo> getStockInfos(CompanyTo company);

	boolean putStocks(Collection<Stock> stock);

	Collection<Stock> extractStocks(Collection<StockInfo> stockInfos);
	
	Collection<CompanyTo> getAvailableCompanies();

}