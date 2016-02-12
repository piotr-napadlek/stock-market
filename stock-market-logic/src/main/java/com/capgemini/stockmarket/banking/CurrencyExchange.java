package com.capgemini.stockmarket.banking;

public interface CurrencyExchange extends CurrencyInformer {

	Money convertMoneyTo(Money money, Currency currency);
}
