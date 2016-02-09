package com.capgemini.stockmarket.broker;


import com.capgemini.stockmarket.dto.TransactionAcceptTo;
import com.capgemini.stockmarket.dto.TransactionOfferTo;
import com.capgemini.stockmarket.dto.TransactionRequestTo;
import com.capgemini.stockmarket.dto.TransactionTo;

public interface BrokersOfficeDesk extends StockInfoProvider {

	public TransactionOfferTo processRequest(TransactionRequestTo transactionRequest);

	public TransactionTo processAcceptance(TransactionAcceptTo transactionAccept);

}
