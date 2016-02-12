package com.capgemini.stockmarket.player.strategy;

import java.util.Date;

import com.capgemini.stockmarket.banking.account.BankAccountInfo;
import com.capgemini.stockmarket.broker.StockInfoProvider;
import com.capgemini.stockmarket.common.StockTransactionInfo;
import com.capgemini.stockmarket.dto.TransactionObjectTo;

public interface RequestCompositor {

	TransactionObjectTo<StockTransactionInfo, StockTransactionInfo> composeRequest(
			BankAccountInfo wallet, StockInfoProvider stockDataProvider, Date currentDate);

	TransactionObjectTo<StockTransactionInfo, StockTransactionInfo> verifyTransactionOffer(
			TransactionObjectTo<StockTransactionInfo, StockTransactionInfo> transactionOffer);

}
