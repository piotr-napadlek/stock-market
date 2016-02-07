package com.capgemini.stockmarket.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.capgemini.stockmarket.entity.StockPriceRecordEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/test-database-context.xml" })
public class StockPriceRecordRepositoryTest {
	private static List<StockPriceRecordEntity> allRecords;

	@Inject
	private StockPriceRecordRepository stockPriceRecordRepository;

	@Before
	public void getAllRecords() {
		if (allRecords == null) {
			allRecords = stockPriceRecordRepository.findAll();
		}
	}

	@Test
	public void shouldCheckThatRepositoryExists() {
		Assert.assertNotNull(stockPriceRecordRepository);
	}

	@Test
	public void shouldFindThatThereAreRowsInDB() {
		Assert.assertFalse(stockPriceRecordRepository.findAll().isEmpty());
	}

	@Test
	public void shouldFindStockRecordByDate() throws ParseException {
		// given
		Date date = new SimpleDateFormat("yyyyMMdd").parse("20160212");
		// when
		List<StockPriceRecordEntity> stockPrices = stockPriceRecordRepository.findByDate(date);
		List<StockPriceRecordEntity> javaFiltered = allRecords.stream()
				.filter(stock -> stock.getDate().equals(date)).collect(Collectors.toList());
		// then
		Assert.assertNotNull(stockPrices);
		Assert.assertEquals(javaFiltered.size(), stockPrices.size());
		Assert.assertTrue(date.equals(stockPrices.get(0).getDate()));
	}

	@Test
	public void shouldFindRecordsByCompanyName() {
		// given
		final String name = "Microsoft";
		// when
		List<StockPriceRecordEntity> stockPrices = stockPriceRecordRepository
				.findByCompanyName(name);
		List<StockPriceRecordEntity> javaFiltered = allRecords.stream().filter(
				stock -> stock.getCompany().getName().toLowerCase().equals(name.toLowerCase()))
				.collect(Collectors.toList());

		// then
		Assert.assertNotNull(stockPrices);
		Assert.assertEquals(javaFiltered.size(), stockPrices.size());
	}


	@Test
	public void shouldFindAllTillDate() throws ParseException {
		// given
		Date date = new SimpleDateFormat("yyyyMMdd").parse("20160213");
		// when
		List<StockPriceRecordEntity> stockPrices = stockPriceRecordRepository
				.findAllTillDate(date);
		List<StockPriceRecordEntity> javaFiltered = allRecords.stream()
				.filter(stock -> stock.getDate().compareTo(date) <= 0)
				.collect(Collectors.toList());
		// then
		Assert.assertNotNull(stockPrices);
		Assert.assertFalse(stockPrices.isEmpty());
		Assert.assertEquals(javaFiltered.size(), stockPrices.size());
		stockPrices.forEach(stock -> Assert.assertTrue(stock.getDate().compareTo(date) <= 0));
	}

	@Test
	public void shouldFindAllBetweenDate() throws ParseException {
		// given
		Date tillDate = new SimpleDateFormat("yyyyMMdd").parse("20160213");
		Date fromDate = new SimpleDateFormat("yyyyMMdd").parse("20160212");

		// when
		List<StockPriceRecordEntity> stockPrices = stockPriceRecordRepository
				.findBetweenDates(fromDate, tillDate);
		List<StockPriceRecordEntity> javaFiltered = allRecords.stream()
				.filter(stock -> stock.getDate().compareTo(tillDate) <= 0
						&& stock.getDate().compareTo(fromDate) >= 0)
				.collect(Collectors.toList());
		// then
		Assert.assertNotNull(stockPrices);
		Assert.assertEquals(javaFiltered.size(), stockPrices.size());
		stockPrices.forEach(stock -> Assert.assertTrue(stock.getDate().compareTo(tillDate) <= 0
				&& stock.getDate().compareTo(fromDate) >= 0));
	}

	@Test
	public void shouldFindAllBetweenDateByCompanyName() throws ParseException {
		// given
		final Date tillDate = new SimpleDateFormat("yyyyMMdd").parse("20160213");
		final Date fromDate = new SimpleDateFormat("yyyyMMdd").parse("20160212");
		final String name = "Intel";
		// when
		List<StockPriceRecordEntity> stockPrices = stockPriceRecordRepository
				.findByCompanyNameBetweenDates(name, fromDate, tillDate);
		List<StockPriceRecordEntity> javaFiltered = allRecords
				.stream().filter(
						stock -> stock.getDate().compareTo(tillDate) <= 0
								&& stock.getDate().compareTo(fromDate) >= 0
								&& stock.getCompany().getName().toLowerCase()
										.equals(name.toLowerCase()))
				.collect(Collectors.toList());
		// then
		Assert.assertNotNull(stockPrices);
		Assert.assertEquals(javaFiltered.size(), stockPrices.size());
		stockPrices.forEach(stock -> Assert.assertTrue(stock.getDate().compareTo(tillDate) <= 0
				&& stock.getDate().compareTo(fromDate) >= 0
				&& stock.getCompany().getName().toLowerCase().equals(name.toLowerCase())));
	}
}
