package com.capgemini.stockmarket.player;


import java.io.*;
import java.util.*;

public interface RequestCompositor {

	TransactionRequestTo composeRequest(BankAccounttInfo wallet, StockInfoProvider stockDataProvider, Date currentDate);

	TransactionAcceptTo verifyTransactionOffer(TransactionOfferTo transactionOffer);

}
