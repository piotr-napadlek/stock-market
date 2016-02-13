package com.capgemini.stockmarket.broker.processor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.banking.account.validator.Validator;
import com.capgemini.stockmarket.broker.BrokersOfficeException;
import com.capgemini.stockmarket.common.DateInfo;
import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.Money;
import com.capgemini.stockmarket.dto.transactions.Stock;

@Component
@Scope("singleton")
public class DefaultBrokersOfficeStockCertifier implements StockCertifier{
	private Map<String, Stock> stocksReleased = new HashMap<>();
	private DateInfo dateInfo;
	private Validator validator;

	@Inject
	public DefaultBrokersOfficeStockCertifier(DateInfo dateInfo, Validator validator) {
		this.dateInfo = dateInfo;
		this.validator = validator;
	}

	@Override
	public Stock provideCertifiedStock(CompanyTo ofCompany, double price, Currency currency) {
		Stock newStock = Stock.createStock(generateSignature(), ofCompany, dateInfo.getCurrentDate(),
				price, currency);
		stocksReleased.put(newStock.getSignature(), newStock);
		return newStock;
	}

	private String generateSignature() {
		return UUID.randomUUID().toString();
	}

	@Override
	public boolean confirmStockValidity(Stock stock) {
		return stocksReleased.containsKey(stock.getSignature());
	}

	@Override
	public Money cashStock(Stock stock, double forPrice) {
		if (confirmStockValidity(stock)) {
			stocksReleased.remove(stock.getSignature());
			Money returnMoney = validator.createMoney(stock.currencyBought(), forPrice);
			stock = null;
			return returnMoney;
		} else {
			throw new BrokersOfficeException("Invalid stock was provided to cash!");
		}
	}
}
