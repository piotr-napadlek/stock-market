package com.capgemini.stockmarket.banking.account.validator;

import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.Money;

interface ValidityProvider {
	Money createMoney(Currency currency, double amount);
}
