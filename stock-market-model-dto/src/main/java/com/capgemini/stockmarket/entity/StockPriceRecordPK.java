package com.capgemini.stockmarket.entity;

import java.io.Serializable;
import java.util.Date;

public class StockPriceRecordPK implements Serializable {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private CompanyEntity company;
	@SuppressWarnings("unused")
	private Date date;
}
