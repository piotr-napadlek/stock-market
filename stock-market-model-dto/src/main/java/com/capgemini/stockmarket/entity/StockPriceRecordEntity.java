package com.capgemini.stockmarket.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "STOCK_PRICE")
public class StockPriceRecordEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(optional = false)
	private CompanyEntity company;
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
