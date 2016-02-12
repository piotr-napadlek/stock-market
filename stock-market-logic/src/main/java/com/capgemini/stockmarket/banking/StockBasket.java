package com.capgemini.stockmarket.banking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.stockmarket.broker.Stock;
import com.capgemini.stockmarket.broker.StockInfo;
import com.capgemini.stockmarket.dto.CompanyTo;

@Component
public class StockBasket implements Basket {
	private Map<String, Stock> stocks;

	@Override
	public Collection<StockInfo> getStockInfos(CompanyTo company) {
		basketCompanyCheck(company);
		return stocks.values().stream().filter(stock -> stock.company().equals(company))
				.map(stock -> stock.getInfo()).collect(Collectors.toList());
	}

	private void basketCompanyCheck(CompanyTo company) {
		if (stocks.values().stream().anyMatch(stock -> stock.company().equals(company))) {
			throw new BankOperationException(
					"No such company in basket: " + company.getName());
		}
	}

	@Override
	@Transactional
	public boolean putStocks(Collection<Stock> stock) {
		stock.forEach(s -> {
			checkStockExistance(s);
			stocks.put(s.getStockId(), s);
		});
		return true;
	}

	@Override
	@Transactional
	public Collection<Stock> extractStocks(Collection<StockInfo> stockInfos) {
		List<Stock> extracted = new ArrayList<>();
		stockInfos.forEach(stockInfo -> {
			checkStockNonExistance(stockInfo);
			extracted.add(stocks.remove(stockInfo.getStockId()));
		});
		return extracted;
	}

	private void checkStockNonExistance(StockInfo stockToCheck) {
		if (stocks.containsKey(stockToCheck.getStockId()) == false) {
			throw new BankOperationException(
					"No such stock in basket. Stock id: " + stockToCheck.getStockId());
		}
	}

	private void checkStockExistance(Stock s) {
		if (stocks.containsKey(s.getStockId())) {
			throw new BankOperationException(
					"Something went wrong. There is already a stock with such id in basket");
		}
	}

	@Override
	public Collection<CompanyTo> getAvailableCompanies() {
		return stocks.values().stream().map(stock -> stock.company()).distinct()
				.collect(Collectors.toList());
	}

}
