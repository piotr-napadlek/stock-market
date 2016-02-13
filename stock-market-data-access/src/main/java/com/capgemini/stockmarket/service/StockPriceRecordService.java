package com.capgemini.stockmarket.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.capgemini.stockmarket.dto.StockPriceRecordTo;
import com.capgemini.stockmarket.entity.StockPriceRecordPK;

public interface StockPriceRecordService {
	// read
	List<StockPriceRecordTo> findAllRecords();
	
	List<StockPriceRecordTo> findByDate(Date date);
	List<StockPriceRecordTo> findTillDate(Date tillDate);
	List<StockPriceRecordTo> findBetweenDates(Date fromDate, Date tillDate);
	
	List<StockPriceRecordTo> findByCompanyName(String name);
	List<StockPriceRecordTo> findByCompanyId(Long id);
	
	List<StockPriceRecordTo> findByCompanyNameBetweenDates(String companyName, Date fromDate, Date tillDate);
	StockPriceRecordTo findByCompanyNameAndExactDay(String companyName, Date date);
	
	// create
	StockPriceRecordTo saveOne(StockPriceRecordTo spr);
	List<StockPriceRecordTo> saveAll(List<StockPriceRecordTo> spr);
	
	// delete
	StockPriceRecordTo deleteOne(StockPriceRecordTo spr);
	StockPriceRecordTo deleteOne(Long id);
	List<StockPriceRecordTo> deleteAll(Collection<Long> ids);
	List<StockPriceRecordTo> deleteAll();

	// update
	StockPriceRecordTo update(StockPriceRecordTo spr);
	List<StockPriceRecordTo> updateAll(List<StockPriceRecordTo> spr);
}
