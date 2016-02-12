package com.capgemini.stockmarket.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TransactionObjectTo <B, S> {

	private Set<B> buyItems = new HashSet<B>();
	private Set<S> sellItems = new HashSet<S>();
	private String signature;
	private List<Money> money = new ArrayList<>();
	
	public boolean addBuyItem(B buyItem) {
		return buyItems.add(buyItem);
	}
	
	public boolean addAllBuyItems(Collection<B> buyItems) {
		return buyItems.addAll(buyItems);
	}
	
	public boolean addSellItem(S sellItem) {
		return sellItems.add(sellItem);
	}
	
	public boolean addAllSellItems(Collection<S> sellItems) {
		return sellItems.addAll(sellItems);
	}
	
	public Collection<B> getBuyItems() {
		return buyItems;
	}
	
	public Collection<S> getSellItems() {
		return sellItems;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public void addMoney(Money money) {
		this.money.add(money);
	}

	public List<Money> getMoney() {
		return this.money;
	}
}
