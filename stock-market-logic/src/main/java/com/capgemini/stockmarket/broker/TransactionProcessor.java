package com.capgemini.stockmarket.broker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.banking.account.caretaker.AccountBalanceCaretaker;
import com.capgemini.stockmarket.common.StockTransactionInfo;
import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.TransactionObjectTo;
import com.capgemini.stockmarket.settings.BrokersOfficeSettings;

@Component
public class TransactionProcessor {
	private BrokersOfficeSettings settings;
	private Random random = new Random();
	private StockCertifier stockCertifier;
	private AccountBalanceCaretaker account;
	private Map<CompanyTo, StockTransactionInfo> stockPriceSellOffer = new HashMap<>();

	@Inject
	public TransactionProcessor(BrokersOfficeSettings settings, StockCertifier stockCertifier,
			AccountBalanceCaretaker account) {
		this.settings = settings;
		this.stockCertifier = stockCertifier;
		this.account = account;
	}

	public TransactionObjectTo<StockTransactionInfo, StockTransactionInfo> prepareOffer(
			TransactionObjectTo<StockTransactionInfo, StockTransactionInfo> request) {

		stockPriceSellOffer.clear();
		TransactionObjectTo<StockTransactionInfo, StockTransactionInfo> newOffer = new TransactionObjectTo<>();
		if ((int) request.getBuyItems().stream().map(item -> item.getCompany()).distinct()
				.count() < request.getBuyItems().size()) {
			throw new BrokersOfficeException("You should provide distinct companies offers. "
					+ "Cheating is disallowed.");
		}
		request.getSellItems().forEach(item -> {
			StockTransactionInfo sti = new StockTransactionInfo(item.getCompany(),
					randomBuyAmount(item.getAmount()), randomBuyPrice(item.getUnitPrice()),
					item.getCurrency());
			newOffer.addBuyItem(sti);
		});
		request.getBuyItems().forEach(item -> {
			StockTransactionInfo sti = new StockTransactionInfo(item.getCompany(),
					randomSellAmount(item.getAmount()), randomSellPrice(item.getUnitPrice()),
					item.getCurrency());
			newOffer.addSellItem(sti);
			stockPriceSellOffer.put(sti.getCompany(), sti);
		});
		return newOffer;
	}

	public TransactionObjectTo<Void, Stock> provideStocks(
			TransactionObjectTo<StockTransactionInfo, Stock> transactionAccept) {

		account.clearAccount();
		transactionAccept.getMoney().forEach(money -> account.putMoney(money));
		validateUserSentEnoughMoney(transactionAccept.getBuyItems());
		List<Stock> newStocksToSell = new ArrayList<>();
		transactionAccept.getBuyItems().forEach(buyItem -> {
			for (int i = 0; i < buyItem.getAmount(); i++) {
				newStocksToSell.add(stockCertifier.provideCertifiedStock(buyItem.getCompany(),
						buyItem.getUnitPrice(), buyItem.getCurrency()));
			}
		});
		TransactionObjectTo<Void, Stock> transaction = new TransactionObjectTo<>();
		transaction.addAllSellItems(newStocksToSell);

		transactionAccept.getSellItems().forEach(stock -> {
			if (stockCertifier.confirmStockValidity(stock)) {
				transaction.addMoney(stockCertifier.cashStock(stock,
						stockPriceSellOffer.get(stock.company()).getUnitPrice()));
			} else {
				throw new BrokersOfficeException("Was tried to be sold invalid stock!");
			}
		});
		return transaction;
	}

	private void validateUserSentEnoughMoney(Collection<StockTransactionInfo> buyItems) {
		try {
			buyItems.forEach(item -> {
				account.extractMoney(item.getCurrency(),
						item.getAmount() * item.getUnitPrice());
			});
		} catch (Exception e) {
			BrokersOfficeException ex = new BrokersOfficeException(
					"Not enough money provided for this buy demand.");
			ex.addSuppressed(e);
			throw ex;
		}
	}

	public void setSettings(BrokersOfficeSettings settings) {
		this.settings = settings;
	}

	private int randomSellAmount(int request) {
		double replySpan = settings.getMaxSellAvailability()
				- settings.getMinSellAvailability();
		double difference = random.nextDouble() * replySpan;
		return (int) Math.round((settings.getMaxSellAvailability() - difference) * request);
	}

	private double randomSellPrice(double request) {
		double replySpan = settings.getMaxSellOffer() - settings.getMinSellOffer();
		double difference = random.nextDouble() * replySpan;
		return (settings.getMaxSellOffer() - difference) * request;
	}

	private int randomBuyAmount(int request) {
		double replySpan = settings.getMaxBuyAvailability() - settings.getMinBuyAvailability();
		double difference = random.nextDouble() * replySpan;
		return (int) Math.round((settings.getMaxBuyAvailability() - difference) * request);
	}

	private double randomBuyPrice(double request) {
		double replySpan = settings.getMaxBuyOffer() - settings.getMinBuyOffer();
		double difference = random.nextDouble() * replySpan;
		return (settings.getMaxBuyOffer() - difference) * request;
	}
}
