package com.capgemini.stockmarket.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.entity.CompanyEntity;
import com.capgemini.stockmarket.repository.CompanyRepository;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceTest {
	private static final List<CompanyEntity> allCompanies = new ArrayList<>();

	@InjectMocks
	private CompanyServiceImpl companyService;
	@Mock
	private CompanyRepository companyRepository;
	@Spy
	private Mapper mapper = new DozerBeanMapper(Arrays.asList("mappings/company-mapping.xml"));

	@BeforeClass
	public static void populateCompanies() {
		if (allCompanies.isEmpty()) {
			allCompanies.add(new CompanyEntity(1L, "Microsoft"));
			allCompanies.add(new CompanyEntity(2L, "Intel"));
			allCompanies.add(new CompanyEntity(3L, "Google"));
		}
	}

	@Test
	public void shouldDetectMapper() {
		Assert.assertNotNull(mapper);
	}

	@Test
	public void shouldFindAllCompanies() {
		// given
		Mockito.when(companyRepository.findAll()).thenReturn(allCompanies);
		// when
		List<CompanyTo> companies = companyService.findAllCompanies();
		// then
		Assert.assertNotNull(companies);
		Assert.assertFalse(companies.isEmpty());
		Assert.assertEquals(3, companies.size());
		Assert.assertEquals("Microsoft", companies.get(0).getName());
		Mockito.verify(mapper, Mockito.atLeastOnce()).map(Mockito.any(CompanyEntity.class),
				Mockito.eq(CompanyTo.class));
	}

	@Test
	public void shouldFindCompanyByFullName() {
		// given
		final String name = "Intel";
		Mockito.when(companyRepository.findByName(name))
				.thenReturn(
						allCompanies.stream()
								.filter(co -> co.getName().toLowerCase()
										.equals(name.toLowerCase()))
						.collect(Collectors.toList()));
		// when
		List<CompanyTo> companies = companyService.findCompaniesByName(name);
		// then
		Assert.assertNotNull(companies);
		Assert.assertFalse(companies.isEmpty());
		Assert.assertEquals(1, companies.size());
		Assert.assertEquals(name, companies.get(0).getName());
		Assert.assertNotNull(companies.get(0).getId());
		Mockito.verify(mapper, Mockito.atLeastOnce()).map(Mockito.any(CompanyEntity.class),
				Mockito.eq(CompanyTo.class));
	}

	@Test
	public void shouldFindbyNameFragment() {
		// given
		final String nameFragment = "L";
		Mockito.when(companyRepository.findByNameFragment(Mockito.anyString()))
				.thenAnswer(new Answer<List<CompanyEntity>>() {
					@Override
					public List<CompanyEntity> answer(InvocationOnMock invocation)
							throws Throwable {
						return allCompanies.stream()
								.filter(co -> co.getName()
										.toLowerCase().contains(invocation
												.getArgumentAt(0, String.class).toLowerCase()))
								.collect(Collectors.toList());
					}
				});
		// when
		List<CompanyTo> companies = companyService.findCompaniesByNameFragment(nameFragment);
		// then
		Assert.assertNotNull(companies);
		Assert.assertFalse(companies.isEmpty());
		Mockito.verify(companyRepository, Mockito.times(1))
				.findByNameFragment(Mockito.eq(nameFragment));
		Mockito.verify(mapper, Mockito.atLeastOnce()).map(Mockito.any(CompanyEntity.class),
				Mockito.eq(CompanyTo.class));
	}

	@Test
	public void shouldFindById() {
		// given
		final Long id = 2L;
		Mockito.when(companyRepository.findOne(Mockito.anyLong()))
				.thenAnswer(new Answer<CompanyEntity>() {
					@Override
					public CompanyEntity answer(InvocationOnMock invocation) throws Throwable {
						return allCompanies.stream()
								.filter(co -> co.getId()
										.equals(invocation.getArgumentAt(0, Long.class)))
								.findFirst().orElse(null);
					}
				});
		// when
		CompanyTo company = companyService.findById(id);
		// then
		Assert.assertNotNull(company);
		Assert.assertEquals(id, company.getId());
		Mockito.verify(mapper, Mockito.times(1)).map(Mockito.any(CompanyEntity.class),
				Mockito.eq(CompanyTo.class));
		Mockito.verify(companyRepository, Mockito.times(1)).findOne(Mockito.eq(id));
	}

	@Test
	public void shouldCallRepoToSave() {
		// given
		CompanyTo companyToSave = new CompanyTo(null, "Facebook");
		Mockito.when(companyRepository.save(Mockito.any(CompanyEntity.class)))
				.thenReturn(new CompanyEntity(4L, "Facebook"));
		// when
		CompanyTo companySaved = companyService.save(companyToSave);
		// then
		Assert.assertNotNull(companySaved);
		Mockito.verify(companyRepository, Mockito.times(1))
				.save(Mockito.any(CompanyEntity.class));
		Mockito.verify(mapper, Mockito.times(1)).map(Mockito.any(CompanyEntity.class),
				Mockito.eq(CompanyTo.class));
		Mockito.verify(mapper, Mockito.times(1)).map(Mockito.any(CompanyTo.class),
				Mockito.eq(CompanyEntity.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionWhenSavingCompanyWithId() {
		// given
		CompanyTo companyToSave = new CompanyTo(4L, "Facebook");
		Mockito.when(companyRepository.save(Mockito.any(CompanyEntity.class)))
				.thenReturn(new CompanyEntity(4L, "Facebook"));
		// when
		CompanyTo companySaved = companyService.save(companyToSave);
		// then
		Assert.assertNull(companySaved);
		Mockito.verify(companyRepository, Mockito.never())
				.save(Mockito.any(CompanyEntity.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRejectUpdatingNonExistentItem() {
		// given
		final Long id = 4L;
		final CompanyTo companyToUpdate = new CompanyTo(id, "Apple");
		Mockito.when(companyRepository.findOne(Mockito.anyLong()))
				.thenAnswer(new Answer<CompanyEntity>() {
					@Override
					public CompanyEntity answer(InvocationOnMock invocation) throws Throwable {
						return allCompanies.stream()
								.filter(co -> co.getId()
										.equals(invocation.getArgumentAt(0, Long.class)))
								.findFirst().orElse(null);
					}
				});
		// when
		CompanyTo companyUpdated = companyService.update(companyToUpdate);
		// then
		Mockito.verify(companyRepository, Mockito.times(1)).findOne(id);
		Mockito.verify(companyRepository, Mockito.never())
				.save(Mockito.any(CompanyEntity.class));
		Assert.assertNull(companyUpdated);
	}

	@Test
	public void shouldCallRepoToDelete() {
		// given
		final Long idToDelete = 2L;
		Mockito.when(companyRepository.findOne(idToDelete))
				.thenReturn(new CompanyEntity(idToDelete, "Intel"));
		// when
		CompanyTo deletedCompany = companyService.delete(idToDelete);
		// then
		Mockito.verify(companyRepository, Mockito.times(1)).findOne(idToDelete);
		Mockito.verify(companyRepository, Mockito.times(1)).delete(idToDelete);
		Assert.assertNotNull(deletedCompany);
	}

	@Test
	public void shouldRejectDeletingNonExistentId() {
		// given
		final Long idToDelete = 5L;
		Mockito.when(companyRepository.findOne(idToDelete)).thenReturn(new CompanyEntity());
		// when
		CompanyTo deletedCompany = null;
		try {
			deletedCompany = companyService.delete(idToDelete);
		} catch (Exception e) {
			Assert.assertTrue(e instanceof IllegalArgumentException);
		}
		// then
		Mockito.verify(companyRepository, Mockito.times(1)).findOne(idToDelete);
		Mockito.verify(companyRepository, Mockito.never()).delete(idToDelete);
		Assert.assertNull(deletedCompany);
	}
}
