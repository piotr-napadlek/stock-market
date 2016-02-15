package com.capgemini.stockmarket.player;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.capgemini.stockmarket.banking.account.BankAccount;
import com.capgemini.stockmarket.broker.BrokersOfficeDesk;
import com.capgemini.stockmarket.common.DateInfo;
import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.Money;
import com.capgemini.stockmarket.dto.transactions.StockInfo;
import com.capgemini.stockmarket.dto.transactions.TxAccept;
import com.capgemini.stockmarket.dto.transactions.TxOffer;
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
		when(brokersOfficeDesk.processRequest(any())).thenReturn(new TxOffer());
		when(brokersOfficeDesk.processAccept(any())).thenReturn(Optional.ofNullable(null));
	}

	@Test
	public void shouldCalculatePlayersWorthWhenNoStocks() {
		// given
		when(account.getBalanceFor(any())).thenReturn(8000d);
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
		companies.add(new CompanyTo(2L, "2"));
		@SuppressWarnings("unchecked")
		List<StockInfo> mockList = Mockito.mock(List.class);

		when(mockList.size()).thenReturn(2);
		when(account.getBalanceFor(any())).thenReturn(8000d);
		when(account.getAvailableStockCompanies()).thenReturn(companies);
		when(account.getStockInfos(any())).thenReturn(mockList);
		when(brokersOfficeDesk.getTodaysPriceFor(new CompanyTo(1L, "1"))).thenReturn(20d);
		when(brokersOfficeDesk.getTodaysPriceFor(new CompanyTo(2L, "2"))).thenReturn(10d);
		// 8000 + 2 * 20 + 2 * 10 = 8060
		// when
		double worth = player.estimateDefaultCurrencyWorth();
		// then
		assertEquals(8060d, worth, 0.00000001d);
		verify(brokersOfficeDesk, times(2)).getTodaysPriceFor(any());
	}

	@Test
	public void shouldCheckIfSettingsApply() {
		// given
		PlayerSettings settings = new PlayerSettings();
		settings.setBaseBalance(19999d);
		settings.setPlayerName("other");
		reset(account);
		// when
		player.setSettings(settings);
		// then
		assertEquals("other", player.getName());
		verify(account, atLeastOnce()).clearAccount();
		ArgumentCaptor<Money> captor = ArgumentCaptor.forClass(Money.class);
		verify(account, times(1)).putMoney(captor.capture());
		assertEquals(captor.getValue().getCurrency(),
				Currency.valueOf(settings.getCurrency()));
		assertEquals(captor.getValue().getAmount(), 19999d, 0.0000001);
	}
}
