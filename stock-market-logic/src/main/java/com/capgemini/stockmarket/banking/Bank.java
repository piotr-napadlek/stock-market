package com.capgemini.stockmarket.banking;

import com.capgemini.stockmarket.banking.account.BankAccount;
import com.capgemini.stockmarket.banking.account.validator.ValidityChecker;

public interface Bank {
	BankAccount createBankAccount();
	ValidityChecker getValidityChecker();
}
