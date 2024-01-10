package com.ensat.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.ensat.category.entities.Bakery;
import com.ensat.category.entities.CategoryDtlsDto;
import com.ensat.category.entities.CategoryDto;
import com.ensat.category.repositories.BakeryRepository;
import com.ensat.category.repositories.BeveragesRepository;
import com.ensat.category.repositories.DairyRepository;
import com.ensat.category.repositories.GroceryRepository;
import com.ensat.category.repositories.MeatAndPoultryRepository;
import com.ensat.entities.Inspection_DTLS;
import com.ensat.entities.Product;
import com.ensat.entities.Quality;
import com.ensat.entities.QualityMetric;
import com.ensat.entities.Result;
import com.ensat.model.InspectionDetails;
import com.ensat.model.InspectionDetailsDTO;
import com.ensat.model.ProductDetails;
import com.ensat.model.ProductDetailsDTO;
import com.ensat.model.ReportsDto;
import com.ensat.repositories.InspectionDtlsRepository;
import com.ensat.repositories.ProductRepository;
import com.ensat.repositories.QualityMetricRepository;
import com.ensat.utility.Constants;
import com.ensat.utility.Utility;

@Service
public class InspectionServiceImpl implements InspectionService, Constants {
	private static final Logger logger = LoggerFactory.getLogger(InspectionServiceImpl.class);
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private QualityMetricRepository qualityMetricRepository;
    
    @Autowired
    private InspectionDtlsRepository inspectionDtlsRepository;
    @Autowired
    private GroceryRepository groceryRepository;
    @Autowired
    private DairyRepository dairyRepository;
    @Autowired
    private BakeryRepository bakeryRepository;
    @Autowired
    private BeveragesRepository beveragesRepository;
    @Autowired
    private MeatAndPoultryRepository meatAndPoultryRepository;
	

	@Override
	public List<InspectionDetailsDTO> listAllInspection() {
		List<InspectionDetailsDTO> list = new ArrayList<>();
    	try {
    		
    		list = inspectionDtlsRepository.findAllInspectionDetails();
    		
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
			logger.error(ERROR_MESSAGE, e);
		}
		
		
		return list;
	}

	@Override
	public boolean autoInspectProducts() {
		try {
			List<ProductDetailsDTO> productList = productRepository.findAllProductDetails();
			boolean inspectedAnyProduct = false;

			for (ProductDetailsDTO productDto : productList) {
				LocalDate currentDate = LocalDate.now();
				LocalDate expiryDate = productDto.getExpiryDate();
				long productId = productDto.getProductId();

				if (expiryDate != null && expiryDate.isBefore(currentDate)) {
					Product product = productRepository.findById(productId).get();

					List<Inspection_DTLS> inspDtlsList = inspectionDtlsRepository.findAll();
					long inspectionId = Utility.findInspectionIdByProductId(inspDtlsList, productId);
					Inspection_DTLS inspectionDtls = inspectionDtlsRepository.findById(inspectionId).get();

					inspectionDtls.setProduct(product);
					inspectionDtls.setInspector(AUTO_INSPECTED);
					inspectionDtls.setComments(PRODUCT_INSPECTED);
					inspectionDtls.setResult(FAIL);
					inspectionDtlsRepository.save(inspectionDtls);

					inspectedAnyProduct = true; // Set the flag to true if any product is inspected
				} else if (expiryDate != null && expiryDate.isAfter(currentDate)){
					Product product = productRepository.findById(productId).get();

					List<Inspection_DTLS> inspDtlsList = inspectionDtlsRepository.findAll();
					long inspectionId = Utility.findInspectionIdByProductId(inspDtlsList, productId);
					Inspection_DTLS inspectionDtls = inspectionDtlsRepository.findById(inspectionId).get();

					inspectionDtls.setProduct(product);
					inspectionDtls.setInspector(AUTO_INSPECTED);
					inspectionDtls.setComments(PRODUCT_INSPECTED);
					inspectionDtls.setResult(PASS);
					inspectionDtlsRepository.save(inspectionDtls);
				}
			}

			if (inspectedAnyProduct)
				return true; // Return true if any product is inspected

		} catch (NotReadablePropertyException e) {
			logger.error(NOT_READABLE_PROPERTY_EXCEPTION, e);
			e.printStackTrace();
			System.out.println(ERROR_LIST_ALL_PRODUCTS + e.getMessage());
		} catch (DataIntegrityViolationException e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				ConstraintViolationException constraintViolationException = (ConstraintViolationException) e.getCause();
				logger.error(CONSTRAINT_VIOLATION, constraintViolationException.getConstraintName(),
						constraintViolationException.getSQL());
			}
			logger.error(DATA_INTEGRITY_VIOLATION_EXCEPTION, e);
		} catch (InvalidDataAccessResourceUsageException e) {
			e.printStackTrace();
			System.out.println(ERROR_LIST_ALL_PRODUCTS + e.getMessage());
			logger.error(ERROR_MESSAGE, e);
		}
		return false;
	}
	
	@Override
	public ReportsDto getReportsDetails() {
		ReportsDto dto = new ReportsDto();
        try {
            List<QualityMetric> qualityList = qualityMetricRepository.findAll();

            // Count the occurrences of each Quality type
            Map<com.ensat.entities.Quality, Long> qualityCount = qualityList.stream()
                    .collect(Collectors.groupingBy(QualityMetric::getQuality, Collectors.counting()));

            // Calculate percentages and set them in the ReportsDto
            long totalEntries = qualityList.size();

            // Calculate percentages
            double lowPercentage = (double) qualityCount.getOrDefault(com.ensat.entities.Quality.LOW, 0L) / totalEntries * 100;
            double mediumPercentage = (double) qualityCount.getOrDefault(com.ensat.entities.Quality.MEDIUM, 0L) / totalEntries * 100;
            double highPercentage = (double) qualityCount.getOrDefault(com.ensat.entities.Quality.HIGH, 0L) / totalEntries * 100;

            // Set the values in the ReportsDto
            dto.setLow((int) lowPercentage);
            dto.setMedium((int) mediumPercentage);
            dto.setHigh((int) highPercentage);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(ERROR_MESSAGE + e.getMessage());
			logger.error(ERROR_MESSAGE, e);
		}
		return dto;
	}
}
