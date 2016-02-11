package com.capgemini.stockmarket.banking;


import java.util.List;
import java.util.Map;
import java.util.Set;

import com.capgemini.stockmarket.broker.Stock;
import com.capgemini.stockmarket.broker.StockInfo;
import com.capgemini.stockmarket.dto.CompanyTo;

public class StockBasket {

	private Map<CompanyTo, List<Stock>> shareMap;

	public List<StockInfo> getSharesInfo(CompanyTo company) {
		return null;
		
	}

	public boolean putShares(CompanyTo company, Set<Stock> shares) {
		return false;
		
	}

	public Set<Stock> extractShares(List<StockInfo> shareInfo) {
		return null;
		
	}

}
