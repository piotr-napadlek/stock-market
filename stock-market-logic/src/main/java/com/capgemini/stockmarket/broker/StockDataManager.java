package com.capgemini.stockmarket.broker;


import java.util.List;

import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.common.DateInfo;
import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.StockPriceRecordTo;
import com.capgemini.stockmarket.dto.transactions.TxFromBO;

@Component
public class StockDataManager implements StockPriceInformer {

	private DateInfo currentDate;

	public void setCurrentDate() {
		
	}

	public void recordTransaction(TxFromBO transaction) {
		
	}

	public StockPriceRecordTo getCompanyStockPriceHistory() {
		return null;
	}

	public List<CompanyTo> getCompanies() {
		return null;
		
	}

	public DateInfo getCurrentDate() {
		return currentDate;
	}

	@Override
	public double getCurrentStockPrice(CompanyTo company) {
		// TODO Auto-generated method stub
		return 0;
	}

}
