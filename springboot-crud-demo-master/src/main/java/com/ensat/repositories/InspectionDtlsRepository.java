package com.ensat.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ensat.entities.Inspection_DTLS;
import com.ensat.entities.Product;

public interface InspectionDtlsRepository  extends JpaRepository<Inspection_DTLS, Long>{
	
	List<Inspection_DTLS> findAllByProductId(Long productId);
	
	
}
