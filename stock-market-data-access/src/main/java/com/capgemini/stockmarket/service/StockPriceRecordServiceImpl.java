package com.capgemini.stockmarket.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.dozer.Mapper;

import com.capgemini.stockmarket.dto.StockPriceRecordTo;
import com.capgemini.stockmarket.repository.StockPriceRecordRepository;

public class StockPriceRecordServiceImpl implements StockPriceRecordService {

	@Inject
	private StockPriceRecordRepository sprRepository;
	@Inject
	private CompanyService companyService;
	@Inject
	private Mapper mapper;

	@Override
	public List<StockPriceRecordTo> findAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockPriceRecordTo> findByDate(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockPriceRecordTo> findTillDate(Date tillDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockPriceRecordTo> findBetweenDates(Date fromDate, Date tillDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockPriceRecordTo> findByCompanyName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockPriceRecordTo> findByCompanyId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockPriceRecordTo> findByCompanyNameBetweenDates(String companyName,
			Date fromDate, Date tillDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StockPriceRecordTo findByCompanyNameAndExactDay(String companyName, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StockPriceRecordTo saveOne(StockPriceRecordTo spr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockPriceRecordTo> saveAll(List<StockPriceRecordTo> spr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StockPriceRecordTo deleteOne(StockPriceRecordTo spr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StockPriceRecordTo deleteOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockPriceRecordTo> deleteAll(Collection<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StockPriceRecordTo update(StockPriceRecordTo spr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockPriceRecordTo> updateAll(StockPriceRecordTo spr) {
		// TODO Auto-generated method stub
		return null;
	}

}
