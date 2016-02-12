package com.capgemini.stockmarket.banking;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class NationalBank implements Bank {
	private Validator validator;
	private List<BankAccount> bankAccounts = new ArrayList<>();
	
	public NationalBank() {
		this.validator = new BankValidator();
	}
	
	@Override
	@Bean
	@Scope("prototype")
	public BankAccount createBankAccount() {
		BankAccount account = new BankAccount(this.validator);
		bankAccounts.add(account);
		return account;
	}

	@Bean
	@Scope("singleton")
	public Validator getValiditor() {
		return this.validator;
	}

	@Bean
	@Scope("singleton")
	@Override
	public ValidityChecker getValidityChecker() {
		return this.validator;
	}
}
