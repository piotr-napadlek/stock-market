package com.capgemini.stockmarket.dto.transactions;

import java.util.HashMap;
import java.util.Map;

import com.capgemini.stockmarket.dto.CompanyTo;

public final class TxRequest {
	private final Map<CompanyTo, Integer> buyRequests = new HashMap<>();
	private final Map<CompanyTo, Integer> sellRequests = new HashMap<>();

	public void addBuyRequest(CompanyTo company, Integer amount) {
		if (this.buyRequests.containsKey(company) == false) {
			this.buyRequests.put(company, amount);
		}
	}
	
	public void addSellRequest(CompanyTo company, Integer amount) {
		if (this.sellRequests.containsKey(company) == false) {
			this.sellRequests.put(company, amount);
		}
	}

	public Map<CompanyTo, Integer> getBuyRequests() {
		return buyRequests;
	}

	public Map<CompanyTo, Integer> getSellRequests() {
		return sellRequests;
	}

	public Integer getRequestedBuyAmountFor(CompanyTo company) {
		return buyRequests.getOrDefault(company, 0);
	}

	public Integer getRequestedSellAmountFor(CompanyTo company) {
		return sellRequests.getOrDefault(company, 0);
	}
}
