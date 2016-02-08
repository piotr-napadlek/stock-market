package com.capgemini.stockmarket.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.entity.CompanyEntity;
import com.capgemini.stockmarket.repository.CompanyRepository;

@Service
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService {

	@Inject
	private Mapper mapper;
	@Inject
	private CompanyRepository companyRepository;

	@Override
	public List<CompanyTo> findAllCompanies() {
		return mapList(companyRepository.findAll());
	}

	@Override
	public List<CompanyTo> findCompaniesByName(String name) {
		return mapList(companyRepository.findByName(name));
	}

	@Override
	public List<CompanyTo> findCompaniesByNameFragment(String nameFragment) {
		return mapList(companyRepository.findByNameFragment(nameFragment));
	}

	@Override
	public CompanyTo findById(Long id) {
		return mapper.map(companyRepository.findOne(id), CompanyTo.class);
	}

	@Transactional(readOnly = false)
	@Override
	public CompanyTo save(CompanyTo company) {
		if (company.getId() != null) {
			throw new IllegalArgumentException("Id of new company should be null.");
		}
		if (companyRepository.findByName(company.getName()).isEmpty() == false) {
			throw new IllegalArgumentException(
					"Name of a company should be unique. There exists one already in the database");
		}
		return mapper.map(companyRepository.save(mapper.map(company, CompanyEntity.class)),
				CompanyTo.class);
	}

	@Transactional(readOnly = false)
	@Override
	public CompanyTo update(CompanyTo company) {
		if (company.getId() == null) {
			throw new IllegalArgumentException("Company id should be set before updating.");
		}
		if (companyRepository.findOne(company.getId()) == null) {
			throw new IllegalArgumentException("Company with id " + company.getId() + " was not found.");
		}
		return mapper.map(companyRepository.save(mapper.map(company, CompanyEntity.class)),
				CompanyTo.class);
	}

	@Transactional(readOnly = false)
	@Override
	public CompanyTo delete(CompanyTo company) {
		return delete(company.getId());
	}

	@Transactional(readOnly = false)
	@Override
	public CompanyTo delete(Long id) {
		CompanyTo company = mapper.map(companyRepository.findOne(id), CompanyTo.class);
		if (id == null || company == null || company.getId() == null) {
			throw new IllegalArgumentException("Company with such id wasn't found in the database.");
		}
		companyRepository.delete(id);
		return company;
	}

	private List<CompanyTo> mapList(List<CompanyEntity> entities) {
		return entities.stream().map(entity -> mapper.map(entity, CompanyTo.class))
				.collect(Collectors.toList());
	}
}
