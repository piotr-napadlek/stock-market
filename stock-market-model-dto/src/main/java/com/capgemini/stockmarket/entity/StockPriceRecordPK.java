package com.capgemini.stockmarket.entity;

import java.io.Serializable;
import java.util.Date;

public class StockPriceRecordPK implements Serializable {
	private static final long serialVersionUID = 1L;
	private CompanyEntity company;
	private Date date;

	public StockPriceRecordPK() {
	}

	public StockPriceRecordPK(CompanyEntity company, Date date) {
		this.company = company;
		this.date = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof StockPriceRecordPK)) {
			return false;
		}
		StockPriceRecordPK other = (StockPriceRecordPK) obj;
		if (company == null) {
			if (other.company != null) {
				return false;
			}
		} else if (!company.equals(other.company)) {
			return false;
		}
		if (date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!date.equals(other.date)) {
			return false;
		}
		return true;
	}
	
	
}
