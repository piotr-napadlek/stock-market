package com.capgemini.stockmarket.broker;

public interface BrokersOfficeProxy extends StockInfoProvider {
	public BoFeeInfo getBoTransactionFee();
}
