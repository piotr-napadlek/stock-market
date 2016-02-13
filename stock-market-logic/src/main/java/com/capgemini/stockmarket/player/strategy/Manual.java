package com.capgemini.stockmarket.player.strategy;

import java.util.Date;

import com.capgemini.stockmarket.banking.account.BankAccountInfo;
import com.capgemini.stockmarket.broker.StockInfoProvider;
import com.capgemini.stockmarket.dto.transactions.TxAccept;
import com.capgemini.stockmarket.dto.transactions.TxOffer;
import com.capgemini.stockmarket.dto.transactions.TxRequest;


public class Manual implements RequestCompositor {

	@Override
	public TxRequest composeRequest(BankAccountInfo wallet,
			StockInfoProvider stockDataProvider, Date currentDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TxAccept verifyTransactionOffer(TxOffer offer) {
		// TODO Auto-generated method stub
		return null;
	}


}
