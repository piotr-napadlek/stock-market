package com.capgemini.stockmarket.banking;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.Money;

@Component("nationalBankValidator")
final class BankValidator implements Validator {
	private Map<String, Money> validMoney = new ConcurrentHashMap<>();
	
	@Override
	public Money createMoney(Currency currency, double amount) {
		final String signature = createSignature();
		Money newMoney = new Money(currency, amount, signature);
		validMoney.put(signature, newMoney);
		return newMoney;
	}

	@Override
	public boolean validate(Money money) {
		if (money.getBankSignature().equals("Initialization money")) {
			return true;
		}
		if (validMoney.containsKey(money.getBankSignature())) {
			 validMoney.remove(money.getBankSignature());
			return true;
		} else {
			return false;
		}
	}

	private String createSignature() {
		return UUID.randomUUID().toString();
	}
}
