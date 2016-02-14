package com.capgemini.stockmarket.broker.processor;

import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.transactions.TxAccept;
import com.capgemini.stockmarket.dto.transactions.TxFromBO;
import com.capgemini.stockmarket.dto.transactions.TxFromPlayer;
import com.capgemini.stockmarket.dto.transactions.TxOffer;
import com.capgemini.stockmarket.dto.transactions.TxRequest;
import com.capgemini.stockmarket.settings.BrokersOfficeSettings;

public interface TransactionProcessor {

	TxOffer prepareOffer(TxRequest request);

	Pair<Currency, Double> getTransactionFee(TxAccept request);

	Optional<TxFromBO> provideStocks(Optional<TxFromPlayer> accept);

	void setSettings(BrokersOfficeSettings settings);

}