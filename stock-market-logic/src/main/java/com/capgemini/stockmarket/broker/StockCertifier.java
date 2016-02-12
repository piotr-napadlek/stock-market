package com.capgemini.stockmarket.broker;

import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.Money;

public interface StockCertifier {
	Stock provideCertifiedStock(CompanyTo ofCompany, double price, Currency currency);
	boolean confirmStockValidity(Stock stock);
	Money cashStock(Stock stock, double forPrice);
}
