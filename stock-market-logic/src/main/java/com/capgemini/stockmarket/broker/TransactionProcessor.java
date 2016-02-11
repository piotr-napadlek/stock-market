package com.capgemini.stockmarket.broker;

import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.common.StockTransactionInfo;
import com.capgemini.stockmarket.dto.TransactionObjectTo;

@Component
public class TransactionProcessor {

	public TransactionObjectTo<StockTransactionInfo> prepareOffer(TransactionObjectTo<StockTransactionInfo> request) {
		return null;

	}

	public TransactionObjectTo<Stock> signTransaction(TransactionObjectTo<StockTransactionInfo> transactionAccept) {
		return null;

	}

}
