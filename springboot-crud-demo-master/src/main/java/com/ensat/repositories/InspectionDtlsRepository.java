package com.ensat.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ensat.entities.Inspection_DTLS;

public interface InspectionDtlsRepository extends JpaRepository<Inspection_DTLS, Long>{
	
	List<Inspection_DTLS> findAllByProductId(Long productId);
	
	
}
