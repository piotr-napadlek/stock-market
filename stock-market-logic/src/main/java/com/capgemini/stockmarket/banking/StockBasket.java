package com.capgemini.stockmarket.banking;


import java.util.List;
import java.util.Map;
import java.util.Set;

import com.capgemini.stockmarket.broker.Share;
import com.capgemini.stockmarket.broker.ShareInfo;
import com.capgemini.stockmarket.dto.CompanyTo;

public class StockBasket {

	private Map<CompanyTo, List<Share>> shareMap;

	public List<ShareInfo> getSharesInfo(CompanyTo company) {
		return null;
		
	}

	public boolean putShares(CompanyTo company, Set<Share> shares) {
		return false;
		
	}

	public Set<Share> extractShares(List<ShareInfo> shareInfo) {
		return null;
		
	}

}
