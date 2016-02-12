package com.capgemini.stockmarket.banking;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.capgemini.stockmarket.banking.account.basket.Basket;
import com.capgemini.stockmarket.banking.account.basket.StockBasket;
import com.capgemini.stockmarket.broker.Stock;
import com.capgemini.stockmarket.broker.StockInfo;
import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.Currency;

@RunWith(MockitoJUnitRunner.class)
public class StockBasketTest {
	private Basket basket = new StockBasket();
	private Stock stock = Stock.createStock("XX", new CompanyTo(1L, "Microsoft"), new Date(), 1,
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
