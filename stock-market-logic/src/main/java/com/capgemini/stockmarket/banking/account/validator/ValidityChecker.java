package com.capgemini.stockmarket.banking.account.validator;

import com.capgemini.stockmarket.dto.Money;

public interface ValidityChecker {
	boolean validate(Money money);
}
