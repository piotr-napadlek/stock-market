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
		return companyRepository.findAll().stream()
				.map(entity -> mapper.map(entity, CompanyTo.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<CompanyTo> findCompaniesByName(String name) {
		return companyRepository.findByName(name).stream()
				.map(entity -> mapper.map(entity, CompanyTo.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<CompanyTo> findCompaniesByNameFragment(String nameFragment) {
		return companyRepository.findByNameFragment(nameFragment).stream()
				.map(entity -> mapper.map(entity, CompanyTo.class))
				.collect(Collectors.toList());
	}

	@Override
	public CompanyTo findById(Long id) {
		return mapper.map(companyRepository.findOne(id), CompanyTo.class);
	}

	@Transactional(readOnly = false)
	@Override
	public CompanyTo save(CompanyTo company) {
		if (company.getId() != null) {
			throw new IllegalStateException("Id of new company should be null.");
		}
		return mapper.map(companyRepository.save(mapper.map(company, CompanyEntity.class)),
				CompanyTo.class);
	}

	@Transactional(readOnly = false)
	@Override
	public CompanyTo update(CompanyTo company) {
		if (company.getId() == null) {
			throw new IllegalStateException("Company id should be set befor updating.");
		}
		return mapper.map(companyRepository.save(mapper.map(company, CompanyEntity.class)),
				CompanyTo.class);
	}

	@Transactional(readOnly = false)
	@Override
	public CompanyTo delete(CompanyTo company) {
		companyRepository.delete(mapper.map(company, CompanyEntity.class));
		return company;
	}

	@Transactional(readOnly = false)
	@Override
	public CompanyTo delete(Long id) {
		CompanyTo company = mapper.map(companyRepository.findOne(id), CompanyTo.class);
		companyRepository.delete(id);
		return company;
	}
}
