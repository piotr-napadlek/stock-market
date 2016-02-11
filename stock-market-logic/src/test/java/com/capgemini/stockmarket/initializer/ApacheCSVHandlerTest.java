package com.capgemini.stockmarket.initializer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.capgemini.stockmarket.dto.StockPriceRecordTo;
import com.capgemini.stockmarket.initializer.csv.ApacheCSVHandler;

public class ApacheCSVHandlerTest {

	private ApacheCSVHandler csvHandler;

	@Before
	public void initHandler() {
		csvHandler = new ApacheCSVHandler();
		csvHandler.setCsvFormatDelimeter(',');
		csvHandler.setDateFormat("yyyyMMdd");
		csvHandler.setHeaders("company,date,price");
	}

	@Test
	public void shouldProperlyReadDataFromCSVFile() throws Exception {
		// given
		String csvContent = new String(Files.readAllBytes(
				Paths.get(getClass().getResource("/csv/stock-history.csv").toURI())));
		Assert.assertNotNull(csvContent);
		Assert.assertFalse(csvContent.isEmpty());
		// when
		List<StockPriceRecordTo> sprList = csvHandler.parseCSV(csvContent);
		// then
		Assert.assertNotNull(sprList);
		Assert.assertEquals(6, sprList.size());
		Assert.assertEquals("INTEL", sprList.get(0).getCompany().getName());
		Assert.assertEquals(101.3d, sprList.get(5).getPrice(), 1e-15);
	}
	
	@Test
	public void shouldHandleEmptyString() throws Exception {
		// given
		final String empty = "";
		// when
		List<StockPriceRecordTo> sprList = csvHandler.parseCSV(empty);
		// then
		Assert.assertNotNull(sprList);
		Assert.assertTrue(sprList.isEmpty());
	}
	
	@Test
	public void shouldHandleNull() throws Exception {
		// given
		// when
		List<StockPriceRecordTo> sprList = csvHandler.parseCSV(null);
		// then
		Assert.assertNotNull(sprList);
		Assert.assertTrue(sprList.isEmpty());
	}
}
