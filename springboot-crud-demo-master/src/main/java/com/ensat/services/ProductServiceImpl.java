package com.ensat.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ensat.entities.Inspection;
import com.ensat.entities.Inspection_DTLS;
import com.ensat.entities.Product;
import com.ensat.entities.QualityMetric;
import com.ensat.entities.Result;
import com.ensat.model.ProductDetails;
import com.ensat.model.ProductDetailsDTO;
import com.ensat.repositories.InspectionRepository;
import com.ensat.repositories.ProductRepository;
import com.ensat.repositories.QualityMetricRepository;

/**
 * Product service implement.
 */
@Service
public class ProductServiceImpl implements ProductService {
     @Autowired
     private ProductRepository productRepository;
     
     @Autowired
     private QualityMetricRepository qualityMetricRepository;
     
     @Autowired
     private InspectionRepository inspectionRepository;

    

    @Override
    public List<ProductDetailsDTO> listAllProducts() {
        return productRepository.findAllProductDetails();
    }

    @Override
	public ProductDetails getProductById(Long id) {
		ProductDetails productDetails = new ProductDetails();
		try {
			Product product = productRepository.findById(id).get();
			productDetails.setId(product.getId());
			productDetails.setCategory(product.getCategory());
			productDetails.setDescription(product.getDescription());
			productDetails.setName(product.getName());
			productDetails.setPrice(product.getPrice());
			productDetails.setQuantity(product.getQuantity());
			QualityMetric qualityMetric = qualityMetricRepository.findFirstByProduct_Id(product.getId()).get();
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
			product.setId(productDetails.getId());
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
			Inspection inspection = new Inspection();
			inspection.setResult(Result.PENDING);
			inspection.setProduct(product);
			inspectionRepository.save(inspection);
			
//			Date currentDate = new Date();
//			Inspection_DTLS inspectionDtls = new Inspection_DTLS();
//			inspectionDtls.setProductId(productDetails.getId());
//			inspectionDtls.setCategory(productDetails.getCategory());
//			inspectionDtls.setDate(currentDate);
//			inspectionDtls.setInspector("Insp_1");
//			inspectionDtls.setProductName(productDetails.getName());
//			inspectionDtls.setResult("PENDING");
//			inspectionRepository.save(inspectionDtls);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error createProductNew: " + e.getMessage());
		}
		return product;
	}

    @Override
	public boolean deleteProduct(Long id) {
		try {
			inspectionRepository.deleteByProduct_Id(id);
			qualityMetricRepository.deleteByProduct_Id(id);
			productRepository.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error createProductNew: " + e.getMessage());
		}
		return true;
	}

	@Override
	public ProductDetails editProductById(ProductDetails productDetails) {
		try {
			Product product = productRepository.findById(productDetails.getId()).get();
			product.setId(productDetails.getId());
			product.setCategory(productDetails.getCategory());
			product.setDescription(productDetails.getDescription());
			product.setName(productDetails.getName());
			product.setPrice(productDetails.getPrice());
			product.setQuantity(productDetails.getQuantity());
			product = productRepository.save(product);
			QualityMetric qualityMetric = qualityMetricRepository.findFirstByProduct_Id(productDetails.getId()).get();
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
