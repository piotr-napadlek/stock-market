package com.capgemini.stockmarket.player.strategy;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.banking.account.BankAccountInfo;
import com.capgemini.stockmarket.broker.Stock;
import com.capgemini.stockmarket.broker.StockInfoProvider;
import com.capgemini.stockmarket.common.StockTransactionInfo;
import com.capgemini.stockmarket.dto.TransactionObjectTo;

@Component("defaultStrategy")
public class Manual implements RequestCompositor {

	@Override
	public TransactionObjectTo<StockTransactionInfo, StockTransactionInfo> composeRequest(
			BankAccountInfo wallet, StockInfoProvider stockDataProvider, Date currentDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionObjectTo<StockTransactionInfo, StockTransactionInfo> verifyTransactionOffer(
			TransactionObjectTo<StockTransactionInfo, StockTransactionInfo> transactionOffer) {
		// TODO Auto-generated method stub
		return null;
	}



}
