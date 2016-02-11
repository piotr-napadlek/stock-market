package com.capgemini.stockmarket.broker;

import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.dto.TransactionAcceptTo;
import com.capgemini.stockmarket.dto.TransactionOfferTo;
import com.capgemini.stockmarket.dto.TransactionRequestTo;
import com.capgemini.stockmarket.dto.TransactionTo;

@Component
public class TransactionProcessor {

	public TransactionOfferTo prepareOffer(TransactionRequestTo request) {
		return null;

	}

	public TransactionTo signTransaction(TransactionAcceptTo transactionAccept) {
		return null;

	}

}
