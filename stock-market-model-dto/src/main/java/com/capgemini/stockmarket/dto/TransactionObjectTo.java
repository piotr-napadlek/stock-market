package com.capgemini.stockmarket.dto;

import java.util.HashSet;
import java.util.Set;

public class TransactionObjectTo <T> {

	private Set<T> buyItems = new HashSet<T>();
	private Set<T> sellItems = new HashSet<T>();
	private String signature;
	
	public boolean addBuyItem(T buyItem) {
		return buyItems.add(buyItem);
	}
	
	public boolean addSellItem(T sellItem) {
		return sellItems.add(sellItem);
	}
	
	public Set<T> getBuyItems() {
		return buyItems;
	}
	
	public Set<T> getSellItems() {
		return sellItems;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}
