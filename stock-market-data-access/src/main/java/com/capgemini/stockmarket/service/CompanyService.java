package com.capgemini.stockmarket.service;

import java.util.List;

import com.capgemini.stockmarket.dto.CompanyTo;

public interface CompanyService {

	List<CompanyTo> findAllCompanies();
	List<CompanyTo> findCompaniesByName(String name);
	List<CompanyTo> findCompaniesByNameFragment(String nameFragment);
	
	CompanyTo findById(Long id);
	
	CompanyTo save(CompanyTo company);
	CompanyTo update(CompanyTo company);
	
	CompanyTo delete(CompanyTo company);
	CompanyTo delete(Long id);
}
