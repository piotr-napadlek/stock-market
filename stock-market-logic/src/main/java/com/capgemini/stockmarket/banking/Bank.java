package com.capgemini.stockmarket.banking;

public interface Bank {
	BankAccount createBankAccount();
	ValidityChecker getValidityChecker();
}
