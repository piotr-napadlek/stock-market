package com.capgemini.stockmarket.broker;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.StockPriceRecordTo;
import com.capgemini.stockmarket.dto.TransactionAcceptTo;
import com.capgemini.stockmarket.dto.TransactionOfferTo;
import com.capgemini.stockmarket.dto.TransactionRequestTo;
import com.capgemini.stockmarket.dto.TransactionTo;
import com.capgemini.stockmarket.settings.BrokersOfficeSettings;

@Component ("defaultBrokersOffice")
@Scope("singleton")
public class DefaultBrokersOffice implements BrokersOffice {
	private TransactionProcessor processor;
	private StockDataManager dataManager;
	
	@Inject
	public DefaultBrokersOffice(StockDataManager manager, TransactionProcessor processor) {
		this.processor = processor;
		this.dataManager = manager;
	}
	
	@Override
	public void dateChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TransactionOfferTo processRequest(TransactionRequestTo transactionRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionTo processAcceptance(TransactionAcceptTo transactionAccept) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CompanyTo> getStockCompanies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockPriceRecordTo> getSharePriceHistoryFor(CompanyTo company, Date fromDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockPriceRecordTo> getSharePriceHistoryFor(CompanyTo company) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPublicSecuritySignature() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void applySettings(BrokersOfficeSettings settings) {
		// TODO Auto-generated method stub
		
	}

}
