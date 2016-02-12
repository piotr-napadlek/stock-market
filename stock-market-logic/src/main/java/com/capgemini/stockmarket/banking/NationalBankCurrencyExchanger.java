package com.capgemini.stockmarket.banking;

import org.springframework.stereotype.Component;

@Component
public class NationalBankCurrencyExchanger implements CurrencyExchange {

	@Override
	public double getSellExchangeRateBetween(Currency first, Currency second) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getBuyExchangeRateBetween(Currency first, Currency second) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getBuyUsdExchangeRateFor(Currency currency) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getSellUsdExchangeRateFor(Currency currency) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Money convertMoneyTo(Money money, Currency currency) {
		// TODO Auto-generated method stub
		return null;
	}

}
