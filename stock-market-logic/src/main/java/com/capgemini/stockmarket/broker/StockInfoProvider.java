package com.capgemini.stockmarket.broker;


import java.util.Date;
import java.util.List;

import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.StockPriceRecordTo;

public interface StockInfoProvider {

	List<CompanyTo> getStockCompanies();

	List<StockPriceRecordTo> getSharePriceHistoryFor(CompanyTo company, Date fromDate);

	List<StockPriceRecordTo> getSharePriceHistoryFor(CompanyTo company);
	
	Double getTodaysPriceFor(CompanyTo company);

}
