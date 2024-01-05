package com.ensat.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ensat.entities.Inspection_DTLS;
import com.ensat.entities.Product;

public interface InspectionDtlsRepository  extends JpaRepository<Inspection_DTLS, Long>{
	@Transactional
	void deleteByProduct_Id(long productId);
}
