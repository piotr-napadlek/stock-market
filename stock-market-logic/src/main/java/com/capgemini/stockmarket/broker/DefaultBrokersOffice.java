package com.capgemini.stockmarket.broker;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.StockPriceRecordTo;
import com.capgemini.stockmarket.dto.transactions.TxAccept;
import com.capgemini.stockmarket.dto.transactions.TxFromBO;
import com.capgemini.stockmarket.dto.transactions.TxFromPlayer;
import com.capgemini.stockmarket.dto.transactions.TxOffer;
import com.capgemini.stockmarket.dto.transactions.TxRequest;
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

	@Override
	public TxOffer processRequest(TxRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pair<Currency, Double> getTransactionFee(TxAccept request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<TxFromBO> processAccept(Optional<TxFromPlayer> accept) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoFeeInfo getBoTransactionFee() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getTodaysPriceFor(CompanyTo company) {
		// TODO Auto-generated method stub
		return null;
	}


}
