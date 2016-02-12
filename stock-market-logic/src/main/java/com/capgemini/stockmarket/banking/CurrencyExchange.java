package com.capgemini.stockmarket.banking;

import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.Money;

public interface CurrencyExchange extends CurrencyInformer {

	Money convertMoneyTo(Money money, Currency currency);
}
