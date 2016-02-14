package com.capgemini.stockmarket.banking.account.caretaker;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.banking.BankOperationException;
import com.capgemini.stockmarket.banking.account.validator.MoneyValidator;
import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.Money;

@Component("nationalBankAccountCaretaker")
@Scope("prototype")
public class NationalBankAccountBalanceCaretaker implements AccountBalanceCaretaker{
	private Map<Currency, Double> balances = new ConcurrentHashMap<>();
	private MoneyValidator validator;
	
	@Inject
	public NationalBankAccountBalanceCaretaker(MoneyValidator validator) {
		this.validator = validator;
	}
	
	@Override
	public boolean putMoney(Money money) {
		checkMoneyAmount(money);
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

	private void checkMoneyAmount(Money money) {
		checkMoneyAmount(money.getAmount());
	}

	private void checkMoneyAmount(double amount) {
		if (amount <= 0) {
			throw new BankOperationException("Impossible money amount");
		}
	}
	
	@Override
	public Money extractMoney(Currency currency, double amount) {
		currencyCheck(currency);
		checkMoneyAmount(amount);
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
	public double getBalanceFor(Currency currency) {
		currencyCheck(currency);
		return balances.get(currency).doubleValue();
	}

	@Override
	public Set<Currency> getAvailableCurrencies() {
		return balances.keySet();
	}

	@Override
	public void clearAccount() {
		this.balances.clear();
	}
}
