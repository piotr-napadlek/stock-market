package com.capgemini.stockmarket.player.strategy;

import java.util.Date;

import com.capgemini.stockmarket.banking.account.BankAccountInfo;
import com.capgemini.stockmarket.broker.StockInfoProvider;
import com.capgemini.stockmarket.common.StockTransactionInfo;
import com.capgemini.stockmarket.dto.TransactionObjectTo;

public class EagerTransactioner implements RequestCompositor {

	@Override
	public TransactionObjectTo<StockTransactionInfo> composeRequest(BankAccountInfo wallet,
			StockInfoProvider stockDataProvider, Date currentDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionObjectTo<StockTransactionInfo> verifyTransactionOffer(
			TransactionObjectTo<StockTransactionInfo> transactionOffer) {
		// TODO Auto-generated method stub
		return null;
	}


}
