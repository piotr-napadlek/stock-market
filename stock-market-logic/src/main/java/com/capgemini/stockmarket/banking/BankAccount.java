package com.capgemini.stockmarket.banking;


import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.broker.Stock;
import com.capgemini.stockmarket.broker.StockInfo;
import com.capgemini.stockmarket.dto.CompanyTo;

@Component
@Scope("prototype")
public class BankAccount implements BankAccountInfo {

	private StockBasket stockBasket;
	private Set<CurrencyBalance> balances;

	public boolean putMoney(CurrencyAmount money) {
		return false;
		
	}

	public boolean putShares(Set<Stock> shares) {
		return false;
		
	}

	public CurrencyAmount extractMoney(Currency currency, double amount) {
		return null;
		
	}

	public Set<Stock> extractShares(List<StockInfo> shareInfos) {
		return null;
		
	}

	@Override
	public double getBalanceFor(Currency currency) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Currency> getAvailableCurrencies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CompanyTo> getAvailableSharesCompanies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<StockInfo> getShareInfos(CompanyTo company) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void clearAccount() {
		
	}
}


