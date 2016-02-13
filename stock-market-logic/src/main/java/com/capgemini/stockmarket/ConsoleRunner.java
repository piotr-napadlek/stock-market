package com.capgemini.stockmarket;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.capgemini.stockmarket.initializer.SimulationInitializer;
import com.capgemini.stockmarket.simulation.StockSimulationManager;

public class ConsoleRunner {
	private static final Log LOG = LogFactory.getLog(ConsoleRunner.class);

	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("game-context.xml");
		SimulationInitializer initializer = context.getBean(SimulationInitializer.class);
		String data = new String(
				Files.readAllBytes(
						Paths.get(ConsoleRunner.class.getResource("/data.csv").toURI())),
				"UTF-8");
		LOG.info(data);
		initializer.initializeGame(data);
		StockSimulationManager manager = context.getBean(StockSimulationManager.class);
		manager.start();
		manager.nextDay();
		Scanner in = new Scanner(System.in);
		String input;
		do {
			input = in.nextLine();
			
			manager.nextDay();
		} while (input.equals("exit") == false);
		
	}

}