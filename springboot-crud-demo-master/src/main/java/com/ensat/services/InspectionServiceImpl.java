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

import com.ensat.category.entities.CategoryDtlsDto;
import com.ensat.category.entities.CategoryDto;
import com.ensat.category.repositories.BakeryRepository;
import com.ensat.category.repositories.BeveragesRepository;
import com.ensat.category.repositories.DairyRepository;
import com.ensat.category.repositories.GroceryRepository;
import com.ensat.category.repositories.MeatAndPoultryRepository;
import com.ensat.entities.Product;
import com.ensat.model.InspectionDetails;
import com.ensat.model.InspectionDetailsDTO;
import com.ensat.model.ProductDetails;
import com.ensat.model.ProductDetailsDTO;
import com.ensat.repositories.InspectionDtlsRepository;
import com.ensat.repositories.ProductRepository;
import com.ensat.repositories.QualityMetricRepository;
import com.ensat.utility.Constants;

@Service
public class InspectionServiceImpl implements InspectionService, Constants {
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
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
		}
		
		
		return list;
	}


	@Override
	public InspectionDetails getProductInspection(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public InspectionDetails editProductInspection(InspectionDetails inspection) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean autoInspectProducts() {
		// TODO Auto-generated method stub
		return false;
	}

}
