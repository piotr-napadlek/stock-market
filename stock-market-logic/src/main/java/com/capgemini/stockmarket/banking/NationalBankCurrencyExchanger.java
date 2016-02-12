package com.capgemini.stockmarket.banking;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.Money;

@Component
public class NationalBankCurrencyExchanger implements CurrencyExchange {

	@Override
	public double getSellExchangeRateBetween(Currency first, Currency second) {
		return 0;
	}

	@Override
	public double getBuyExchangeRateBetween(Currency first, Currency second) {
		return 0;
	}

	@Override
	public double getBuyUsdExchangeRateFor(Currency currency) {
		return 0;
	}

	@Override
	public double getSellUsdExchangeRateFor(Currency currency) {
		return 0;
	}

	@Override
	public Money convertMoneyTo(Money money, Currency currency) {
		throw new NotImplementedException("soon.");
	}

}
