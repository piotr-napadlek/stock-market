package com.capgemini.stockmarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capgemini.stockmarket.entity.CompanyEntity;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long>{

	@Query("select company from CompanyEntity company where upper(company.name) = upper(:name)")
	public List<CompanyEntity> findByName(@Param("name") String name);
	
	@Query("select company from CompanyEntity company where upper(company.name) like concat('%', concat(upper(:nameFragment), '%'))")
	public List<CompanyEntity> findByNameFragment(@Param("nameFragment") String nameFragment);
}
