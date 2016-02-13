package com.capgemini.stockmarket.broker;

import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.transactions.TxAccept;
import com.capgemini.stockmarket.dto.transactions.TxFromBO;
import com.capgemini.stockmarket.dto.transactions.TxFromPlayer;
import com.capgemini.stockmarket.dto.transactions.TxOffer;
import com.capgemini.stockmarket.dto.transactions.TxRequest;

public interface BrokersOfficeDesk extends BrokersOfficeProxy {

	public TxOffer processRequest(TxRequest request);

	public Pair<Currency, Double> getTransactionFee(TxAccept request);

	public Optional<TxFromBO> processAccept(Optional<TxFromPlayer> accept);
}
