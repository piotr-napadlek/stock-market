package com.capgemini.stockmarket.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capgemini.stockmarket.entity.StockPriceRecordEntity;
import com.capgemini.stockmarket.entity.StockPriceRecordPK;


public interface StockPriceRecordRepository extends JpaRepository<StockPriceRecordEntity, Long> {

	@Query("select record from StockPriceRecordEntity record where record.date = :dateRecorded ")
	public List<StockPriceRecordEntity> findByDate(@Param("dateRecorded") Date dateRecorded);
	
	@Query("select record from StockPriceRecordEntity record where upper(record.company.name) = upper(:company)")
	public List<StockPriceRecordEntity> findByCompanyName(@Param("company") String companyName);
	
	@Query("select record from StockPriceRecordEntity record where record.company.id = :id ")
	public List<StockPriceRecordEntity> findByCompanyId( @Param("id") Long companyId);
	
	@Query("select record from StockPriceRecordEntity record where record.date <= :date ")
	public List<StockPriceRecordEntity> findAllTillDate(@Param("date") Date tillDate);
	
	@Query("select record from StockPriceRecordEntity record where record.date between :fromDate and :tillDate ")
	public List<StockPriceRecordEntity> findBetweenDates(@Param("fromDate") Date fromDate, @Param("tillDate") Date tillDate);
	
	@Query("select record from StockPriceRecordEntity record where record.date between :fromDate and :tillDate and upper(record.company.name) = upper(:name)")
	public List<StockPriceRecordEntity> findByCompanyNameBetweenDates(@Param("name") String companyName, @Param("fromDate") Date fromDate, @Param("tillDate") Date tillDate);
}
