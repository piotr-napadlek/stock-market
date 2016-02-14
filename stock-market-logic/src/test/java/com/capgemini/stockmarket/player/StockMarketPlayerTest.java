package com.capgemini.stockmarket.player;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.capgemini.stockmarket.banking.account.BankAccount;
import com.capgemini.stockmarket.broker.BrokersOfficeDesk;
import com.capgemini.stockmarket.common.DateInfo;
import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.transactions.TxAccept;
import com.capgemini.stockmarket.dto.transactions.TxRequest;
import com.capgemini.stockmarket.player.strategy.RequestCompositor;
import com.capgemini.stockmarket.settings.PlayerSettings;

@RunWith(MockitoJUnitRunner.class)
public class StockMarketPlayerTest {

	@InjectMocks
	private StockMarketPlayerImpl player;
	@Mock
	private RequestCompositor compositor;
	@Mock
	private BankAccount account;
	@Mock
	private BrokersOfficeDesk brokersOfficeDesk;
	@Mock
	private DateInfo dateInfo;
	@Spy
	private PlayerSettings settings = new PlayerSettings();
	
	
	@Before
	public void setUp() {
		when(compositor.composeRequest(any(), any(), any())).thenReturn(new TxRequest());
		when(compositor.verifyTransactionOffer(any())).thenReturn(new TxAccept());
	}
	
	@Test
	public void shouldCalculatePlayersWorthWhenNoStocks() {
		// given
		when(account.getBalanceFor(Currency.PLN)).thenReturn(8000d);
		when(account.getAvailableStockCompanies()).thenReturn(new ArrayList<>());
		// when
		double worth = player.estimateDefaultCurrencyWorth();
		// then
		assertEquals(8000d, worth, 0.00000001d);
		verify(brokersOfficeDesk, never()).getTodaysPriceFor(any());
	}
	
	@Test
	public void shouldCalculatePlayersWorthWhenCoupleStocks() {
		// given
		List<CompanyTo> companies = new ArrayList<>();
		companies.add(new CompanyTo(1L, "1"));
		
		when(account.getBalanceFor(Currency.PLN)).thenReturn(8000d);
		when(account.getAvailableStockCompanies()).thenReturn(new ArrayList<>());
		// when
		double worth = player.estimateDefaultCurrencyWorth();
		// then
		assertEquals(8000d, worth, 0.00000001d);
		verify(brokersOfficeDesk, never()).getTodaysPriceFor(any());
	}
}
