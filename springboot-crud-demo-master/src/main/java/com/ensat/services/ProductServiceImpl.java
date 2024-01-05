package com.ensat.services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Service;

import com.ensat.entities.Inspection_DTLS;
import com.ensat.entities.Product;
import com.ensat.entities.QualityMetric;
import com.ensat.model.ProductDetails;
import com.ensat.model.ProductDetailsDTO;
import com.ensat.repositories.InspectionDtlsRepository;
import com.ensat.repositories.ProductRepository;
import com.ensat.repositories.QualityMetricRepository;
/**
 * Product service implement.
 */
@Service
public class ProductServiceImpl implements ProductService {
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
     @Autowired
     private ProductRepository productRepository;
     
     @Autowired
     private QualityMetricRepository qualityMetricRepository;
     
     @Autowired
     private InspectionDtlsRepository inspectionDtlsRepository;

    @Override
    public List<ProductDetailsDTO> listAllProducts() {
    	List<ProductDetailsDTO> list = new ArrayList<>();
    	try {
    		
    		list = productRepository.findAllProductDetails();
    		
		} catch (NotReadablePropertyException e) {
			logger.error("Error while processing NotReadablePropertyException", e);
			e.printStackTrace();
			System.out.println("Error Inspection_DTLS: " + e.getMessage());
		} catch (DataIntegrityViolationException e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				ConstraintViolationException constraintViolationException = (ConstraintViolationException) e.getCause();
				logger.error("Constraint violation details - Constraint Name: {}, SQL: {}",constraintViolationException.getConstraintName(), constraintViolationException.getSQL());
			}
			logger.error("Error while processing DataIntegrityViolationException", e);
		} catch(InvalidDataAccessResourceUsageException e) {
			e.printStackTrace();
			System.out.println("Error listAllProducts: " + e.getMessage());
		}
        return list;
    }

    @Override
	public ProductDetails getProductById(Long id) {
		ProductDetails productDetails = new ProductDetails();
		try {
			Product product = productRepository.findById(id).get();
			productDetails.setProductId(product.getProductId());
			productDetails.setCategory(product.getCategory());
			productDetails.setDescription(product.getDescription());
			productDetails.setName(product.getName());
			productDetails.setPrice(product.getPrice());
			productDetails.setQuantity(product.getQuantity());
			QualityMetric qualityMetric = qualityMetricRepository.findFirstByProduct_Id(product.getProductId()).get();
			productDetails.setColour(qualityMetric.getColour());
			productDetails.setExpiryDate(qualityMetric.getExpiryDate());
			productDetails.setManufacturingDate(qualityMetric.getManufacturingDate());
			productDetails.setQuality(qualityMetric.getQuality());
			productDetails.setWeight(qualityMetric.getWeight());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error createProductNew: " + e.getMessage());
		}
		return productDetails;
	}

    @Override
	public Product saveProduct(ProductDetails productDetails) {
		Product product = new Product();
		try {
			long userId = 100100;
			product.setId(userId);
			product.setProductId(productDetails.getProductId());
			product.setCategory(productDetails.getCategory());
			product.setDescription(productDetails.getDescription());
			product.setName(productDetails.getName());
			product.setPrice(productDetails.getPrice());
			product.setQuantity(productDetails.getQuantity());
			product = productRepository.save(product);
			
			QualityMetric qualityMetric = new QualityMetric();
			qualityMetric.setProduct(product);
			qualityMetric.setColour(productDetails.getColour());
			qualityMetric.setExpiryDate(productDetails.getExpiryDate());
			qualityMetric.setManufacturingDate(productDetails.getManufacturingDate());
			qualityMetric.setQuality(productDetails.getQuality());
			qualityMetric.setWeight(productDetails.getWeight());
			qualityMetricRepository.save(qualityMetric);
			
			Inspection_DTLS inspectionDtls = new Inspection_DTLS();
			try {
				inspectionDtls.setProduct(product);
				inspectionDtls.setCategory(productDetails.getCategory());
				inspectionDtls.setDate(productDetails.getExpiryDate());
				inspectionDtls.setInspector("Insp_1");
				inspectionDtls.setProductName(productDetails.getName());
				inspectionDtls.setResult("PENDING");
				inspectionDtlsRepository.save(inspectionDtls);
			} catch (NotReadablePropertyException e) {
				logger.error("Error while processing NotReadablePropertyException", e);
				e.printStackTrace();
				System.out.println("Error Inspection_DTLS: " + e.getMessage());
			} catch (DataIntegrityViolationException e) {
				if (e.getCause() instanceof ConstraintViolationException) {
					ConstraintViolationException constraintViolationException = (ConstraintViolationException) e.getCause();
					logger.error("Constraint violation details - Constraint Name: {}, SQL: {}",constraintViolationException.getConstraintName(), constraintViolationException.getSQL());
				}
				logger.error("Error while processing DataIntegrityViolationException", e);
			}
			String inspDtls = inspectionDtls.toString();
			System.out.println(inspDtls);

		} catch (DataIntegrityViolationException e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				ConstraintViolationException constraintViolationException = (ConstraintViolationException) e.getCause();
				logger.error("Constraint violation details - Constraint Name: {}, SQL: {}",constraintViolationException.getConstraintName(), constraintViolationException.getSQL());
			}
			logger.error("Error while processing DataIntegrityViolationException", e);
		}
		return product;
	}

    @Override
	public boolean deleteProduct(Long id) {
		try {
			inspectionDtlsRepository.deleteByProduct_Id(id);;
			qualityMetricRepository.deleteByProduct_Id(id);
			productRepository.deleteByProductId(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error createProductNew: " + e.getMessage());
		}
		return true;
	}

	@Override
	public ProductDetails editProductById(ProductDetails productDetails) {
		try {
			Product product = productRepository.findById(productDetails.getProductId()).get();
			product.setProductId(productDetails.getProductId());
			product.setCategory(productDetails.getCategory());
			product.setDescription(productDetails.getDescription());
			product.setName(productDetails.getName());
			product.setPrice(productDetails.getPrice());
			product.setQuantity(productDetails.getQuantity());
			product = productRepository.save(product);
			QualityMetric qualityMetric = qualityMetricRepository.findFirstByProduct_Id(productDetails.getProductId()).get();
			qualityMetric.setColour(productDetails.getColour());
			qualityMetric.setExpiryDate(productDetails.getExpiryDate());
			qualityMetric.setManufacturingDate(productDetails.getManufacturingDate());
			qualityMetric.setQuality(productDetails.getQuality());
			qualityMetric.setWeight(productDetails.getWeight());
			qualityMetricRepository.save(qualityMetric);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error createProductNew: " + e.getMessage());
		}
		return productDetails;
	}

}
