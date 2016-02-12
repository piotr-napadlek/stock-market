package com.capgemini.stockmarket.banking;

public interface CurrencyInformer {
	double getSellExchangeRateBetween(Currency first, Currency second);
	double getBuyExchangeRateBetween(Currency first, Currency second);
	double getBuyUsdExchangeRateFor(Currency currency);
	double getSellUsdExchangeRateFor(Currency currency);
}
