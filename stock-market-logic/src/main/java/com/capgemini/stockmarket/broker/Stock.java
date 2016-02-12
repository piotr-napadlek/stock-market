package com.capgemini.stockmarket.broker;

import java.util.Date;
import java.util.UUID;

import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.Money;

public final class Stock implements StockInfo {
	private CompanyTo company;
	private double priceBought = 0;
	private Currency currencyBought;
	private Date dateBought;
	private String id;
	private String signature;

	private Stock() {
	}

	private Stock(Date dateBought, CompanyTo company, double priceBought, Currency currency,
			String signature) {
		this.dateBought = new Date(dateBought.getTime());
		this.company = new CompanyTo(company.getId().longValue(), company.getName());
		this.priceBought = priceBought;
		this.currencyBought = currency;
		this.signature = signature;
		this.id = UUID.randomUUID().toString();
	}

	public static Stock createShare(String signature, CompanyTo company, Date date,
			double price, Currency currency) {
		return new Stock(date, company, price, currency, signature);
	}

	@Override
	public Date dateBought() {
		return null;
	}

	@Override
	public CompanyTo company() {
		return null;
	}

	@Override
	public Money priceBought() {
		return null;
	}

	@Override
	public String getStockId() {
		// TODO Auto-generated method stub
		return null;
	}

	public StockInfo getInfo() {
		return new Stock(this.dateBought, this.company, this.priceBought, this.currencyBought,
				this.id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Stock)) {
			return false;
		}
		Stock other = (Stock) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public String getSignature() {
		return signature;
	}

}
