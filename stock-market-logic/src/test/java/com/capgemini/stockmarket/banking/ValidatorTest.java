package com.capgemini.stockmarket.banking;

import org.junit.Assert;
import org.junit.Test;

import com.capgemini.stockmarket.banking.account.validator.BankValidator;
import com.capgemini.stockmarket.banking.account.validator.MoneyValidator;
import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.Money;

public class ValidatorTest {
	private MoneyValidator validator= new BankValidator();
	
	@Test
	public void shouldCheckThatMoneyCreatedAreValidOnlyOnce() {
		// given when
		Money money = validator.createMoney(Currency.CHF, 500);
		// then
		Assert.assertTrue(validator.validate(money));
		Assert.assertFalse(validator.validate(money));
	}
}
