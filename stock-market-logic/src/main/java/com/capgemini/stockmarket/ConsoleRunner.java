package com.capgemini.stockmarket;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.capgemini.stockmarket.initializer.SimulationInitializer;
import com.capgemini.stockmarket.settings.PlayerSettings;
import com.capgemini.stockmarket.simulation.StockSimulationManager;
import com.capgemini.stockmarket.simulation.StockSimulationManagerImpl;

public class ConsoleRunner {
	private static final Log LOG = LogFactory.getLog(ConsoleRunner.class);

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("game-context.xml");
		SimulationInitializer initializer = context.getBean(SimulationInitializer.class);
		Scanner in = new Scanner(System.in);
		String data = new String(
				Files.readAllBytes(
						Paths.get(ConsoleRunner.class.getResource("/data.csv").toURI())),
				"UTF-8");
		LOG.info(data);
		initializer.initializeSimulation(data);
		StockSimulationManager manager = context.getBean(StockSimulationManagerImpl.class);
		
		PlayerSettings settings = context.getBean(PlayerSettings.class);
		settings.setPlayerName("Piotr");
		manager.setPlayerSettings("DefaultPlayer", settings);
		
		PlayerSettings otherSettings = context.getBean(PlayerSettings.class);
		otherSettings.setBaseBalance(50000d);
		otherSettings.getAdditionalBalances().put("EUR", 2000d);
		otherSettings.setPlayerName("Krystian");
		manager.addPlayer(otherSettings);

		manager.addPlayer("Bierny Maciek");
		manager.setPlayerStrategy("Bierny Maciek", "passive");
		manager.start();
		
		String input;
		do {
			input = in.nextLine();

			manager.nextDay();
		} while (input.equals("exit") == false);
		in.close();
	}

}
