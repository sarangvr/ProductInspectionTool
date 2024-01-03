package com.ensat.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ensat.entities.QualityMetric;

public interface QualityMetricRepository extends JpaRepository<QualityMetric, Long>{
	
	Optional<QualityMetric> findFirstByProduct_Id(long productId);
	
	@Transactional
	void deleteByProduct_Id(long productId);
	
}
