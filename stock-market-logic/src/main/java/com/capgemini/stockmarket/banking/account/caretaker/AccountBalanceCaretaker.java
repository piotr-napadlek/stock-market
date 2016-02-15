package com.capgemini.stockmarket.banking.account.caretaker;

import java.util.Set;

import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.Money;

public interface AccountBalanceCaretaker {
	boolean putMoney(Money money);
	Money extractMoney(Currency currency, double amount);
	void clearAccount();
	double getBalanceFor(Currency currency);
	Set<Currency> getAvailableCurrencies();
	Currency getDefaultCurrency();
}
