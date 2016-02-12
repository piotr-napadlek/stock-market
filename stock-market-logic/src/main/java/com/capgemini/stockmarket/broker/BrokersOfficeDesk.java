package com.capgemini.stockmarket.broker;

import com.capgemini.stockmarket.common.StockTransactionInfo;
import com.capgemini.stockmarket.dto.TransactionObjectTo;

public interface BrokersOfficeDesk extends StockInfoProvider {

	public TransactionObjectTo<StockTransactionInfo, StockTransactionInfo> processRequest(
			TransactionObjectTo<StockTransactionInfo, StockTransactionInfo> transactionRequest);

	public TransactionObjectTo<Void, Stock> processAcceptance(
			TransactionObjectTo<StockTransactionInfo, Stock> transactionAccept);
}
