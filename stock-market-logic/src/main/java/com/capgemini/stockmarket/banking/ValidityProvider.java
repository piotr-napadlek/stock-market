package com.capgemini.stockmarket.banking;

interface ValidityProvider {
	Money createMoney(Currency currency, double amount);
}
