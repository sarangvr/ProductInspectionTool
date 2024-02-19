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
	public CategoryGraphDto getReportsDetails() {
		CategoryGraphDto dto = new CategoryGraphDto();
		try {
			List<ProductDetailsDTO> productList = productRepository.findAllProductDetails();
			List<QualityMetric> qualityList = qualityMetricRepository.findAll();
			List<Inspection_DTLS> inspDtlsList = inspectionDtlsRepository.findAll();
			long productId = 0;
			// Initialize counts for each category
			long highCountGrocery = 0;
			long mediumCountGrocery = 0;
			long lowCountGrocery = 0;
			long highPassGrocery = 0;
			long mediumPassGrocery = 0;
			long lowPassGrocery = 0;
			long highFailGrocery = 0;
			long mediumFailGrocery = 0;
			long lowFailGrocery = 0;
			// For Dairy
			long highcountDairy = 0;
			long mediumcountDairy = 0;
			long lowcountDairy = 0;
			long highpassDairy = 0;
			long mediumpassDairy = 0;
			long lowpassDairy = 0;
			long highfailDairy = 0;
			long mediumfailDairy = 0;
			long lowfailDairy = 0;

			// For Bakery
			long highCountBakery = 0;
			long mediumCountBakery = 0;
			long lowCountBakery = 0;
			long highPassBakery = 0;
			long mediumPassBakery = 0;
			long lowPassBakery = 0;
			long highFailBakery = 0;
			long mediumFailBakery = 0;
			long lowFailBakery = 0;

			// For Beverages
			long highCountBeverages = 0;
			long mediumCountBeverages = 0;
			long lowCountBeverages = 0;
			long highPassBeverages = 0;
			long mediumPassBeverages = 0;
			long lowPassBeverages = 0;
			long highFailBeverages = 0;
			long mediumFailBeverages = 0;
			long lowFailBeverages = 0;

			// For Meat and Poultry
			long highCountMeat = 0;
			long mediumCountMeat = 0;
			long lowCountMeat = 0;
			long highPassMeat = 0;
			long mediumPassMeat = 0;
			long lowPassMeat = 0;
			long highFailMeat = 0;
			long mediumFailMeat = 0;
			long lowFailMeat = 0;

			// Iterate through the inspection details
			for (ProductDetailsDTO productsDto : productList) {
				// Assuming there is a link between Inspection_DTLS and QualityMetric through
				// productId
				productId = productsDto.getProductId();

				for (QualityMetric qualityDto : qualityList) {
					for (Inspection_DTLS inspDtls : inspDtlsList) {
						if (inspDtls.getCategory().equals(GROCERY)) {
							if (qualityDto.getQuality() == Quality.HIGH) {
								highCountGrocery++;
								if (PASS.equalsIgnoreCase(inspDtls.getResult())) {
									highPassGrocery++;
								} else if (FAIL.equalsIgnoreCase(inspDtls.getResult())) {
									highFailGrocery++;
								}

							} else if (qualityDto.getQuality() == Quality.MEDIUM) {
								mediumCountGrocery++;
								if (PASS.equalsIgnoreCase(inspDtls.getResult())) {
									mediumPassGrocery++;
								} else if (FAIL.equalsIgnoreCase(inspDtls.getResult())) {
									mediumFailGrocery++;
								}
							} else if (qualityDto.getQuality() == Quality.LOW) {
								lowcountDairy++;
								if (PASS.equalsIgnoreCase(inspDtls.getResult())) {
									lowPassGrocery++;
								} else if (FAIL.equalsIgnoreCase(inspDtls.getResult())) {
									lowFailGrocery++;
								}
							}

						} else if (inspDtls.getCategory().equals(DAIRY)) {
							if (qualityDto.getQuality() == Quality.HIGH) {
								highcountDairy++;
								if (PASS.equalsIgnoreCase(inspDtls.getResult())) {
									highpassDairy++;
								} else if (FAIL.equalsIgnoreCase(inspDtls.getResult())) {
									highfailDairy++;
								}

							} else if (qualityDto.getQuality() == Quality.MEDIUM) {
								mediumcountDairy++;
								if (PASS.equalsIgnoreCase(inspDtls.getResult())) {
									mediumpassDairy++;
								} else if (FAIL.equalsIgnoreCase(inspDtls.getResult())) {
									mediumfailDairy++;
								}
							} else if (qualityDto.getQuality() == Quality.LOW) {
								lowcountDairy++;
								if (PASS.equalsIgnoreCase(inspDtls.getResult())) {
									lowpassDairy++;
								} else if (FAIL.equalsIgnoreCase(inspDtls.getResult())) {
									mediumfailDairy++;
								}
							}

						} else if (inspDtls.getCategory().equals(BAKERY)) {
							if (qualityDto.getQuality() == Quality.HIGH) {
								highCountBakery++;
								if (PASS.equalsIgnoreCase(inspDtls.getResult())) {
									highPassBakery++;
								} else if (FAIL.equalsIgnoreCase(inspDtls.getResult())) {
									highFailBakery++;
								}

							} else if (qualityDto.getQuality() == Quality.MEDIUM) {
								mediumCountBakery++;
								if (PASS.equalsIgnoreCase(inspDtls.getResult())) {
									mediumPassBakery++;
								} else if (FAIL.equalsIgnoreCase(inspDtls.getResult())) {
									mediumFailBakery++;
								}
							} else if (qualityDto.getQuality() == Quality.LOW) {
								lowCountBakery++;
								if (PASS.equalsIgnoreCase(inspDtls.getResult())) {
									lowPassBakery++;
								} else if (FAIL.equalsIgnoreCase(inspDtls.getResult())) {
									lowFailBakery++;
								}
							}

						} else if (inspDtls.getCategory().equals(BEVERAGES)) {
							if (qualityDto.getQuality() == Quality.HIGH) {
								highCountBeverages++;
								if (PASS.equalsIgnoreCase(inspDtls.getResult())) {
									highPassBeverages++;
								} else if (FAIL.equalsIgnoreCase(inspDtls.getResult())) {
									highFailBeverages++;
								}

							} else if (qualityDto.getQuality() == Quality.MEDIUM) {
								mediumCountBeverages++;
								if (PASS.equalsIgnoreCase(inspDtls.getResult())) {
									mediumPassBeverages++;
								} else if (FAIL.equalsIgnoreCase(inspDtls.getResult())) {
									mediumFailBeverages++;
								}
							} else if (qualityDto.getQuality() == Quality.LOW) {
								lowCountBeverages++;
								if (PASS.equalsIgnoreCase(inspDtls.getResult())) {
									lowPassBeverages++;
								} else if (FAIL.equalsIgnoreCase(inspDtls.getResult())) {
									lowFailBeverages++;
								}
							}

						} else if (inspDtls.getCategory().equals(MEAT_AND_POULTRY)) {
							if (qualityDto.getQuality() == Quality.HIGH) {
								highCountMeat++;
								if (PASS.equalsIgnoreCase(inspDtls.getResult())) {
									highPassMeat++;
								} else if (FAIL.equalsIgnoreCase(inspDtls.getResult())) {
									highFailMeat++;
								}

							} else if (qualityDto.getQuality() == Quality.MEDIUM) {
								mediumCountMeat++;
								if (PASS.equalsIgnoreCase(inspDtls.getResult())) {
									mediumPassMeat++;
								} else if (FAIL.equalsIgnoreCase(inspDtls.getResult())) {
									mediumFailMeat++;
								}
							} else if (qualityDto.getQuality() == Quality.LOW) {
								lowCountMeat++;
								if (PASS.equalsIgnoreCase(inspDtls.getResult())) {
									lowPassMeat++;
								} else if (FAIL.equalsIgnoreCase(inspDtls.getResult())) {
									lowFailMeat++;
								}
							}
						}

					}
				}
				// Set values in the DTO
				dto.setHighCountGrocery((int) highCountGrocery);
				dto.setMediumCountGrocery((int) mediumCountGrocery);
				dto.setLowCountGrocery((int) lowCountGrocery);
				dto.setHighPassGrocery((int) highPassGrocery);
				dto.setMediumPassGrocery((int) mediumPassGrocery);
				dto.setLowPassGrocery((int) lowPassGrocery);
				dto.setHighFailGrocery((int) highFailGrocery);
				dto.setMediumFailGrocery((int) mediumFailGrocery);
				dto.setLowFailGrocery((int) lowFailGrocery);

				dto.setHighCountDairy((int) highcountDairy);
				dto.setMediumCountDairy((int) mediumcountDairy);
				dto.setLowCountDairy((int) lowcountDairy);
				dto.setHighPassDairy((int) highpassDairy);
				dto.setMediumPassDairy((int) mediumpassDairy);
				dto.setLowPassDairy((int) lowpassDairy);
				dto.setHighFailDairy((int) highfailDairy);
				dto.setMediumFailDairy((int) mediumfailDairy);
				dto.setLowFailDairy((int) lowfailDairy);

				// For Beverages
				dto.setHighCountBeverages((int) highCountBeverages);
				dto.setMediumCountBeverages((int) mediumCountBeverages);
				dto.setLowCountBeverages((int) lowCountBeverages);
				dto.setHighPassBeverages((int) highPassBeverages);
				dto.setMediumPassBeverages((int) mediumPassBeverages);
				dto.setLowPassBeverages((int) lowPassBeverages);
				dto.setHighFailBeverages((int) highFailBeverages);
				dto.setMediumFailBeverages((int) mediumFailBeverages);
				dto.setLowFailBeverages((int) lowFailBeverages);

				// For Bakery
				dto.setHighCountBakery((int) highCountBakery);
				dto.setMediumCountBakery((int) mediumCountBakery);
				dto.setLowCountBakery((int) lowCountBakery);
				dto.setHighPassBakery((int) highPassBakery);
				dto.setMediumPassBakery((int) mediumPassBakery);
				dto.setLowPassBakery((int) lowPassBakery);
				dto.setHighFailBakery((int) highFailBakery);
				dto.setMediumFailBakery((int) mediumFailBakery);
				dto.setLowFailBakery((int) lowFailBakery);

				// For Meat and Poultry
				dto.setHighCountMeat((int) highCountMeat);
				dto.setMediumCountMeat((int) mediumCountMeat);
				dto.setLowCountMeat((int) lowCountMeat);
				dto.setHighPassMeat((int) highPassMeat);
				dto.setMediumPassMeat((int) mediumPassMeat);
				dto.setLowPassMeat((int) lowPassMeat);
				dto.setHighFailMeat((int) highFailMeat);
				dto.setMediumFailMeat((int) mediumFailMeat);
				dto.setLowFailMeat((int) lowFailMeat);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(ERROR_MESSAGE + e.getMessage());
			logger.error(ERROR_MESSAGE, e);
		}
		return dto;
	}
}
