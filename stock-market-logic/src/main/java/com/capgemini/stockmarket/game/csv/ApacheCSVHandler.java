package com.capgemini.stockmarket.game.csv;

import java.text.DateFormat;
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
		CSVFormat csvFormat = CSVFormat.EXCEL
				.withDelimiter(csvFormatDelimeter)
				.withHeader(headers.split(","))
				.withIgnoreEmptyLines();
		if (csv.indexOf("\r\n") < 0) {
			csvFormat = csvFormat.withRecordSeparator("\n");
		}
		CSVParser csvParser = CSVParser.parse(csv, csvFormat);
		DateFormat dateParser = new SimpleDateFormat(dateFormat);

		List<StockPriceRecordTo> sprTos = new ArrayList<>();

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
