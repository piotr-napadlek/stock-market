package com.capgemini.stockmarket.broker;

import com.capgemini.stockmarket.broker.datamanager.StockInfoProvider;

public interface BrokersOfficeProxy extends StockInfoProvider {
	public BoFeeInfo getBoTransactionFee();
}
