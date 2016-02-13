package com.capgemini.stockmarket.broker;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.broker.processor.TransactionProcessor;
import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.StockPriceRecordTo;
import com.capgemini.stockmarket.dto.transactions.TxAccept;
import com.capgemini.stockmarket.dto.transactions.TxFromBO;
import com.capgemini.stockmarket.dto.transactions.TxFromPlayer;
import com.capgemini.stockmarket.dto.transactions.TxOffer;
import com.capgemini.stockmarket.dto.transactions.TxRequest;
import com.capgemini.stockmarket.settings.BrokersOfficeSettings;

@Component("defaultBrokersOffice")
@Scope("singleton")
public class DefaultBrokersOffice implements BrokersOffice {
	private TransactionProcessor processor;
	private StockDataManager dataManager;
	private BrokersOfficeSettings settings;

	@Inject
	public DefaultBrokersOffice(StockDataManager manager, TransactionProcessor processor,
			BrokersOfficeSettings settings) {
		this.processor = processor;
		this.dataManager = manager;
		this.settings = settings;
	}

	@Override
	public List<CompanyTo> getStockCompanies() {
		return dataManager.getCompanies();
	}

	@Override
	public List<StockPriceRecordTo> getSharePriceHistoryFor(CompanyTo company, Date fromDate) {
		return dataManager.getCompanyStockPriceHistory(company, fromDate);
	}

	@Override
	public List<StockPriceRecordTo> getSharePriceHistoryFor(CompanyTo company) {
		return dataManager.getCompanyStockPriceHistory(company);
	}

	@Override
	public void applySettings(BrokersOfficeSettings settings) {
		this.settings = settings;
		processor.setSettings(settings);
	}

	@Override
	public TxOffer processRequest(TxRequest request) {
		return processor.prepareOffer(request);
	}

	@Override
	public Pair<Currency, Double> getTransactionFee(TxAccept request) {
		return processor.getTransactionFee(request);
	}

	@Override
	public Optional<TxFromBO> processAccept(Optional<TxFromPlayer> accept) {
		Optional<TxFromBO> transaction = processor.provideStocks(accept);
		if (transaction.isPresent() && accept.isPresent()) {
			dataManager.recordTransaction(transaction.get(), accept.get());
		}
		return transaction;
	}

	@Override
	public BoFeeInfo getBoTransactionFee() {
		return new BoFeeInfo(settings.getMinProvision().getLeft(), settings.getBoProvision(),
				settings.getMinProvision().getRight());
	}

	@Override
	public Double getTodaysPriceFor(CompanyTo company) {
		return dataManager.getCurrentStockPrice(company);
	}
}
