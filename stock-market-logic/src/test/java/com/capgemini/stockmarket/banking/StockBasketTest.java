package com.capgemini.stockmarket.banking;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.capgemini.stockmarket.banking.account.basket.Basket;
import com.capgemini.stockmarket.banking.account.basket.StockBasket;
import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.transactions.Stock;
import com.capgemini.stockmarket.dto.transactions.StockInfo;

@RunWith(MockitoJUnitRunner.class)
public class StockBasketTest {
	private Basket basket = new StockBasket();
	private Stock stock = Stock.createStock("XX", new CompanyTo(1L, "Microsoft"), new Date(), 1,
			Currency.PLN);
	private Stock stock2 = Stock.createStock("XX", new CompanyTo(1L, "Microsoft"), new Date(), 1,
			Currency.PLN);
	private Stock stock3 = Stock.createStock("XX", new CompanyTo(1L, "Microsoft"), new Date(), 1,
			Currency.PLN);
	private Stock otherStock = Stock.createStock("YY", new CompanyTo(2L, "Intel"), new Date(), 10,
			Currency.PLN);

	@Test
	public void shouldPerformSomeSimplePutAndExtract() {
		// given
		List<Stock> stockList = new ArrayList<>();
		stockList.add(stock);
		stockList.add(otherStock);
		// when
		basket.putStocks(stockList);
		// then
		assertEquals(2, basket.getAvailableCompanies().size());
		// when
		StockInfo stockInfo = stock.getInfo();
		List<StockInfo> stocksToExtract = new ArrayList<>();
		stocksToExtract.add(stockInfo);
		List<Stock> extracted = basket.extractStocks(stocksToExtract);
		// then
		assertEquals(1, basket.getAvailableCompanies().size());
		assertEquals(10, basket.getStockInfos(new CompanyTo(2L, "Intel")).get(0).priceBought(),
				0d);
		assertEquals(1, extracted.size());
		assertEquals("XX", extracted.get(0).getSignature());
	}
	
	@Test
	public void shouldExtractGivenAmountOfCompanyStock() {
		// given
		List<Stock> stockList = new ArrayList<>();
		stockList.add(stock);
		stockList.add(stock2);
		stockList.add(stock3);
		stockList.add(otherStock);
		CompanyTo microsoft = new CompanyTo(1L, "Microsoft");
		CompanyTo intel = new CompanyTo(2L, "Intel");
		// when
		basket.putStocks(stockList);
		// then
		assertEquals(2, basket.getAvailableCompanies().size());
		assertEquals(3, basket.getStockInfos(microsoft).size());
		// when
		List<Stock> extracted = basket.extractStock(microsoft, 2);
		// then
		assertEquals(2, extracted.size());
		assertEquals(1, basket.getStockInfos(microsoft).size());
		assertEquals(2, basket.getAvailableCompanies().size());
		// when
		List<Stock> intelExtracted = basket.extractStock(intel, 1);
		// then
		assertEquals(1, intelExtracted.size());
		assertEquals(1, basket.getAvailableCompanies().size());
		// when then
		try {
			basket.extractStock(microsoft, 2);
			Assert.fail();
		} catch (Throwable e) {
			Assert.assertTrue(e instanceof BankOperationException);
			Assert.assertTrue(e.getMessage().contains("Not enough stock"));
		}
	}
	
	@Test (expected = BankOperationException.class)
	public void shouldThrowWhenNonExistingStockRequested() {
		// given
		List<Stock> inBasket = new ArrayList<>();
		inBasket.add(stock);
		List<StockInfo> toExtract = new ArrayList<>();
		toExtract.add(otherStock.getInfo());
		// when
		basket.putStocks(inBasket);
		basket.extractStocks(toExtract);
	}
	
	@Test  (expected = BankOperationException.class)
	public void shouldThrowWhenNoSuchCompany() {
		// given
		List<Stock> inBasket = new ArrayList<>();
		inBasket.add(stock);
		CompanyTo company = new CompanyTo();
		// when
		basket.putStocks(inBasket);
		basket.getStockInfos(company);
	}
	
	@Test (expected = BankOperationException.class)
	public void shouldThrowWhenStockAlreadyInBasket() {
		// given
		List<Stock> inBasket = new ArrayList<>();
		inBasket.add(stock);
		// when
		basket.putStocks(inBasket);
		basket.putStocks(inBasket);
	}
}
