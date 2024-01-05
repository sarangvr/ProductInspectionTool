package com.ensat.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ensat.entities.Product;
import com.ensat.model.ProductDetailsDTO;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT new com.ensat.model.ProductDetailsDTO(" +
           "p.productId, " +
           "p.name, " +
           "p.description, " +
           "p.price, " +
           "p.quantity, " +
           "q.manufacturingDate, " +
           "q.expiryDate, " +
           "p.category, " +
           "q.weight, " +
           "q.colour, " +
           "q.quality) " +
           "FROM Product p " +
           "JOIN QualityMetric q ON p.productId = q.product.productId")
    List<ProductDetailsDTO> findAllProductDetails();
    @Transactional
	void deleteByProductId(long productId);
}
