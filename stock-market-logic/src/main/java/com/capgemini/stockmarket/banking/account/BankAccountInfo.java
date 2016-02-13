package com.capgemini.stockmarket.banking.account;


import java.util.Collection;
import java.util.List;

import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.transactions.StockInfo;

public interface BankAccountInfo {

	double getBalanceFor(Currency currency);

	Collection<Currency> getAvailableCurrencies();

	List<CompanyTo> getAvailableStockCompanies();

	Collection<StockInfo> getStockInfos(CompanyTo company);

}
