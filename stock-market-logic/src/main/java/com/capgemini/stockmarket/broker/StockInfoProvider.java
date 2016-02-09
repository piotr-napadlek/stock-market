package com.capgemini.stockmarket.broker;


import java.io.*;
import java.util.*;

public interface StockInfoProvider {

	List<CompanyTo> getStockCompanies();

	List<StockPriceRecordTo> getSharePriceHistoryFor(CompanyTo company, Date fromDate);

	List<StockPriceRecordTo> getSharePriceHistoryFor(CompanyTo company);

}
