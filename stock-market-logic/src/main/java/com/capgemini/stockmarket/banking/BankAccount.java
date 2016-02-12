package com.capgemini.stockmarket.banking;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import com.capgemini.stockmarket.broker.Stock;
import com.capgemini.stockmarket.broker.StockInfo;
import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.TransactionObjectTo;

final public class BankAccount implements BankAccountInfo {

	private StockBasket stockBasket;
	private Map<Currency, Double> balances = new ConcurrentHashMap<>();
	private Validator validator;

	@Inject
	public BankAccount(Validator validator) {
		this.validator = validator;
	}

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

	public boolean putShares(Set<Stock> shares) {
		return false;

	}

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

	public Set<Stock> extractShares(List<StockInfo> shareInfos) {
		return null;
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
	public List<CompanyTo> getAvailableSharesCompanies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<StockInfo> getShareInfos(CompanyTo company) {
		// TODO Auto-generated method stub
		return null;
	}

	public void clearAccount() {
		this.balances.clear();
	}

	public void digestTransaction(TransactionObjectTo<Stock> transaction) {
		// TODO Auto-generated method stub
	}
}
