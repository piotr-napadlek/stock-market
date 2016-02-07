package com.capgemini.stockmarket.entity;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "STOCK_PRICE")
@IdClass(StockPriceRecordPK.class)
@Access(AccessType.FIELD)
public class StockPriceRecordEntity {
	
	@Id
	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	private CompanyEntity company;
	@Id
	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(nullable = false)
	private double price = 0d;
	
	// for Hibernate
	public StockPriceRecordEntity() {
	}
	
	public StockPriceRecordEntity(CompanyEntity company, Date date, double price) {
		this.company = company;
		this.date = date;
		this.price = price;
	}

	public CompanyEntity getCompany() {
		return company;
	}

	public void setCompany(CompanyEntity company) {
		this.company = company;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
