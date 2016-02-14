package com.capgemini.stockmarket.simulation.logger;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.capgemini.stockmarket.broker.BrokersOffice;
import com.capgemini.stockmarket.common.DateInfo;
import com.capgemini.stockmarket.simulation.players.PlayersInfo;
import com.capgemini.stockmarket.simulation.state.SimulationStateInfo;

@Component
public class SimulationLogerImpl implements SimulationLogger {
	private static final Log LOG = LogFactory.getLog(SimulationLogger.class);
	
	private PlayersInfo playersManager;
	private BrokersOffice defaultBO;
	private DateInfo dateInfo;
	private SimulationStateInfo stateInfo;

	@Inject
	public SimulationLogerImpl(PlayersInfo playersManager, BrokersOffice defaultBO,
			DateInfo dateInfo, SimulationStateInfo stateInfo) {
		this.playersManager = playersManager;
		this.defaultBO = defaultBO;
		this.dateInfo = dateInfo;
		this.stateInfo = stateInfo;
	}

	@Override
	public void dateChanged() {
		logState();
	}

	@Override
	public void logState() {
		LOG.info("Current day is: " + dateInfo.getCurrentDate().toString());
		StringBuilder builder = new StringBuilder("Current stock prices are: \n");
		defaultBO.getStockCompanies().forEach(comp -> {
			builder.append(comp.getName() + ": " + defaultBO.getTodaysPriceFor(comp) + ", ");
		});
		LOG.info(builder.toString());
		LOG.info("Obecny stan graczy: \n");
		playersManager.getAllPlayers().forEach(player -> LOG.info(player.toString()));
		LOG.info("Stan gry: " + stateInfo.getSimulationState().toString());
	}

}
