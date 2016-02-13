package com.capgemini.stockmarket.banking.account;


import java.util.Collection;

import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.transactions.StockInfo;

public interface BankAccountInfo {

	double getBalanceFor(Currency currency);

	Collection<Currency> getAvailableCurrencies();

	Collection<CompanyTo> getAvailableStockCompanies();

	Collection<StockInfo> getStockInfos(CompanyTo company);

}
