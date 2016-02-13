package com.capgemini.stockmarket.banking.account.basket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.stockmarket.banking.BankOperationException;
import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.transactions.Stock;
import com.capgemini.stockmarket.dto.transactions.StockInfo;

@Component
public class StockBasket implements Basket {
	private Map<String, Stock> stocks = new HashMap<>();

	@Override
	public List<StockInfo> getStockInfos(CompanyTo company) {
		basketCompanyCheck(company);
		return stocks.values().stream().filter(stock -> stock.getCompany().equals(company))
				.map(stock -> stock.getInfo()).collect(Collectors.toList());
	}

	private void basketCompanyCheck(CompanyTo company) {
		if (stocks.values().stream()
				.anyMatch(stock -> stock.getCompany().equals(company)) == false) {
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
	public List<Stock> extractStocks(Collection<StockInfo> stockInfos) {
		stockInfos.forEach(stockInfo -> checkStockNonExistance(stockInfo));
		List<Stock> extracted = new ArrayList<>();
		stockInfos.forEach(stockInfo -> extracted.add(stocks.remove(stockInfo.getStockId())));
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
	public List<CompanyTo> getAvailableCompanies() {
		return stocks.values().stream().map(stock -> stock.getCompany()).distinct()
				.collect(Collectors.toList());
	}

	@Override
	public void clearBasket() {
		this.stocks.clear();
	}

	@Override
	public List<Stock> extractStock(CompanyTo company, int amount) {
		List<StockInfo> stockInfos = getStockInfos(company);
		if (stockInfos.size() < amount) {
			throw new BankOperationException(
					"Not enough stock in basket for requested company. Company "
							+ company.getName() + ", amount " + amount);
		}
		return extractStocks(
				stockInfos.subList(0, amount).stream().collect(Collectors.toList()));
	}
}
