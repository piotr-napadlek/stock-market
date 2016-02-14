package com.capgemini.stockmarket.broker.datamanager;

import java.util.Date;
import java.util.List;

import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.StockPriceRecordTo;
import com.capgemini.stockmarket.dto.transactions.TxFromBO;
import com.capgemini.stockmarket.dto.transactions.TxFromPlayer;

public interface StockDataManager extends StockPriceInformer {

	void recordTransaction(TxFromBO response, TxFromPlayer accept);

	List<StockPriceRecordTo> getCompanyStockPriceHistory(CompanyTo company);

	List<StockPriceRecordTo> getCompanyStockPriceHistory(CompanyTo company, Date fromDate);

	List<CompanyTo> getCompanies();

	Date getCurrentDate();

	double getCurrentStockPrice(CompanyTo company);

}