package com.capgemini.stockmarket.player.strategy;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.banking.account.BankAccountInfo;
import com.capgemini.stockmarket.broker.datamanager.StockInfoProvider;
import com.capgemini.stockmarket.dto.transactions.TxAccept;
import com.capgemini.stockmarket.dto.transactions.TxOffer;
import com.capgemini.stockmarket.dto.transactions.TxRequest;

@Component ("passive")
@Scope ("prototype")
public class TotallyPassive implements RequestCompositor {

	public TotallyPassive() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public TxRequest composeRequest(BankAccountInfo wallet,
			StockInfoProvider stockDataProvider, Date currentDate) {
		// TODO Auto-generated method stub
		return new TxRequest();
	}

	@Override
	public TxAccept verifyTransactionOffer(TxOffer offer) {
		// TODO Auto-generated method stub
		return new TxAccept();
	}

}
