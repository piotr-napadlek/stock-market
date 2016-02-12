package com.capgemini.stockmarket.banking;

import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.Money;

interface ValidityProvider {
	Money createMoney(Currency currency, double amount);
}
