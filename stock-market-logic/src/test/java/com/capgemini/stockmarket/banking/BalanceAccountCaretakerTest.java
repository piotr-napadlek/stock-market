package com.capgemini.stockmarket.banking;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.capgemini.stockmarket.banking.account.caretaker.NationalBankAccountBalanceCaretaker;
import com.capgemini.stockmarket.banking.account.validator.MoneyValidator;
import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.Money;

@RunWith(MockitoJUnitRunner.class)
public class BalanceAccountCaretakerTest {

	@InjectMocks
	private NationalBankAccountBalanceCaretaker caretaker;
	@Mock
	private MoneyValidator validator;

	@Before
	public void setUp() {
		when(validator.validate(Mockito.any(Money.class))).thenReturn(true);
		when(validator.createMoney(Mockito.any(Currency.class), anyDouble()))
				.thenAnswer(new Answer<Money>() {
					@Override
					public Money answer(InvocationOnMock invocation) throws Throwable {
						return new Money(invocation.getArgumentAt(0, Currency.class),
								invocation.getArgumentAt(1, Double.class),
								"trust-me-these-money-are-legal");
					}
				});
	}

	@Test
	public void shouldInitializeBalances() {
		// given
		Money money = new Money(Currency.PLN, 10_000, "oh-i-am-legal");
		// when
		caretaker.putMoney(money);
		// then
		assertEquals(1, caretaker.getAvailableCurrencies().size());
		assertEquals(10_000, caretaker.getBalanceFor(Currency.PLN), 0.00001d);
	}
	
	@Test
	public void shouldGetMeSomeMoney() {
		// given
		Money money = new Money(Currency.PLN, 10_000, "oh-i-am-legal");
		// when
		caretaker.putMoney(money);
		Money moneyFromAccount = caretaker.extractMoney(Currency.PLN, 4000d);
		// then
		assertEquals(4000, moneyFromAccount.getAmount(), 0.00001d);
		assertTrue(moneyFromAccount.getBankSignature().contains("trust-me"));
		assertEquals(6000d, caretaker.getBalanceFor(Currency.PLN), 0.00001d);
		verify(validator, times(1)).createMoney(Currency.PLN, 4000d);
		verify(validator, times(1)).validate(money);
		// when
		try {
			caretaker.extractMoney(Currency.PLN, 7000d);
			fail("Account shouldnt allow a debit");
		} catch (Exception e) {
			assertTrue(e instanceof BankOperationException);
			assertTrue(e.getMessage().contains("Not enough"));
			verify(validator, times(1)).createMoney(Currency.PLN, 4000d);
		}
	}
	
	@Test
	public void shouldHandleMultipleCurrencies() {
		// given
		Money money = new Money(Currency.PLN, 10_000, "oh-i-am-legal");
		Money euro = new Money(Currency.EUR, 2_000, "oh-i-am-legal");
		// when
		caretaker.putMoney(money);
		caretaker.putMoney(euro);
		// then
		assertEquals(2, caretaker.getAvailableCurrencies().size());
		assertEquals(10000d, caretaker.getBalanceFor(Currency.PLN), 0.00001d);
		assertEquals(2000d, caretaker.getBalanceFor(Currency.EUR), 0.000001d);
		// when
		Money extracted = caretaker.extractMoney(Currency.EUR, 999.99);
		// then
		assertEquals(999.99d, extracted.getAmount(), 0.0001d);
		assertEquals(1000.01d, caretaker.getBalanceFor(Currency.EUR), 0.00001d);
		verify(validator, times(1)).createMoney(any(), anyDouble());
		verify(validator, times(2)).validate(any());
	}
	
	@Test (expected = BankOperationException.class)
	public void shouldClearAccount() {
		// given
		Money money = new Money(Currency.PLN, 10_000, "oh-i-am-legal");
		Money euro = new Money(Currency.EUR, 2_000, "oh-i-am-legal");
		// when
		caretaker.putMoney(money);
		caretaker.putMoney(euro);
		caretaker.clearAccount();
		// then
		caretaker.getBalanceFor(Currency.PLN);
	}

}
