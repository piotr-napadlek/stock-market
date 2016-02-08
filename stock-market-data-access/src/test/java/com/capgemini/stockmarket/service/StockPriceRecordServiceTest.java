package com.capgemini.stockmarket.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.capgemini.stockmarket.dto.StockPriceRecordTo;
import com.capgemini.stockmarket.entity.CompanyEntity;
import com.capgemini.stockmarket.entity.StockPriceRecordEntity;
import com.capgemini.stockmarket.repository.CompanyRepository;
import com.capgemini.stockmarket.repository.StockPriceRecordRepository;

@RunWith(MockitoJUnitRunner.class)
public class StockPriceRecordServiceTest {
	private static final List<StockPriceRecordEntity> allSPRs = new ArrayList<>();
	private static final List<CompanyEntity> allCompanies = new ArrayList<>();

	@InjectMocks
	private StockPriceRecordServiceImpl sprService;
	@Spy
	@InjectMocks
	private CompanyServiceImpl companyService;

	@Mock
	private CompanyRepository companyRepository;
	@Mock
	private StockPriceRecordRepository sprRepository;
	@Spy
	private Mapper mapper = new DozerBeanMapper(
			Arrays.asList("mappings/company-mapping.xml", "mappings/spr-mapping.xml"));

	@BeforeClass
	public static void populateSprs() throws ParseException {
		if (allCompanies.isEmpty()) {
			allCompanies.add(new CompanyEntity(1L, "Microsoft"));
			allCompanies.add(new CompanyEntity(2L, "Intel"));
			allCompanies.add(new CompanyEntity(3L, "Google"));
		}
		if (allSPRs.isEmpty()) {
			DateFormat dateParser = new SimpleDateFormat("yyyyMMdd");
			allSPRs.add(new StockPriceRecordEntity(allCompanies.get(0),
					dateParser.parse("20160212"), 10.2));
			allSPRs.add(new StockPriceRecordEntity(allCompanies.get(0),
					dateParser.parse("20160213"), 11.2));
			allSPRs.add(new StockPriceRecordEntity(allCompanies.get(0),
					dateParser.parse("20160214"), 10.5));
			allSPRs.add(new StockPriceRecordEntity(allCompanies.get(1),
					dateParser.parse("20160212"), 100.2));
			allSPRs.add(new StockPriceRecordEntity(allCompanies.get(1),
					dateParser.parse("20160213"), 110.2));
			allSPRs.add(new StockPriceRecordEntity(allCompanies.get(1),
					dateParser.parse("20160214"), 100.5));
			allSPRs.add(new StockPriceRecordEntity(allCompanies.get(2),
					dateParser.parse("20160212"), 5d));
			allSPRs.add(new StockPriceRecordEntity(allCompanies.get(2),
					dateParser.parse("20160213"), 6d));
			allSPRs.add(new StockPriceRecordEntity(allCompanies.get(2),
					dateParser.parse("20160214"), 7d));
		}
	}

	@Test
	public void shouldFindAllSPRs() {
		// given
		Mockito.when(sprRepository.findAll()).thenReturn(allSPRs);
		// when
		List<StockPriceRecordTo> allFound = sprService.findAllRecords();
		// then
		Assert.assertNotNull(allFound);
		Assert.assertFalse(allFound.isEmpty());
		Assert.assertEquals(allSPRs.size(), allFound.size());
		Mockito.verify(mapper, Mockito.times(9)).map(Mockito.any(StockPriceRecordEntity.class),
				Mockito.eq(StockPriceRecordTo.class));
		Mockito.verify(sprRepository, Mockito.times(1)).findAll();
	}
	
	//TODO: PNAPADLE: add lacking tests in the same manner as in CompanyServiceTest
}
