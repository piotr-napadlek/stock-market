package com.capgemini.stockmarket.banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.capgemini.stockmarket.broker.Stock;
import com.capgemini.stockmarket.broker.StockInfo;
import com.capgemini.stockmarket.dto.CompanyTo;

public class StockBasket {

	private Map<CompanyTo, Set<Stock>> shareMap;

	public List<StockInfo> getSharesInfo(CompanyTo company) {
		basketCompanyCheck(company);
		return new ArrayList<>(shareMap.get(company));
	}

	private void basketCompanyCheck(CompanyTo company) {
		if (shareMap.containsKey(company) == false) {
			throw new BankOperationException("No such company in basket: " + company.getName());
		}
	}

	public boolean putShares(CompanyTo company, Set<Stock> shares) {
		return false;

	}

	public Set<Stock> extractShares(List<StockInfo> shareInfo) {
		shareInfo.forEach(info -> basketCompanyCheck(info.company()));
		return null;
	}

}
