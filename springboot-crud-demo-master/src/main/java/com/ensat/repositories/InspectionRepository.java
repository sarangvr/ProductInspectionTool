package com.ensat.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ensat.entities.Inspection;
import com.ensat.entities.Inspection_DTLS;
import com.ensat.model.ProductDetailsDTO;

public interface InspectionRepository extends JpaRepository<Inspection, Long>{
	
	Optional<Inspection> findFirstByProduct_Id(long string);
	
	@Transactional
	void deleteByProduct_Id(long productId);
	
	@Query("SELECT new com.ensat.model.ProductDetailsDTO(p.id, p.name, p.description, p.price, p.quantity, q.manufacturingDate, q.expiryDate, p.category, q.weight, q.colour, q.quality)\n"
		    + "FROM com.ensat.entities.Product p\n"
		    + "JOIN com.ensat.entities.QualityMetric q ON p.id = q.product.id\n"
		    + "JOIN com.ensat.entities.Inspection i ON p.id = i.product.id\n"
		    + "")
	List<ProductDetailsDTO> findAllProductDetailsByPendingStatus();

	void save(Inspection_DTLS inspectionDtls);
	
}
