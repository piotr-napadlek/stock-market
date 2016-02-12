package com.capgemini.stockmarket.banking;

import com.capgemini.stockmarket.dto.Money;

public interface ValidityChecker {
	boolean validate(Money money);
}
