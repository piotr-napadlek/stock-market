package com.capgemini.stockmarket.player.strategy;


import java.util.Date;

import com.capgemini.stockmarket.banking.BankAccountInfo;
import com.capgemini.stockmarket.broker.StockInfoProvider;
import com.capgemini.stockmarket.common.StockTransactionInfo;
import com.capgemini.stockmarket.dto.TransactionObjectTo;

public interface RequestCompositor {

	TransactionObjectTo<StockTransactionInfo> composeRequest(BankAccountInfo wallet, StockInfoProvider stockDataProvider, Date currentDate);

	TransactionObjectTo<StockTransactionInfo> verifyTransactionOffer(TransactionObjectTo<StockTransactionInfo> transactionOffer);

}
