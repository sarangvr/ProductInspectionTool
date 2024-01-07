package com.ensat.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ensat.entities.Inspection_DTLS;
import com.ensat.model.InspectionDetailsDTO;

public interface InspectionDtlsRepository extends JpaRepository<Inspection_DTLS, Long>{
	
	@Query("SELECT new com.ensat.model.InspectionDetailsDTO(" +
	           "p.productId, " +
	           "p.name, " +
	           "i.date, " +
	           "i.inspector, " +
	           "i.comments, " +
	           "i.result) " +
	           "FROM Product p " +
	           "JOIN Inspection_DTLS i ON p.productId = i.product.productId")
	List<InspectionDetailsDTO> findAllInspectionDetails();
	
	List<Inspection_DTLS> findAllByProductId(Long productId);

	
	
}
