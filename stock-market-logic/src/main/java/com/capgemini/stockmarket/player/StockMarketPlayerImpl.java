package com.capgemini.stockmarket.player;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.banking.BankAccount;
import com.capgemini.stockmarket.banking.Currency;
import com.capgemini.stockmarket.banking.CurrencyAmount;
import com.capgemini.stockmarket.broker.BrokersOfficeDesk;
import com.capgemini.stockmarket.broker.Stock;
import com.capgemini.stockmarket.common.DateInfo;
import com.capgemini.stockmarket.common.IllegalOperationException;
import com.capgemini.stockmarket.common.StockTransactionInfo;
import com.capgemini.stockmarket.dto.TransactionObjectTo;
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
			RequestCompositor compositor) {
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
		TransactionObjectTo<StockTransactionInfo> transactionRequest = compositor.composeRequest(account,
				brokersOfficeDesk, dateInfo.getCurrentDate());
		TransactionObjectTo<StockTransactionInfo> transactionOffer = brokersOfficeDesk
				.processRequest(transactionRequest);
		setState(PlayerState.VERIFYING);
		TransactionObjectTo<StockTransactionInfo> transactionAccept = compositor
				.verifyTransactionOffer(transactionOffer);
		TransactionObjectTo<Stock> transaction = brokersOfficeDesk.processAcceptance(transactionAccept);
		digestTransaction(transaction);
	}

	private void digestTransaction(TransactionObjectTo<Stock> transaction) {
		
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
		if (PlayerState.NEW.equals(this.state)) {
			account.clearAccount();
			CurrencyAmount money = new CurrencyAmount(Currency.valueOf(settings.getCurrency()),
					settings.getBaseBalance());
			account.putMoney(money);
			settings.getAdditionalBalances().forEach((currency, balance) -> account
					.putMoney(new CurrencyAmount(Currency.valueOf(currency), balance)));
		}
	}
}
