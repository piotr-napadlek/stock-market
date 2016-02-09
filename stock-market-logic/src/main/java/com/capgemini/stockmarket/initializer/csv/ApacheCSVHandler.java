package com.capgemini.stockmarket.initializer.csv;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.StockPriceRecordTo;
import com.capgemini.stockmarket.initializer.CSVHandler;

@Component
public class ApacheCSVHandler implements CSVHandler {

	@Value("${csv.format.delimeter}")
	private char csvFormatDelimeter;
	@Value("${csv.format.headers}")
	private String headers;
	@Value("$csv.format.dateFormat")
	private String dateFormat;

	@Override
	public List<StockPriceRecordTo> parseCSV(String csv) throws Exception {
		if (csv == null) {
			return new ArrayList<StockPriceRecordTo>();
		}
		CSVFormat csvFormat = CSVFormat.EXCEL
				.withDelimiter(csvFormatDelimeter)
				.withHeader(headers.split(","))
				.withIgnoreEmptyLines();
		if (csv.indexOf("\r\n") < 0) {
			csvFormat = csvFormat.withRecordSeparator("\n");
		}
		return extractSPRs(CSVParser.parse(csv, csvFormat));
	}

	private List<StockPriceRecordTo> extractSPRs(CSVParser csvParser) throws ParseException {
		List<StockPriceRecordTo> sprTos = new ArrayList<>();
		DateFormat dateParser = new SimpleDateFormat(dateFormat);
		for (CSVRecord csvRecord : csvParser) {
			String companyName = "";
			Date dateRecorded = new Date();
			double price = 0d;
			companyName = csvRecord.get("company");
			dateRecorded = dateParser.parse(csvRecord.get("date"));
			price = Double.parseDouble(csvRecord.get("price"));
			CompanyTo companyTo = new CompanyTo(null, companyName);
			sprTos.add(new StockPriceRecordTo(dateRecorded, companyTo, price));
		}
		return sprTos;
	}

	public char getCsvFormatDelimeter() {
		return csvFormatDelimeter;
	}

	public String getHeaders() {
		return headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public void setCsvFormatDelimeter(char csvFormatDelimeter) {
		this.csvFormatDelimeter = csvFormatDelimeter;
	}
}
