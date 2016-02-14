package com.capgemini.stockmarket.broker.processor;

import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.Money;
import com.capgemini.stockmarket.dto.transactions.Stock;

public interface StockCertifier {
	Stock provideCertifiedStock(CompanyTo ofCompany, double price);
	boolean confirmStockValidity(Stock stock);
	Money cashStock(Stock stock, double forPrice);
	boolean validateMoney(Money money);
}
