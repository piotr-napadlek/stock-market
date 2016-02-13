package com.capgemini.stockmarket.broker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.Money;
import com.capgemini.stockmarket.dto.transactions.NumPair;
import com.capgemini.stockmarket.dto.transactions.Stock;
import com.capgemini.stockmarket.dto.transactions.TxFromBO;
import com.capgemini.stockmarket.dto.transactions.TxFromPlayer;
import com.capgemini.stockmarket.dto.transactions.TxOffer;
import com.capgemini.stockmarket.dto.transactions.TxRequest;
import com.capgemini.stockmarket.settings.BrokersOfficeSettings;

@Component
public class TransactionProcessor {
	private BrokersOfficeSettings settings;
	private StockCertifier stockCertifier;
	private StockPriceInformer informer;

	private Map<CompanyTo, NumPair<Integer, Double>> stockPriceSellOffer = new HashMap<>();
	private Map<CompanyTo, NumPair<Integer, Double>> stockPriceBuyOffer = new HashMap<>();
	private Random random = new Random();
	private double transactionFee = 0d;
	private double transactionSum = 0d;

	@Inject
	public TransactionProcessor(BrokersOfficeSettings settings, StockCertifier stockCertifier,
			StockPriceInformer informer) {
		this.settings = settings;
		this.stockCertifier = stockCertifier;
		this.informer = informer;
	}

	public TxOffer prepareOffer(TxRequest request) {
		stockPriceSellOffer.clear();
		stockPriceBuyOffer.clear();
		TxOffer offer = new TxOffer();
		prepareBuyOffer(request, offer);
		prepareSellOffer(request, offer);
		return offer;
	}

	public Pair<Currency, Double> getTransactionFee(TxRequest request) {
		transactionSum = 0;
		request.getBuyRequests().forEach((company, amount) -> {
			transactionSum += stockPriceSellOffer.get(company).getRight() * amount;
		});
		request.getSellRequests().forEach((company, amount) -> {
			transactionSum += stockPriceBuyOffer.get(company).getRight() * amount;
		});
		Currency currencyAccepted = settings.getMinProvision().getLeft();
		transactionFee = transactionSum * settings.getBoProvision();
		transactionFee = Math.max(transactionFee, settings.getMinProvision().getRight());
		return Pair.of(currencyAccepted, transactionFee);
	}

	public Optional<TxFromBO> provideStocks(Optional<TxFromPlayer> accept) {
		TxFromBO transaction = null;
		if (accept.isPresent()) {
			transaction = new TxFromBO();
			List<Stock> newStocksToSell = prepareNewStocksForPlayer(accept.get());
			transaction.addAllStocskBought(newStocksToSell);
			cashStocksBeingSold(accept.get(), transaction);
		}
		return Optional.ofNullable(transaction);
	}

	private void prepareSellOffer(TxRequest request, TxOffer offer) {
		request.getBuyRequests().forEach((company, amount) -> {
			double priceForStock = informer.getCurrentStockPrice(company);
			double priceOffer = randomSellPrice(priceForStock);
			int amountOffer = randomSellAmount(amount);
			offer.addSellAccept(company, amountOffer, priceOffer);
			stockPriceSellOffer.put(company, NumPair.of(amountOffer, priceOffer));
		});
	}

	private void prepareBuyOffer(TxRequest request, TxOffer offer) {
		request.getSellRequests().forEach((company, amount) -> {
			double priceForStock = informer.getCurrentStockPrice(company);
			double priceOffer = randomBuyPrice(priceForStock);
			int amountOffer = randomBuyAmount(amount);
			offer.addBuyAccept(company, amountOffer, priceOffer);
			stockPriceSellOffer.put(company, NumPair.of(amountOffer, priceOffer));
		});
	}

	private void cashStocksBeingSold(TxFromPlayer accept, TxFromBO transaction) {
		accept.getAllSoldStocks().forEach(stock -> {
			if (stockCertifier.confirmStockValidity(stock)) {
				transaction.addMoneyForDisposal(stockCertifier.cashStock(stock,
						stockPriceSellOffer.get(stock.getCompany()).getRight()));
			} else {
				throw new BrokersOfficeException("Was tried to be sold invalid stock!");
			}
		});
	}

	private List<Stock> prepareNewStocksForPlayer(TxFromPlayer accept) {
		List<Stock> stocksToSell = new ArrayList<>();
		accept.getMoneyToBuyStocks().forEach((company, amountMoney) -> {
			validateUserSentEnoughMoney(amountMoney.getRight(), company,
					amountMoney.getLeft());
			IntStream.range(0, amountMoney.getLeft()).forEach(i -> {
				stocksToSell.add(stockCertifier.provideCertifiedStock(company,
						stockPriceBuyOffer.get(company).getRight(), company.stockCurrency()));
			});
		});
		return stocksToSell;
	}

	private void validateUserSentEnoughMoney(Money money, CompanyTo company, Integer amount) {
		double demandedMoney = stockPriceBuyOffer.getOrDefault(company, NumPair.of(0, 0d))
				.product();
		if (money.getAmount() - demandedMoney > 0.00000001d) {
			throw new BrokersOfficeException(
					"Not enough money provided for this buy demand. Company: "
							+ company.getName());
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
