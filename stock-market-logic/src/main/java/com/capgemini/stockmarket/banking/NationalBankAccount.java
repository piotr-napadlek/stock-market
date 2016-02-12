package com.capgemini.stockmarket.banking;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.broker.Stock;
import com.capgemini.stockmarket.broker.StockInfo;
import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.Money;
import com.capgemini.stockmarket.dto.TransactionObjectTo;

@Component("nationalBankAccount")
@Scope("prototype")
final public class NationalBankAccount implements BankAccount {

	private Basket stockBasket;
	private Map<Currency, Double> balances = new ConcurrentHashMap<>();
	private Validator validator;

	@Inject
	public NationalBankAccount(Validator validator, Basket basket) {
		this.validator = validator;
		this.stockBasket = basket;
	}

	@Override
	public boolean putMoney(Money money) {
		if (money.getAmount() <= 0) {
			throw new BankOperationException("Impossible money amount");
		}
		if (validator.validate(money) == false) {
			throw new BankOperationException("These money are illegal!");
		}
		if (balances.containsKey(money.getCurrency()) == false) {
			balances.put(money.getCurrency(), 0d);
		}
		Double oldValue = balances.get(money.getCurrency());
		Double newValue = oldValue.doubleValue() + money.getAmount();
		balances.put(money.getCurrency(), newValue);
		money = null;
		return true;
	}

	@Override
	public boolean putStocks(Collection<Stock> stock) {
		return stockBasket.putStocks(stock);
	}

	@Override
	public Money extractMoney(Currency currency, double amount) {
		currencyCheck(currency);
		if (balances.get(currency) < amount) {
			throw new BankOperationException("Not enough money!");
		}
		Double oldValue = balances.get(currency);
		Double newValue = oldValue.doubleValue() - amount;
		balances.put(currency, newValue);
		return validator.createMoney(currency, amount);
	}

	private void currencyCheck(Currency currency) {
		if (balances.containsKey(currency) == false) {
			throw new BankOperationException(
					"No such currency balance is held for this account.");
		}
	}

	@Override
	public Collection<Stock> extractStock(Collection<StockInfo> stockInfo) {
		return stockBasket.extractStocks(stockInfo);
	}

	@Override
	public double getBalanceFor(Currency currency) {
		currencyCheck(currency);
		return balances.get(currency).doubleValue();
	}

	@Override
	public Set<Currency> getAvailableCurrencies() {
		return balances.keySet();
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
		this.balances.clear();
	}

	@Override
	public void digestTransaction(TransactionObjectTo<Stock> transaction) {
		stockBasket.putStocks(transaction.getSellItems());
	}
}
