package com.capgemini.stockmarket.player.strategy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.banking.account.BankAccountInfo;
import com.capgemini.stockmarket.broker.datamanager.StockInfoProvider;
import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.transactions.TxAccept;
import com.capgemini.stockmarket.dto.transactions.TxOffer;
import com.capgemini.stockmarket.dto.transactions.TxRequest;

@Component("quickBuyer")
@Scope("prototype")
public class QuickBuyer implements RequestCompositor {
	private TxRequest request;
	private Map<CompanyTo, CompanyRating> ratings = new HashMap<>();

	@Override
	public TxRequest composeRequest(BankAccountInfo wallet,
			StockInfoProvider stockDataProvider, Date currentDate) {
		if (wallet.getAvailableStockCompanies().isEmpty()) {
			buyInitial(wallet, stockDataProvider, currentDate);
		} else {
			tryToSell(wallet, stockDataProvider);
		}
		return request;
	}

	private void tryToSell(BankAccountInfo wallet, StockInfoProvider stockDataProvider) {
		ratings.keySet().removeIf(
				company -> wallet.getAvailableStockCompanies().contains(company) == false);
		request = new TxRequest();
		ratings.forEach((company, rating) -> {
			rating.priceHistory.add(stockDataProvider.getTodaysPriceFor(company));
			if (rating.shouldISell()) {
				request.addSellRequest(company, wallet.getStockInfos(company).size());
			}
		});
	}

	private void buyInitial(BankAccountInfo wallet, StockInfoProvider stockDataProvider,
			Date currentDate) {
		request = new TxRequest();
		double moneyPerCompany = wallet.getBalanceFor(wallet.getDefaultCurrency())
				/ stockDataProvider.getStockCompanies().size();
		stockDataProvider.getStockCompanies().forEach(company -> {
			double todaysPrice = stockDataProvider.getTodaysPriceFor(company);
			request.addBuyRequest(company, (int) (moneyPerCompany / todaysPrice));
			CompanyRating rating = new CompanyRating();
			rating.initialPrice = todaysPrice;
			rating.priceHistory.add(todaysPrice);
			ratings.put(company, rating);
		});
	}

	@Override
	public TxAccept verifyTransactionOffer(TxOffer offer) {
		return new TxAccept().acceptWholeOffer(offer);
	}

	private class CompanyRating {
		private static final double SELL_PERCENT_THRESHOLD = 0.05;
		double initialPrice;
		List<Double> priceHistory = new ArrayList<>(200);

		public boolean shouldISell() {
			if ((currentPrice() - initialPrice) / initialPrice > SELL_PERCENT_THRESHOLD) {
				return true;
			}
			return false;
		}

		double currentPrice() {
			return priceHistory.get(priceHistory.size() - 1);
		}
	}
}
