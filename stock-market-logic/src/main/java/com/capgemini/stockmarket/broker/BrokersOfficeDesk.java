package com.capgemini.stockmarket.broker;


import com.capgemini.stockmarket.common.StockTransactionInfo;
import com.capgemini.stockmarket.dto.TransactionObjectTo;

public interface BrokersOfficeDesk extends StockInfoProvider {

	public TransactionObjectTo<StockTransactionInfo> processRequest(TransactionObjectTo<StockTransactionInfo> transactionRequest);

	public TransactionObjectTo<Stock> processAcceptance(TransactionObjectTo<StockTransactionInfo> transactionAccept);

}
