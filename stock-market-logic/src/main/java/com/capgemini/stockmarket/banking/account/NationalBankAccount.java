package com.capgemini.stockmarket.banking.account;

import java.util.Collection;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.banking.BankOperationException;
import com.capgemini.stockmarket.banking.CurrencyExchange;
import com.capgemini.stockmarket.banking.account.basket.Basket;
import com.capgemini.stockmarket.banking.account.caretaker.AccountBalanceCaretaker;
import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.Money;
import com.capgemini.stockmarket.dto.transactions.Stock;
import com.capgemini.stockmarket.dto.transactions.StockInfo;
import com.capgemini.stockmarket.dto.transactions.TxAccept;
import com.capgemini.stockmarket.dto.transactions.TxFromBO;
import com.capgemini.stockmarket.dto.transactions.TxFromPlayer;

@Component("nationalBankAccount")
@Scope("prototype")
final public class NationalBankAccount implements BankAccount {

	private Basket stockBasket;
	private AccountBalanceCaretaker balanceCaretaker;
	private CurrencyExchange currencyExchange;

	@Inject
	public NationalBankAccount(Basket basket, AccountBalanceCaretaker balanceCaretaker,
			CurrencyExchange currencyExchange) {
		this.stockBasket = basket;
		this.balanceCaretaker = balanceCaretaker;
		this.currencyExchange = currencyExchange;
	}

	@Override
	public boolean putMoney(Money money) {
		return balanceCaretaker.putMoney(money);
	}

	@Override
	public boolean putStocks(Collection<Stock> stock) {
		return stockBasket.putStocks(stock);
	}

	@Override
	public Money extractMoney(Currency currency, double amount) {
		return balanceCaretaker.extractMoney(currency, amount);
	}

	@Override
	public Collection<Stock> extractStock(Collection<StockInfo> stockInfo) {
		return stockBasket.extractStocks(stockInfo);
	}

	@Override
	public double getBalanceFor(Currency currency) {
		return balanceCaretaker.getBalanceFor(currency);
	}

	@Override
	public Set<Currency> getAvailableCurrencies() {
		return balanceCaretaker.getAvailableCurrencies();
	}

	@Override
	public Collection<CompanyTo> getAvailableStockCompanies() {
		return stockBasket.getAvailableCompanies();
	}

	@Override
	public Collection<StockInfo> getStockInfos(CompanyTo company) {
		return stockBasket.getStockInfos(company);
	}

	@Override
	public void clearAccount() {
		this.balanceCaretaker.clearAccount();
	}

	public void exchangeMoney(Money money, Currency targetCurrency) {
		balanceCaretaker.putMoney(currencyExchange.convertMoneyTo(money, targetCurrency));
	}

	@Override
	public void digestTransaction(TxFromBO transaction) {
		transaction.getMoneyForSoldStocks().forEach(money -> balanceCaretaker.putMoney(money));
		stockBasket.putStocks(transaction.getStocksBought());

	}

	@Override
	public TxFromPlayer fillInTransaction(TxAccept accept, Pair<Currency, Double> fee) {
		final Money transactionFee;
		final TxFromPlayer fromPlayer;
		try {
			fromPlayer = new TxFromPlayer();
			accept.getSellAccepts().forEach((company, amount) -> {
				fromPlayer.addAllStocksToSell(
						stockBasket.extractStock(company, amount.getLeft()));
			});
			accept.getBuyAccepts().forEach((company, amount) -> {
				fromPlayer.putMoneyToBuy(company, amount.getLeft(),
						extractMoney(company.stockCurrency(), amount.product()));
			});
			transactionFee = balanceCaretaker.extractMoney(fee.getLeft(), fee.getRight());
			fromPlayer.addTransactionFee(transactionFee);
		} catch (BankOperationException e) {
			e.printStackTrace();
			return null;
		}
		return fromPlayer;
	}

}
