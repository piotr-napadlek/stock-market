package com.capgemini.stockmarket.banking.account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.banking.CurrencyExchange;
import com.capgemini.stockmarket.banking.account.basket.Basket;
import com.capgemini.stockmarket.banking.account.caretaker.AccountBalanceCaretaker;
import com.capgemini.stockmarket.broker.Stock;
import com.capgemini.stockmarket.broker.StockInfo;
import com.capgemini.stockmarket.common.StockTransactionInfo;
import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.Money;
import com.capgemini.stockmarket.dto.TransactionObjectTo;

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
	public TransactionObjectTo<StockTransactionInfo, Stock> fillInTransaction(
			TransactionObjectTo<StockTransactionInfo, StockTransactionInfo> transactionAccept) {

		TransactionObjectTo<StockTransactionInfo, Stock> transactionFilled = new TransactionObjectTo<>();
		fillInSoldStock(transactionAccept, transactionFilled);
		fillInMoneyToBuy(transactionAccept, transactionFilled);
		return transactionFilled;
	}

	private void fillInMoneyToBuy(
			TransactionObjectTo<StockTransactionInfo, StockTransactionInfo> transactionAccept,
			TransactionObjectTo<StockTransactionInfo, Stock> transactionFilled) {
		
		transactionAccept.getBuyItems()
				.forEach(stock -> transactionFilled.addMoney(extractMoney(stock.getCurrency(),
						(stock.getUnitPrice() * stock.getAmount()))));
	}

	private void fillInSoldStock(
			TransactionObjectTo<StockTransactionInfo, StockTransactionInfo> transactionAccept,
			TransactionObjectTo<StockTransactionInfo, Stock> transactionFilled) {
		List<Stock> soldStock = new ArrayList<>();
		transactionAccept.getSellItems().forEach(stockTI -> soldStock
				.addAll(stockBasket.extractStock(stockTI.getCompany(), stockTI.getAmount())));
		transactionFilled.addAllSellItems(soldStock);
	}

	@Override
	public void digestTransaction(TransactionObjectTo<Void, Stock> transaction) {
		transaction.getMoney().forEach(money -> balanceCaretaker.putMoney(money));
		stockBasket.putStocks(transaction.getSellItems());
	}

}
