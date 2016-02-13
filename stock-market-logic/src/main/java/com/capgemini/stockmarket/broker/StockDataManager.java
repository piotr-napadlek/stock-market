package com.capgemini.stockmarket.broker;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.common.DateInfo;
import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.StockPriceRecordTo;
import com.capgemini.stockmarket.dto.transactions.TxFromBO;
import com.capgemini.stockmarket.dto.transactions.TxFromPlayer;
import com.capgemini.stockmarket.service.CompanyService;
import com.capgemini.stockmarket.service.StockPriceRecordService;

@Component
public class StockDataManager implements StockPriceInformer {
	private static final Log LOG = LogFactory.getLog(StockDataManager.class);

	private DateInfo currentDate;
	private StockPriceRecordService sprService;
	private CompanyService companyService;

	@Inject
	public StockDataManager(DateInfo currentDate, StockPriceRecordService sprService,
			CompanyService companyService) {
		this.currentDate = currentDate;
		this.sprService = sprService;
		this.companyService = companyService;
	}

	public void recordTransaction(TxFromBO response, TxFromPlayer accept) {
		// TODO pnapadle: insert db persistance here
		logTransaction(response, accept);
	}

	private void logTransaction(TxFromBO response, TxFromPlayer accept) {
		StringBuilder builder = new StringBuilder(
				"\nZarejestrowano transakcje gracza w dniu: ")
						.append(currentDate.getCurrentDate().toString());
		builder.append("\nNazwa gracza: ").append(accept.getSignature()).append("\n")
				.append("\nSprzedane akcje: ");
		accept.getSoldStocks().forEach((company, stock) -> {
			builder.append(company.getName()).append(": ").append(stock.size())
					.append(", po cenie ")
					.append(response.getMoneyFor(company).get(0).getAmount())
					.append(", ");
		});
		builder.append("\nKupione akcje: ");
		response.getStocksBought().stream()
				.collect(Collectors.groupingBy(stock -> stock.getCompany()))
				.forEach((company, stockList) -> {
					builder.append(company.getName()).append(": ").append(stockList.size())
							.append(", po cenie: ").append(stockList.get(0).priceBought()).append(", ");
				});
		builder.append("\nProwizja dla biura maklerskiego wyniosła ")
				.append(accept.getTransactionFee().getAmount())
				.append(accept.getTransactionFee().getCurrency().toString()).append("\n");
		LOG.info(builder.toString());
	}

	public List<StockPriceRecordTo> getCompanyStockPriceHistory(CompanyTo company) {
		return sprService.findByCompanyId(company.getId());
	}

	public List<StockPriceRecordTo> getCompanyStockPriceHistory(CompanyTo company,
			Date fromDate) {
		return sprService.findByCompanyNameBetweenDates(company.getName(), fromDate,
				getCurrentDate());
	}

	public List<CompanyTo> getCompanies() {
		return companyService.findAllCompanies();
	}

	public Date getCurrentDate() {
		return currentDate.getCurrentDate();
	}

	@Override
	public double getCurrentStockPrice(CompanyTo company) {
		return sprService.findByCompanyNameAndExactDay(company.getName(), getCurrentDate())
				.getPrice();
	}

}
