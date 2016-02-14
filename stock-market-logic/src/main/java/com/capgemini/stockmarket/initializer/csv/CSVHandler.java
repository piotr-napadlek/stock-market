package com.capgemini.stockmarket.initializer.csv;

import java.util.List;

import com.capgemini.stockmarket.dto.StockPriceRecordTo;

public interface CSVHandler {

	List<StockPriceRecordTo> parseCSV(String csv) throws Exception;
	
	void setDelimeter(String delimeter);
	
	void setHeaders(String headers);
	
	void setDateFormat(String dateFormat);

}
