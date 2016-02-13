package com.capgemini.stockmarket.initializer;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.Matchers.any;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.StockPriceRecordTo;
import com.capgemini.stockmarket.initializer.csv.CSVHandler;
import com.capgemini.stockmarket.service.CompanyService;
import com.capgemini.stockmarket.service.StockPriceRecordService;
import com.capgemini.stockmarket.simulation.StockSimulationManager;
import com.capgemini.stockmarket.simulation.state.SimulationState;


@RunWith(MockitoJUnitRunner.class)
public class SimulationInitializerTest {
	@InjectMocks
	private SimulationInitializer initializer;
	@Mock
	private CSVHandler csvHandler;
	@Mock
	private StockSimulationManager simulationManager;
	@Mock
	private StockPriceRecordService sprService;
	@Mock
	private CompanyService companyService;

	@Test
	public void shouldInitializeGame() throws ParseException, Exception {
		// given
		when(csvHandler.parseCSV(anyString())).thenReturn(getSPRTos());
		when(sprService.saveAll(anyListOf(StockPriceRecordTo.class))).thenReturn(getSPRTos());
		final String initializationCsvText = "any csv with proper format";
		// when
		initializer.initializeGame(initializationCsvText);
		// then
		ArgumentCaptor<DateTime> captor = ArgumentCaptor.forClass(DateTime.class);
		verify(csvHandler, times(1)).parseCSV(initializationCsvText);
		verify(simulationManager, times(1)).setStartDate(captor.capture());
		Assert.assertEquals(captor.getValue(), new DateTime(2002, 01, 01, 0, 0, 0));
		verify(simulationManager, times(1)).setFinishDate(captor.capture());
		Assert.assertEquals(captor.getValue(), new DateTime(2002, 01, 03, 0, 0, 0));
		verify(simulationManager, times(1)).setGameState(SimulationState.READY);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void shouldThrowWhenParsedDataIsIncomplete() throws ParseException, Exception {
		// given
		when(csvHandler.parseCSV(anyString())).thenReturn(getIncompleteSprTos());
		final String initializationCsvText = "any csv with improper content";
		// when
		initializer.initializeGame(initializationCsvText);
		// then
		verify(csvHandler, times(1)).parseCSV(initializationCsvText);
		verify(sprService, never()).saveAll(any());
	}
	
	private List<StockPriceRecordTo> getSPRTos() throws ParseException {
		return Arrays.asList(
				new StockPriceRecordTo(new SimpleDateFormat("yyyyMMdd").parse("20020101"),
						new CompanyTo(1L, "Intel"), 10.5),
				new StockPriceRecordTo(new SimpleDateFormat("yyyyMMdd").parse("20020102"),
						new CompanyTo(1L, "Intel"), 10.8),
				new StockPriceRecordTo(new SimpleDateFormat("yyyyMMdd").parse("20020103"),
						new CompanyTo(1L, "Intel"), 11d),
				new StockPriceRecordTo(new SimpleDateFormat("yyyyMMdd").parse("20020101"),
						new CompanyTo(2L, "Microsoft"), 100.5),
				new StockPriceRecordTo(new SimpleDateFormat("yyyyMMdd").parse("20020102"),
						new CompanyTo(2L, "Microsoft"), 101.8),
				new StockPriceRecordTo(new SimpleDateFormat("yyyyMMdd").parse("20020103"),
						new CompanyTo(2L, "Microsoft"), 114d));
	}
	
	private List<StockPriceRecordTo> getIncompleteSprTos() throws ParseException {
		return Arrays.asList(
				new StockPriceRecordTo(new SimpleDateFormat("yyyyMMdd").parse("20020101"),
						new CompanyTo(1L, "Intel"), 10.5),
				new StockPriceRecordTo(new SimpleDateFormat("yyyyMMdd").parse("20020102"),
						new CompanyTo(1L, "Intel"), 10.8),
				new StockPriceRecordTo(new SimpleDateFormat("yyyyMMdd").parse("20020103"),
						new CompanyTo(1L, "Intel"), 11d),
				new StockPriceRecordTo(new SimpleDateFormat("yyyyMMdd").parse("20020101"),
						new CompanyTo(2L, "Microsoft"), 100.5),
				new StockPriceRecordTo(new SimpleDateFormat("yyyyMMdd").parse("20020102"),
						new CompanyTo(2L, "Microsoft"), 101.8),
				new StockPriceRecordTo(null,
						new CompanyTo(2L, "Microsoft"), 114d));
	}
}
