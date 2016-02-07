package com.capgemini.stockmarket.repository;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.capgemini.stockmarket.entity.CompanyEntity;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/test-database-context.xml" })
public class CompanyRepositoryTest {
	private static List<CompanyEntity> allCompanies;

	@Inject
	private CompanyRepository companyRepository;

	@Before
	public void getAllRecords() {
		if (allCompanies == null) {
			allCompanies = companyRepository.findAll();
		}
	}

	@Test
	public void shouldFindCompanyByFullNameIgnoringCase() {
		// given
		final String name = "intel";
		// when
		List<CompanyEntity> companies = companyRepository.findByName(name);
		List<CompanyEntity> javaFiltered = allCompanies.stream()
				.filter(company -> company.getName().toLowerCase().equals(name.toLowerCase()))
				.collect(Collectors.toList());
		// then
		Assert.assertNotNull(companies);
		Assert.assertEquals(javaFiltered.size(), companies.size());
	}
	
	@Test
	public void shouldFindCompanyByNameFragmentIgnoringCase() {
		// given
		final String name = "i";
		// when
		List<CompanyEntity> companies = companyRepository.findByNameFragment(name);
		List<CompanyEntity> javaFiltered = allCompanies.stream()
				.filter(company -> company.getName().toLowerCase().contains(name.toLowerCase()))
				.collect(Collectors.toList());
		// then
		Assert.assertNotNull(companies);
		Assert.assertEquals(javaFiltered.size(), companies.size());
	}
}
