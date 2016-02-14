package com.capgemini.stockmarket.broker.logger;

import com.capgemini.stockmarket.dto.transactions.TxFromBO;
import com.capgemini.stockmarket.dto.transactions.TxFromPlayer;

public interface TransactionLogger {

	void logTransaction(TxFromBO response, TxFromPlayer accept);
}
