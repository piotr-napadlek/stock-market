package com.capgemini.stockmarket.player.strategy;

import java.util.Date;

import com.capgemini.stockmarket.banking.account.BankAccountInfo;
import com.capgemini.stockmarket.broker.datamanager.StockInfoProvider;
import com.capgemini.stockmarket.dto.transactions.TxAccept;
import com.capgemini.stockmarket.dto.transactions.TxOffer;
import com.capgemini.stockmarket.dto.transactions.TxRequest;

public interface RequestCompositor {

	TxRequest composeRequest(BankAccountInfo wallet, StockInfoProvider stockDataProvider,
			Date currentDate);

	TxAccept verifyTransactionOffer(TxOffer offer);

}
