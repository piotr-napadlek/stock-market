package com.capgemini.stockmarket.banking;


import java.io.*;
import java.util.*;

public class BankAccount implements BankAccountInfo {

	private StockBasket stockBasket;
	private Set<CurrencyBalance> balances;

	public boolean putMoney(CurrencyAmount money) {
		
	}

	public boolean putShares(Set<Share> shares) {
		
	}

	public CurrencyAmount extractMoney(Currency currency, double amount) {
		
	}

	public Set<Share> extractShares(List<ShareInfo> shareInfos) {
		
	}

}
