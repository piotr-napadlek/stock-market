package com.capgemini.stockmarket.banking;


import java.util.List;
import java.util.Set;

import com.capgemini.stockmarket.broker.StockInfo;
import com.capgemini.stockmarket.dto.CompanyTo;

public interface BankAccountInfo {

	double getBalanceFor(Currency currency);

	List<Currency> getAvailableCurrencies();

	List<CompanyTo> getAvailableSharesCompanies();

	Set<StockInfo> getShareInfos(CompanyTo company);

}
