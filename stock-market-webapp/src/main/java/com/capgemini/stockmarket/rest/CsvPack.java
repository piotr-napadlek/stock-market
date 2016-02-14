package com.capgemini.stockmarket.rest;

public class CsvPack {

	private String csv;
	private String delimeter;
	private String headers;
	private String dateFormat;
	
	public CsvPack() {
	}
	
	public CsvPack(String csv) {
		this.csv = csv;
	}
	
	public CsvPack(String csv, String delimeter, String headers) {
		this.csv = csv;
		this.delimeter = delimeter;
		this.headers = headers;
	}

	public String getCsv() {
		return csv;
	}

	public void setCsv(String csv) {
		this.csv = csv;
	}

	public String getDelimeter() {
		return delimeter;
	}

	public void setDelimeter(String delimeter) {
		this.delimeter = delimeter;
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

}
