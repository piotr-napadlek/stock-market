package com.capgemini.stockmarket.banking;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class NationalBank implements Bank, ApplicationContextAware {
	private Validator validator;
	private List<BankAccount> bankAccounts = new ArrayList<>();
	private ApplicationContext context;

	@Inject
	public NationalBank(ApplicationContext context, @Qualifier("nationalBankValidator") Validator validator) {
		this.validator = validator;
		this.context = context;
	}

	@Override
	public BankAccount createBankAccount() {
		BankAccount account = context.getBean("nationalBankAccount", BankAccount.class);
		bankAccounts.add(account);
		return account;
	}

	public Validator getValiditor() {
		return this.validator;
	}

	@Override
	public ValidityChecker getValidityChecker() {
		return this.validator;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = applicationContext;
	}
}
