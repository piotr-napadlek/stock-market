package com.capgemini.stockmarket.player;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.banking.account.BankAccount;
import com.capgemini.stockmarket.broker.BrokersOfficeDesk;
import com.capgemini.stockmarket.common.DateInfo;
import com.capgemini.stockmarket.common.IllegalOperationException;
import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.Money;
import com.capgemini.stockmarket.dto.transactions.TxAccept;
import com.capgemini.stockmarket.dto.transactions.TxFromBO;
import com.capgemini.stockmarket.dto.transactions.TxFromPlayer;
import com.capgemini.stockmarket.dto.transactions.TxOffer;
import com.capgemini.stockmarket.dto.transactions.TxRequest;
import com.capgemini.stockmarket.player.strategy.PlayerState;
import com.capgemini.stockmarket.player.strategy.RequestCompositor;
import com.capgemini.stockmarket.settings.PlayerSettings;
import com.capgemini.stockmarket.simulation.calendar.PlayersActionListener;

@Component
@Scope(scopeName = "prototype")
public class StockMarketPlayerImpl implements StockMarketPlayer {

	private RequestCompositor compositor;
	private BankAccount account;

	private BrokersOfficeDesk brokersOfficeDesk;
	private PlayerSettings settings;

	private PlayerState state = PlayerState.NEW;
	private DateInfo dateInfo;
	private List<PlayersActionListener> listeners = new ArrayList<>();

	@Override
	public void setCompositor(RequestCompositor compositor) {
		this.compositor = compositor;
	}

	@Inject
	public StockMarketPlayerImpl(BrokersOfficeDesk brokersOfficeDesk, PlayerSettings settings,
			PlayersActionListener listener, DateInfo dateInfo, BankAccount account,
			@Qualifier("default") RequestCompositor compositor) {
		this.brokersOfficeDesk = brokersOfficeDesk;
		this.settings = settings;
		this.listeners.add(listener);
		this.dateInfo = dateInfo;
		this.account = account;
		this.compositor = compositor;
		applySettings();
	}

	@Override
	public StockMarketPlayer setSettings(PlayerSettings settings) {
		if (PlayerState.NEW.equals(state)) {
			this.settings = settings;
			applySettings();
		} else {
			throw new IllegalOperationException(
					"Player settings cannot be changed when the game has begun!");
		}
		return this;
	}

	private final void doTryTransaction() {
		TxRequest request = compositor.composeRequest(account, brokersOfficeDesk,
				dateInfo.getCurrentDate());
		TxOffer offer = brokersOfficeDesk.processRequest(request);
		setState(PlayerState.VERIFYING);
		TxAccept offerVerified = compositor.verifyTransactionOffer(offer);
		Pair<Currency, Double> transactionFee = brokersOfficeDesk
				.getTransactionFee(offerVerified);
		TxFromPlayer accept = account.fillInTransaction(offerVerified, transactionFee);
		if (accept != null) {
			accept.setSignature(getName());
		}
		Optional<TxFromBO> transaction = brokersOfficeDesk
				.processAccept(Optional.ofNullable(accept));

		if (transaction.isPresent()) {
			account.digestTransaction(transaction.get());
		}
	}

	@Override
	public PlayerState getState() {
		return state;
	}

	@Override
	public void setState(PlayerState state) {
		this.state = state;
	}

	@Override
	public void dateChanged() {
		this.state = PlayerState.THINKING;
		notifyStateChanged();
		// actual transaction logic
		doTryTransaction();
		this.state = PlayerState.READY;
		notifyStateChanged();
	}

	private void notifyStateChanged() {
		listeners.forEach(listener -> listener.playerStateChanged());
	}

	@Override
	public String getName() {
		return settings.getPlayerName();
	}

	private void applySettings() {
		if (PlayerState.NEW.equals(this.state) && account != null) {
			account.clearAccount();
			Money money = new Money(Currency.valueOf(settings.getCurrency()),
					settings.getBaseBalance(), "Initialization money");
			account.putMoney(money);
			settings.getAdditionalBalances().forEach((currency, balance) -> account.putMoney(
					new Money(Currency.valueOf(currency), balance, "Initialization money")));
		}
	}

	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("#.00");
		final StringBuilder toStr = new StringBuilder("\n\nGracz: " + getName());
		toStr.append("\nDostępne środki:\n");
		account.getAvailableCurrencies().forEach(currency -> {
			toStr.append("Waluta: " + currency.toString() + ", Ilość: "
					+ df.format(account.getBalanceFor(currency)) + ", ");
		});
		toStr.append("\nAkcje w koszyku:\n");

		for (CompanyTo company : account.getAvailableStockCompanies()) {
			double worth = (account.getStockInfos(company).size()
					* brokersOfficeDesk.getTodaysPriceFor(company));
			toStr.append("Company: ").append(company.getName()).append(", amount: ")
					.append(account.getStockInfos(company).size()).append(" o wartości ")
					.append(df.format(worth)).append("\n");
		}
		toStr.append("\nCałkowita wartość konta gracza to ")
				.append(df.format(estimateDefaultCurrencyWorth())).append("PLN\n\n");
		return toStr.toString();
	}

	@Override
	public double estimateDefaultCurrencyWorth() {
		double totalWorth = account.getBalanceFor(account.getDefaultCurrency());
		for (CompanyTo company : account.getAvailableStockCompanies()) {
			double worth = (account.getStockInfos(company).size()
					* brokersOfficeDesk.getTodaysPriceFor(company));
			totalWorth += worth;
		}
		return totalWorth;
	}
}
