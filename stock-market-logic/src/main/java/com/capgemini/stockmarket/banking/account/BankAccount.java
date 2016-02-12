package com.capgemini.stockmarket.banking.account;

import java.util.Collection;

import com.capgemini.stockmarket.broker.Stock;
import com.capgemini.stockmarket.broker.StockInfo;
import com.capgemini.stockmarket.common.StockTransactionInfo;
import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.Money;
import com.capgemini.stockmarket.dto.TransactionObjectTo;

public interface BankAccount extends BankAccountInfo {

	boolean putMoney(Money money);

	boolean putStocks(Collection<Stock> stock);

	Money extractMoney(Currency currency, double amount);

	Collection<Stock> extractStock(Collection<StockInfo> stockInfo);
	
	void clearAccount();

	TransactionObjectTo<StockTransactionInfo, Stock> fillInTransaction(
			TransactionObjectTo<StockTransactionInfo, StockTransactionInfo> transactionAccept);

	void digestTransaction(TransactionObjectTo<Void, Stock> transaction);

}