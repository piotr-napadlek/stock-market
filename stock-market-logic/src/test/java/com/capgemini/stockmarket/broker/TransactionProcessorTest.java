package com.capgemini.stockmarket.broker;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.stream.IntStream;

import static org.mockito.Matchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.capgemini.stockmarket.broker.datamanager.StockPriceInformer;
import com.capgemini.stockmarket.broker.processor.DefaultTransactionProcessor;
import com.capgemini.stockmarket.broker.processor.StockCertifier;
import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.Money;
import com.capgemini.stockmarket.dto.transactions.Stock;
import com.capgemini.stockmarket.dto.transactions.TxAccept;
import com.capgemini.stockmarket.dto.transactions.TxOffer;
import com.capgemini.stockmarket.dto.transactions.TxRequest;
import com.capgemini.stockmarket.settings.BrokersOfficeSettings;

@RunWith(MockitoJUnitRunner.class)
public class TransactionProcessorTest {
	private CompanyTo mockCompany = new CompanyTo(1L, "Mock");
	private double mockPrice = 100d;

	@InjectMocks
	private DefaultTransactionProcessor processor;
	@Mock
	private StockCertifier certifier;
	@Mock
	private StockPriceInformer informer;
	@Spy
	private BrokersOfficeSettings settings = new BrokersOfficeSettings();

	@Before
	public void setUp() {
		when(informer.getCurrentStockPrice(any(CompanyTo.class))).thenReturn(mockPrice);
		when(certifier.confirmStockValidity(any(Stock.class))).thenReturn(true);
		when(certifier.provideCertifiedStock(any(), anyDouble()))
				.thenAnswer(new Answer<Stock>() {
					@Override
					public Stock answer(InvocationOnMock invocation) throws Throwable {
						CompanyTo company = invocation.getArgumentAt(0, CompanyTo.class);
						Double price = invocation.getArgumentAt(1, Double.class);
						return Stock.createStock("xy", company, new Date(), price,
								company.stockCurrency());
					}
				});
		when(certifier.cashStock(any(Stock.class), anyDouble())).then(new Answer<Money>() {
			@Override
			public Money answer(InvocationOnMock invocation) throws Throwable {
				return new Money(invocation.getArgumentAt(0, Stock.class).currencyBought(),
						invocation.getArgumentAt(1, Double.class), "xx");
			}
		});
	}

	@Test
	public void basicTestShouldGetOfferFromProcessor() {
		offerCorrectnessCheck(100, 10);
	}

	@Test
	public void offerRepeatabilityTestShouldGetCorrectValueForEachRequest() {
		IntStream.range(0, 800).forEach(i -> {
			IntStream.range(0, 50).forEach(j -> {
				offerCorrectnessCheck(i, j);
			});
		});
	}
	
	@Test
	public void shouldHandleMultipleCompaniesInRequest() {
		// given
		TxRequest request = new TxRequest();
		request.addBuyRequest(new CompanyTo(1L, "name"), 80);
		request.addBuyRequest(new CompanyTo(2L, "other"), 100);
		request.addBuyRequest(new CompanyTo(3L, "third"), 1);
		request.addSellRequest(new CompanyTo(4L, "fourth"), 10);
		// then
		offerCorrectnessCheck(request);
	}
	
	@Test
	public void shouldCalculateTransactionFee() {
		// given
		TxRequest request = new TxRequest();
		request.addBuyRequest(new CompanyTo(2L, "other"), 100);
		request.addSellRequest(new CompanyTo(3L, "third"), 100);
		// when
		TxOffer offer = offerCorrectnessCheck(request);
		TxAccept accept = acceptAll(offer);
		double fee = processor.getTransactionFee(accept).getRight();
		// then
		assertEquals(90d, fee, 10d); // TODO pnapadle: parametrize this
	}
	
 	private TxOffer offerCorrectnessCheck(int buyAmount, int sellAmount) {
		// given
		TxRequest request = new TxRequest();
		request.addBuyRequest(mockCompany, buyAmount);
		request.addSellRequest(mockCompany, sellAmount);
		// then
		return offerCorrectnessCheck(request);
	}

	private TxOffer offerCorrectnessCheck(TxRequest request) {
		// when
		TxOffer offer = processor.prepareOffer(request);
		// then
		assertNotNull(offer);
		assertFalse(offer.getBuyOffers().isEmpty());
		assertFalse(offer.getSellOffers().isEmpty());
		assertNotNull(offer.getBuyOfferFor(mockCompany));
		assertNotNull(offer.getSellOfferFor(mockCompany));

		request.getBuyRequests().keySet().forEach(mockCompany -> {
			assertTrue(offer.getSellOfferFor(mockCompany)
					.getLeft() >= (int) (settings.getMinSellAvailability()
							* request.getRequestedBuyAmountFor(mockCompany)));
			assertTrue(offer.getSellOfferFor(mockCompany)
					.getLeft() <= (int) (settings.getMaxSellAvailability()
							* request.getRequestedBuyAmountFor(mockCompany)));
			assertTrue(offer.getSellOfferFor(mockCompany).getRight() > settings.getMinSellOffer()
					* mockPrice);
			assertTrue(offer.getSellOfferFor(mockCompany).getRight() < settings.getMaxSellOffer()
					* mockPrice);
		});
		
		request.getSellRequests().keySet().forEach(mockCompany -> {
			assertTrue(offer.getBuyOfferFor(mockCompany)
					.getLeft() >= (int) (settings.getMinBuyAvailability()
							* request.getRequestedSellAmountFor(mockCompany)));
			assertTrue(offer.getBuyOfferFor(mockCompany)
					.getLeft() <= (int) (settings.getMaxBuyAvailability()
							* request.getRequestedSellAmountFor(mockCompany)));
			
			assertTrue(offer.getBuyOfferFor(mockCompany).getRight() > settings.getMinBuyOffer()
					* mockPrice);
			assertTrue(offer.getBuyOfferFor(mockCompany).getRight() < settings.getMaxBuyOffer()
					* mockPrice);
		});
		
		return offer;
	}

	private TxAccept acceptAll(TxOffer offer) {
		TxAccept accept = new TxAccept();
		offer.getBuyOffers().forEach((company, amountPrice) -> {
			accept.addSellAccept(company, amountPrice.getLeft(), amountPrice.getRight());
		});
		offer.getSellOffers().forEach((company, amountPrice) -> {
			accept.addBuyAccept(company, amountPrice.getLeft(), amountPrice.getRight());
		});
		return accept;
	}
}
