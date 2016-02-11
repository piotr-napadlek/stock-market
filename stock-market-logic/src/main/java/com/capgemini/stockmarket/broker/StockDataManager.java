package com.capgemini.stockmarket.broker;


import java.util.List;

import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.common.DateInfo;
import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.StockPriceRecordTo;
import com.capgemini.stockmarket.dto.TransactionObjectTo;

@Component
public class StockDataManager {

	private DateInfo currentDate;

	public void setCurrentDate() {
		
	}

	public void recordTransaction(TransactionObjectTo<Stock> transaction) {
		
	}

	public StockPriceRecordTo getCompanyStockPriceHistory() {
		return null;
	}

	public List<CompanyTo> getCompanies() {
		return null;
		
	}

}
