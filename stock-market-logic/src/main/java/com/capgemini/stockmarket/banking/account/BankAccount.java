package com.capgemini.stockmarket.banking.account;

import java.util.Collection;

import org.apache.commons.lang3.tuple.Pair;

import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.Money;
import com.capgemini.stockmarket.dto.transactions.Stock;
import com.capgemini.stockmarket.dto.transactions.StockInfo;
import com.capgemini.stockmarket.dto.transactions.TxAccept;
import com.capgemini.stockmarket.dto.transactions.TxFromBO;
import com.capgemini.stockmarket.dto.transactions.TxFromPlayer;

public interface BankAccount extends BankAccountInfo {

	boolean putMoney(Money money);

	boolean putStocks(Collection<Stock> stock);

	Money extractMoney(Currency currency, double amount);

	Collection<Stock> extractStock(Collection<StockInfo> stockInfo);

	void clearAccount();

	void digestTransaction(TxFromBO transaction);

	TxFromPlayer fillInTransaction(TxAccept offerVerified,
			Pair<Currency, Double> transactionFee);

}