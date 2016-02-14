package com.capgemini.stockmarket.broker.datamanager;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.broker.logger.TransactionLogger;
import com.capgemini.stockmarket.common.DateInfo;
import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.StockPriceRecordTo;
import com.capgemini.stockmarket.dto.transactions.TxFromBO;
import com.capgemini.stockmarket.dto.transactions.TxFromPlayer;
import com.capgemini.stockmarket.service.CompanyService;
import com.capgemini.stockmarket.service.StockPriceRecordService;

@Component
public class StockDataManagerImpl implements StockDataManager {

	private DateInfo currentDate;
	private StockPriceRecordService sprService;
	private CompanyService companyService;
	private TransactionLogger txLogger;

	@Inject
	public StockDataManagerImpl(DateInfo currentDate, StockPriceRecordService sprService,
			CompanyService companyService, TransactionLogger txLogger) {
		this.currentDate = currentDate;
		this.sprService = sprService;
		this.companyService = companyService;
		this.txLogger = txLogger;
	}

	@Override
	public void recordTransaction(TxFromBO response, TxFromPlayer accept) {
		// TODO pnapadle: insert db persistance here
		txLogger.logTransaction(response, accept);
	}

	@Override
	public List<StockPriceRecordTo> getCompanyStockPriceHistory(CompanyTo company) {
		return sprService.findByCompanyId(company.getId());
	}

	@Override
	public List<StockPriceRecordTo> getCompanyStockPriceHistory(CompanyTo company,
			Date fromDate) {
		return sprService.findByCompanyNameBetweenDates(company.getName(), fromDate,
				getCurrentDate());
	}

	@Override
	public List<CompanyTo> getCompanies() {
		return companyService.findAllCompanies();
	}

	@Override
	public Date getCurrentDate() {
		return currentDate.getCurrentDate();
	}

	@Override
	public double getCurrentStockPrice(CompanyTo company) {
		return sprService.findByCompanyNameAndExactDay(company.getName(), getCurrentDate())
				.getPrice();
	}

}
