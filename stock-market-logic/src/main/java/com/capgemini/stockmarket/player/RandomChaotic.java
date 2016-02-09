package com.capgemini.stockmarket.player;


import java.util.Date;

import com.capgemini.stockmarket.banking.BankAccountInfo;
import com.capgemini.stockmarket.broker.StockInfoProvider;
import com.capgemini.stockmarket.dto.TransactionAcceptTo;
import com.capgemini.stockmarket.dto.TransactionOfferTo;
import com.capgemini.stockmarket.dto.TransactionRequestTo;

public class RandomChaotic implements RequestCompositor {

	@Override
	public TransactionRequestTo composeRequest(BankAccountInfo wallet,
			StockInfoProvider stockDataProvider, Date currentDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionAcceptTo verifyTransactionOffer(TransactionOfferTo transactionOffer) {
		// TODO Auto-generated method stub
		return null;
	}

}
