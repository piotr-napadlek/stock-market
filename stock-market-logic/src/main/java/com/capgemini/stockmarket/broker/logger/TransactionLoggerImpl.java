package com.capgemini.stockmarket.broker.logger;

import java.text.DecimalFormat;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.common.DateInfo;
import com.capgemini.stockmarket.dto.transactions.TxFromBO;
import com.capgemini.stockmarket.dto.transactions.TxFromPlayer;

@Component
public class TransactionLoggerImpl implements TransactionLogger {
	private static final Log LOG = LogFactory.getLog(TransactionLogger.class);

	private DateInfo currentDate;

	@Inject
	public TransactionLoggerImpl(DateInfo currentDate) {
		this.currentDate = currentDate;
	}

	@Override
	public void logTransaction(TxFromBO response, TxFromPlayer accept) {
		DecimalFormat df = new DecimalFormat("#.00");
		StringBuilder builder = new StringBuilder(
				"\n==================================================================================")
						.append("\nZarejestrowano transakcje gracza w dniu: ")
						.append(currentDate.getCurrentDate().toString());
		builder.append("\nNazwa gracza: ").append(accept.getSignature())
				.append("\nSprzedane akcje: ");
		accept.getSoldStocks().forEach((company, stockList) -> {
			builder.append(company.getName()).append(": ").append(stockList.size())
					.append(" po cenie ")
					.append(df.format(response.getMoneyFor(company).get(0).getAmount())).append(" ")
					.append(stockList.get(0).currencyBought().toString()).append(", ");
		});
		builder.append("\nKupione akcje: ");
		response.getStocksBought().stream()
				.collect(Collectors.groupingBy(stock -> stock.getCompany()))
				.forEach((company, stockList) -> {
					builder.append(company.getName()).append(": ").append(stockList.size())
							.append(" po cenie: ")
							.append(df.format(stockList.get(0).priceBought())).append(" ")
							.append(stockList.get(0).currencyBought().toString()).append(", ");
				});
		builder.append("\nProwizja dla biura maklerskiego wyniosła ")
				.append(df.format(accept.getTransactionFee().getAmount())).append(" ")
				.append(accept.getTransactionFee().getCurrency().toString()).append("\n")
				.append("-----------------------------------------------------------------------------------\n");
		LOG.info(builder.toString());
	}

}
