package com.capgemini.stockmarket.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.stockmarket.dto.StockPriceRecordTo;
import com.capgemini.stockmarket.entity.StockPriceRecordEntity;
import com.capgemini.stockmarket.repository.StockPriceRecordRepository;

@Service
@Transactional(readOnly = true)
public class StockPriceRecordServiceImpl implements StockPriceRecordService {

	@Inject
	private StockPriceRecordRepository sprRepository;
	@Inject
	private Mapper mapper;

	@Override
	public List<StockPriceRecordTo> findAllRecords() {
		return mapList(sprRepository.findAll());
	}

	@Override
	public List<StockPriceRecordTo> findByDate(Date date) {
		return mapList(sprRepository.findByDate(date));
	}

	@Override
	public List<StockPriceRecordTo> findTillDate(Date tillDate) {
		return mapList(sprRepository.findAllTillDate(tillDate));
	}

	@Override
	public List<StockPriceRecordTo> findBetweenDates(Date fromDate, Date tillDate) {
		return mapList(sprRepository.findBetweenDates(fromDate, tillDate));
	}

	@Override
	public List<StockPriceRecordTo> findByCompanyName(String name) {
		return mapList(sprRepository.findByCompanyName(name));
	}

	@Override
	public List<StockPriceRecordTo> findByCompanyId(Long id) {
		return mapList(sprRepository.findByCompanyId(id));
	}

	@Override
	public List<StockPriceRecordTo> findByCompanyNameBetweenDates(String companyName,
			Date fromDate, Date tillDate) {
		return mapList(
				sprRepository.findByCompanyNameBetweenDates(companyName, fromDate, tillDate));
	}

	@Override
	public StockPriceRecordTo findByCompanyNameAndExactDay(String companyName, Date date) {
		List<StockPriceRecordEntity> sprs = sprRepository
				.findByCompanyNameBetweenDates(companyName, date, date);
		if (sprs.isEmpty()) {
			return findByCompanyNameAndExactDay(companyName,
					new DateTime(date.getTime()).minusDays(1).toDate());
		} else {
			return mapper.map(sprRepository
					.findByCompanyNameBetweenDates(companyName, date, date).get(0),
					StockPriceRecordTo.class);
		}
		
		
	}

	@Transactional(readOnly = false)
	@Override
	public StockPriceRecordTo saveOne(StockPriceRecordTo spr) {
		return mapper.map(sprRepository.save(mapper.map(spr, StockPriceRecordEntity.class)),
				StockPriceRecordTo.class);
	}

	@Transactional(readOnly = false)
	@Override
	public List<StockPriceRecordTo> saveAll(List<StockPriceRecordTo> spr) {
		List<StockPriceRecordTo> sprs = new ArrayList<>(spr.size());
		spr.forEach(sprTo -> sprs.add(saveOne(sprTo)));
		return sprs;
	}

	@Transactional(readOnly = false)
	@Override
	public StockPriceRecordTo deleteOne(StockPriceRecordTo spr) {
		sprRepository.delete(mapper.map(spr, StockPriceRecordEntity.class));
		return spr;
	}

	@Transactional(readOnly = false)
	@Override
	public StockPriceRecordTo deleteOne(Long id) {
		StockPriceRecordEntity sprEntity = sprRepository.findOne(id);
		sprRepository.delete(sprEntity);
		return mapper.map(sprEntity, StockPriceRecordTo.class);
	}

	@Transactional(readOnly = false)
	@Override
	public List<StockPriceRecordTo> deleteAll(Collection<Long> ids) {
		List<StockPriceRecordEntity> sprEntities = sprRepository.findAll(ids);
		sprRepository.delete(sprEntities);
		return mapList(sprEntities);
	}

	@Transactional(readOnly = false)
	@Override
	public StockPriceRecordTo update(StockPriceRecordTo spr) {
		if (sprRepository.findOne(spr.getId()) == null) {
			throw new IllegalArgumentException("Such element does not exist in the database!");
		}
		return mapper.map(sprRepository.save(mapper.map(spr, StockPriceRecordEntity.class)),
				StockPriceRecordTo.class);
	}

	@Transactional(readOnly = false)
	@Override
	public List<StockPriceRecordTo> updateAll(List<StockPriceRecordTo> spr) {
		List<StockPriceRecordTo> sprTos = new ArrayList<>(spr.size());
		spr.forEach(sprTo -> sprTos.add(update(sprTo)));
		return sprTos;
	}

	@Override
	public List<StockPriceRecordTo> deleteAll() {
		List<StockPriceRecordTo> allSprs = findAllRecords();
		sprRepository.deleteAll();
		return allSprs;
	}

	private List<StockPriceRecordTo> mapList(List<StockPriceRecordEntity> entities) {
		return entities.stream().map(entity -> mapper.map(entities, StockPriceRecordTo.class))
				.collect(Collectors.toList());
	}
}
