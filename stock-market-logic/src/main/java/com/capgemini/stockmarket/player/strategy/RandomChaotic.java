package com.capgemini.stockmarket.player.strategy;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.banking.account.BankAccountInfo;
import com.capgemini.stockmarket.broker.StockInfoProvider;
import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.transactions.TxAccept;
import com.capgemini.stockmarket.dto.transactions.TxOffer;
import com.capgemini.stockmarket.dto.transactions.TxRequest;

@Component("defaultStrategy")
public class RandomChaotic implements RequestCompositor {
	private Random random = new Random();

	@Override
	public TxRequest composeRequest(BankAccountInfo wallet,
			StockInfoProvider stockDataProvider, Date currentDate) {
		List<CompanyTo> companies = stockDataProvider.getStockCompanies();
		TxRequest request = new TxRequest();

		for (int i = 0; i < random.nextInt(companies.size() / 2); i++) {
			CompanyTo company = companies.get(random.nextInt(companies.size()));
			int amount = random.nextInt((int) (wallet.getBalanceFor(company.stockCurrency())
					/ stockDataProvider.getTodaysPriceFor(company) / 3));
			amount = Math.max(1, amount);
			request.addBuyRequest(company, amount);
		}
		if (wallet.getAvailableStockCompanies().size() > 0) {
			for (int i = 0; i < random.nextInt(wallet.getAvailableStockCompanies().size())
					/ 2; i++) {
				CompanyTo company = wallet.getAvailableStockCompanies()
						.get(random.nextInt(wallet.getAvailableStockCompanies().size()));
				int amount = random.nextInt(wallet.getStockInfos(company).size());
				request.addSellRequest(company, amount);
			}
		}
		return request;
	}

	@Override
	public TxAccept verifyTransactionOffer(TxOffer offer) {
		TxAccept accept = new TxAccept();
		offer.getBuyOffers().forEach((company, pair) -> accept.addSellAccept(company,
				pair.getLeft(), pair.getRight()));
		offer.getSellOffers().forEach((company, pair) -> accept.addBuyAccept(company,
				pair.getLeft(), pair.getRight()));
		return accept;
	}

}
