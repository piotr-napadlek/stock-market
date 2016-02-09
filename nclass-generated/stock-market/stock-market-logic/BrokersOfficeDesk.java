package stock-market.stock-market-logic;

import java.io.*;
import java.util.*;

public interface BrokersOfficeDesk extends StockInfoProvider {

	public TransactionOfferTo processRequest(TransactionRequestTo transactionRequest);

	public TransactionTo processAcceptance(TransactionAcceptTo transactionAccept);

	StockDataProvider provideStockDataSource();

}
