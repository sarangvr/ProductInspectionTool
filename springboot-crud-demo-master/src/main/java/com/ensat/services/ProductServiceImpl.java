package com.ensat.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Service;

import com.ensat.category.entities.Bakery;
import com.ensat.category.entities.Beverages;
import com.ensat.category.entities.CategoryDto;
import com.ensat.category.entities.Dairy;
import com.ensat.category.entities.Grocery;
import com.ensat.category.entities.MeatAndPoultry;
import com.ensat.category.repositories.BakeryRepository;
import com.ensat.category.repositories.BeveragesRepository;
import com.ensat.category.repositories.DairyRepository;
import com.ensat.category.repositories.GroceryRepository;
import com.ensat.category.repositories.MeatAndPoultryRepository;
import com.ensat.entities.Inspection_DTLS;
import com.ensat.entities.Product;
import com.ensat.entities.QualityMetric;
import com.ensat.model.ProductDetails;
import com.ensat.model.ProductDetailsDTO;
import com.ensat.repositories.InspectionDtlsRepository;
import com.ensat.repositories.ProductRepository;
import com.ensat.repositories.QualityMetricRepository;
import com.ensat.utility.Constants;
import com.ensat.utility.Utility;
/**
 * Product service implement.
 */
@Service
public class ProductServiceImpl implements ProductService, Constants {
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
	public List<ProductDetails> getAllCategoryDetails(CategoryDto categoryDto) {
    	String category = categoryDto.getCategory();
    	long productId = categoryDto.getProductId();
    	if(category.equals(GROCERY)) {
    		List<Grocery> groceryList = groceryRepository.findAll();
			long groceryId = Utility.findGroceryIdByProductId(groceryList, productId);
			groceryRepository.findById(groceryId);
		} else if(category.equals(DAIRY)) {
			List<Dairy> dairyList = dairyRepository.findAll();
			long dairyId = Utility.findDairyIdByProductId(dairyList, productId);
			Dairy dairy = dairyRepository.findById(dairyId).get();
		} else if(category.equals(BAKERY)) {
			List<Bakery> bakeryList = bakeryRepository.findAll();
			long bakeryId = Utility.getBakeryIdFromProductId(bakeryList, productId);
			Bakery bakery = bakeryRepository.findById(bakeryId).get();
		} else if(category.equals(BEVERAGES)) {
			List<Beverages> beveragesList = beveragesRepository.findAll();
			long beveragesId = Utility.getBeveragesIdFromProductId(beveragesList, productId);
			Beverages beverages = beveragesRepository.findById(beveragesId).get();
		} else if(category.equals(MEAT_AND_POULTRY)) {
			List<MeatAndPoultry> meatAndPoultryList = meatAndPoultryRepository.findAll();
			long meatAndPoultryId = Utility.getMeatAndPoulTryIdFromProductId(meatAndPoultryList, productId);
			MeatAndPoultry meatAndPoultry = meatAndPoultryRepository.findById(meatAndPoultryId).get();
		}
		return null;
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

    @SuppressWarnings("unchecked")
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
			inspectionDtls.setProduct(product);
			inspectionDtls.setCategory(productDetails.getCategory());
			inspectionDtls.setDate(productDetails.getExpiryDate());
			inspectionDtls.setInspector("Insp_1");
			inspectionDtls.setProductName(productDetails.getName());
			inspectionDtls.setResult(PENDING);
			inspectionDtlsRepository.save(inspectionDtls);
			
			if(Utility.validateEmptyString(productDetails.getCategory())) {
				if(productDetails.getCategory().equals(GROCERY)) {
					Grocery grocery =  new Grocery();
					grocery.setProduct(product);
					grocery.setFreshnessGrocery(productDetails.getFreshnessGrocery());
					grocery.setNonGmo(productDetails.getNonGmo());
					grocery.setNutrientContent(productDetails.getNutrientContent());
					grocery.setOrganic(productDetails.getOrganic());
					grocery.setWholeGrain(productDetails.getWholeGrain());
					groceryRepository.save(grocery);
					
				}else if(productDetails.getCategory().equals(DAIRY)) {
					Dairy dairy = new Dairy();
					dairy.setProduct(product);
					dairy.setFatContent(productDetails.getFatContent());
					dairy.setFreshnessDairy(productDetails.getFreshnessDairy());
					dairy.setHomogenization(productDetails.getHomogenization());
					dairy.setPasteurization(productDetails.getPasteurization());
					dairy.setPurity(productDetails.getPurity());
					dairyRepository.save(dairy);
				}else if(productDetails.getCategory().equals(BAKERY)) {
					Bakery bakery = new Bakery();
					bakery.setProduct(product);
					bakery.setFlavorBakery(productDetails.getFlavorBakery());
					bakery.setFreshnessBakery(productDetails.getFreshnessBakery());
					bakery.setMoistureContent(productDetails.getMoistureContent());
					bakery.setTexture(productDetails.getTexture());
					bakery.setUniSizeShape(productDetails.getUniSizeShape());
					bakeryRepository.save(bakery);
				}else if(productDetails.getCategory().equals(BEVERAGES)) {
					Beverages beverages = new Beverages();
					beverages.setProduct(product);
					beverages.setClarity(productDetails.getClarity());
					beverages.setColorBeverages(productDetails.getColorBeverages());
					beverages.setFlavorBeverages(productDetails.getFlavorBeverages());
					beverages.setNaturalIngredients(productDetails.getNaturalIngredients());
					beverages.setShelfLife(productDetails.getShelfLife());
					beveragesRepository.save(beverages);
				}else if(productDetails.getCategory().equals(MEAT_AND_POULTRY)) {
					MeatAndPoultry meatAndPoultry = new MeatAndPoultry();
					meatAndPoultry.setProduct(product);
					meatAndPoultry.setColorMeat(productDetails.getColorMeat());
					meatAndPoultry.setFreshnessMeat(productDetails.getFreshnessMeat());
					meatAndPoultry.setMarbling(productDetails.getMarbling());
					meatAndPoultry.setOdor(productDetails.getOdor());
					meatAndPoultry.setTemperatureControl(productDetails.getTemperatureControl());
					meatAndPoultryRepository.save(meatAndPoultry);
				}
			}

		} catch (DataIntegrityViolationException e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				ConstraintViolationException constraintViolationException = (ConstraintViolationException) e.getCause();
				logger.error("Constraint violation details - Constraint Name: {}, SQL: {}",
						constraintViolationException.getConstraintName(), constraintViolationException.getSQL());
			}
			logger.error("Error while processing DataIntegrityViolationException", e);
		} catch (NotReadablePropertyException e) {
			logger.error("Error while processing NotReadablePropertyException", e);
			e.printStackTrace();
			System.out.println("Error Inspection_DTLS: " + e.getMessage());
		}
		return product;
	}

    @Override
	public boolean deleteProduct(Long id) {
		try {
			Product product = productRepository.findById(id).get();
			String oldCategory = product.getCategory();
			if(oldCategory.equals(GROCERY)) {
				List<Grocery> groceryList = groceryRepository.findAll();
				long groceryId = Utility.findGroceryIdByProductId(groceryList, id);
				groceryRepository.deleteById(groceryId);
				
			} else if(oldCategory.equals(DAIRY)) {
				List<Dairy> dairyList = dairyRepository.findAll();
				long dairyId = Utility.findDairyIdByProductId(dairyList, id);
				dairyRepository.deleteById(dairyId);
			}
			List<Inspection_DTLS> inspDtlsList =  inspectionDtlsRepository.findAll();
			long inspectionId = Utility.findInspectionIdByProductId(inspDtlsList, id);
			inspectionDtlsRepository.deleteById(inspectionId);
			
			List<QualityMetric> qualityList = qualityMetricRepository.findAll();
			long qualityId = Utility.findQualityMetricIdByProductId(qualityList, id);
			qualityMetricRepository.deleteById(qualityId);
			
			productRepository.deleteById(id);
			System.out.println("Delete Successful");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error createProductNew: " + e.getMessage());
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductDetails editProductById(ProductDetails productDetails) {
		try {
			String oldCategory = EMPTY_STRING;
			long productId = productDetails.getProductId();
			Product product = productRepository.findById(productDetails.getProductId()).get();
			oldCategory = product.getCategory();
			product.setProductId(productDetails.getProductId());
			product.setCategory(productDetails.getCategory());
			product.setDescription(productDetails.getDescription());
			product.setName(productDetails.getName());
			product.setPrice(productDetails.getPrice());
			product.setQuantity(productDetails.getQuantity());
			product = productRepository.save(product);
			
			List<QualityMetric> qualityList = qualityMetricRepository.findAll();
			long qualityId = Utility.findQualityMetricIdByProductId(qualityList, productDetails.getProductId());
			QualityMetric qualityMetric = qualityMetricRepository.findById(qualityId).get();
			qualityMetric.setColour(productDetails.getColour());
			qualityMetric.setExpiryDate(productDetails.getExpiryDate());
			qualityMetric.setManufacturingDate(productDetails.getManufacturingDate());
			qualityMetric.setQuality(productDetails.getQuality());
			qualityMetric.setWeight(productDetails.getWeight());
			qualityMetricRepository.save(qualityMetric);
			
			List<Inspection_DTLS> inspDtlsList =  inspectionDtlsRepository.findAll();
			long inspectionId = Utility.findInspectionIdByProductId(inspDtlsList, productDetails.getProductId());
			Inspection_DTLS inspectionDtls = inspectionDtlsRepository.findById(inspectionId).get();
				inspectionDtls.setProduct(product);
				inspectionDtls.setCategory(productDetails.getCategory());
				inspectionDtls.setDate(productDetails.getExpiryDate());
				inspectionDtls.setInspector("Insp_1");
				inspectionDtls.setProductName(productDetails.getName());
				inspectionDtls.setResult(PENDING);
				inspectionDtlsRepository.save(inspectionDtls);
				
				
				if (productDetails.getCategory().equals(oldCategory)) {
					if (productDetails.getCategory().equals(GROCERY)) {
						List<Grocery> groceryList = groceryRepository.findAll();
						long groceryId = Utility.findGroceryIdByProductId(groceryList, productDetails.getProductId());
						Grocery grocery = (Grocery) groceryRepository.findById(groceryId).get();

						grocery.setProduct(product);
						grocery.setFreshnessGrocery(productDetails.getFreshnessGrocery());
						grocery.setNonGmo(productDetails.getNonGmo());
						grocery.setNutrientContent(productDetails.getNutrientContent());
						grocery.setOrganic(productDetails.getOrganic());
						grocery.setWholeGrain(productDetails.getWholeGrain());
						groceryRepository.save(grocery);

					} else if (productDetails.getCategory().equals(DAIRY)) {
						List<Dairy> dairyList = dairyRepository.findAll();
						long dairyId = Utility.findDairyIdByProductId(dairyList, productDetails.getProductId());
						Dairy dairy = dairyRepository.findById(dairyId).get();

						dairy.setProduct(product);
						dairy.setFatContent(productDetails.getFatContent());
						dairy.setFreshnessDairy(productDetails.getFreshnessDairy());
						dairy.setHomogenization(productDetails.getHomogenization());
						dairy.setPasteurization(productDetails.getPasteurization());
						dairy.setPurity(productDetails.getPurity());
						dairyRepository.save(dairy);
					} else if (productDetails.getCategory().equals(BAKERY)) {
						List<Bakery> bakeryList = bakeryRepository.findAll();
						long bakeryId = Utility.getBakeryIdFromProductId(bakeryList, productId);
						Bakery bakery = bakeryRepository.findById(bakeryId).get();
						
						bakery.setProduct(product);
						bakery.setFlavorBakery(productDetails.getFlavorBakery());
						bakery.setFreshnessBakery(productDetails.getFreshnessBakery());
						bakery.setMoistureContent(productDetails.getMoistureContent());
						bakery.setTexture(productDetails.getTexture());
						bakery.setUniSizeShape(productDetails.getUniSizeShape());
						bakeryRepository.save(bakery);
					} else if (productDetails.getCategory().equals(BEVERAGES)) {
						List<Beverages> beveragesList = beveragesRepository.findAll();
						long beveragesId = Utility.getBeveragesIdFromProductId(beveragesList, productId);
						Beverages beverages = beveragesRepository.findById(beveragesId).get();
						
						beverages.setProduct(product);
						beverages.setClarity(productDetails.getClarity());
						beverages.setColorBeverages(productDetails.getColorBeverages());
						beverages.setFlavorBeverages(productDetails.getFlavorBeverages());
						beverages.setNaturalIngredients(productDetails.getNaturalIngredients());
						beverages.setShelfLife(productDetails.getShelfLife());
						beveragesRepository.save(beverages);
					} else if (productDetails.getCategory().equals(MEAT_AND_POULTRY)) {
						List<MeatAndPoultry> meatAndPoultryList = meatAndPoultryRepository.findAll();
						long meatAndPoultryId = Utility.getMeatAndPoulTryIdFromProductId(meatAndPoultryList, productId);
						MeatAndPoultry meatAndPoultry = meatAndPoultryRepository.findById(meatAndPoultryId).get();
						
						meatAndPoultry.setProduct(product);
						meatAndPoultry.setColorMeat(productDetails.getColorMeat());
						meatAndPoultry.setFreshnessMeat(productDetails.getFreshnessMeat());
						meatAndPoultry.setMarbling(productDetails.getMarbling());
						meatAndPoultry.setOdor(productDetails.getOdor());
						meatAndPoultry.setTemperatureControl(productDetails.getTemperatureControl());
						meatAndPoultryRepository.save(meatAndPoultry);
					}
				}else {
					
					//updating to new category
					if (productDetails.getCategory().equals(GROCERY)) {
						Grocery grocery = new Grocery();

						grocery.setProduct(product);
						grocery.setFreshnessGrocery(productDetails.getFreshnessGrocery());
						grocery.setNonGmo(productDetails.getNonGmo());
						grocery.setNutrientContent(productDetails.getNutrientContent());
						grocery.setOrganic(productDetails.getOrganic());
						grocery.setWholeGrain(productDetails.getWholeGrain());
						groceryRepository.save(grocery);

					} else if (productDetails.getCategory().equals(DAIRY)) {
						Dairy dairy = new Dairy();

						dairy.setProduct(product);
						dairy.setFatContent(productDetails.getFatContent());
						dairy.setFreshnessDairy(productDetails.getFreshnessDairy());
						dairy.setHomogenization(productDetails.getHomogenization());
						dairy.setPasteurization(productDetails.getPasteurization());
						dairy.setPurity(productDetails.getPurity());
						dairyRepository.save(dairy);
					} else if(productDetails.getCategory().equals(BAKERY)) {
						Bakery bakery = new Bakery();
						
						bakery.setProduct(product);
						bakery.setFlavorBakery(productDetails.getFlavorBakery());
						bakery.setFreshnessBakery(productDetails.getFreshnessBakery());
						bakery.setMoistureContent(productDetails.getMoistureContent());
						bakery.setTexture(productDetails.getTexture());
						bakery.setUniSizeShape(productDetails.getUniSizeShape());
						bakeryRepository.save(bakery);
					}else if(productDetails.getCategory().equals(BEVERAGES)) {
						Beverages beverages = new Beverages();
						
						beverages.setProduct(product);
						beverages.setClarity(productDetails.getClarity());
						beverages.setColorBeverages(productDetails.getColorBeverages());
						beverages.setFlavorBeverages(productDetails.getFlavorBeverages());
						beverages.setNaturalIngredients(productDetails.getNaturalIngredients());
						beverages.setShelfLife(productDetails.getShelfLife());
						beveragesRepository.save(beverages);
					}else if(productDetails.getCategory().equals(MEAT_AND_POULTRY)) {
						MeatAndPoultry meatAndPoultry = new MeatAndPoultry();
						
						meatAndPoultry.setProduct(product);
						meatAndPoultry.setColorMeat(productDetails.getColorMeat());
						meatAndPoultry.setFreshnessMeat(productDetails.getFreshnessMeat());
						meatAndPoultry.setMarbling(productDetails.getMarbling());
						meatAndPoultry.setOdor(productDetails.getOdor());
						meatAndPoultry.setTemperatureControl(productDetails.getTemperatureControl());
						meatAndPoultryRepository.save(meatAndPoultry);
					}
					//Delete
					if(oldCategory.equals(GROCERY)) {
						List<Grocery> groceryList = groceryRepository.findAll();
						long groceryId = Utility.findGroceryIdByProductId(groceryList, productId);
						groceryRepository.deleteById(groceryId);
						
					} else if(oldCategory.equals(DAIRY)) {
						List<Dairy> dairyList = dairyRepository.findAll();
						long dairyId = Utility.findDairyIdByProductId(dairyList, productId);
						dairyRepository.deleteById(dairyId);
					} else if(oldCategory.equals(BAKERY)) {
						List<Bakery> bakeryList = bakeryRepository.findAll();
						long bakeryId = Utility.getBakeryIdFromProductId(bakeryList, productId);
						bakeryRepository.deleteById(bakeryId);
					} else if(oldCategory.equals(BEVERAGES)) {
						List<Beverages> beveragesList = beveragesRepository.findAll();
						long beveragesId = Utility.getBeveragesIdFromProductId(beveragesList, productId);
						beveragesRepository.deleteById(beveragesId);
					} else if(oldCategory.equals(MEAT_AND_POULTRY)) {
						List<MeatAndPoultry> meatAndPoultryList = meatAndPoultryRepository.findAll();
						long meatAndPoultryId = Utility.getMeatAndPoulTryIdFromProductId(meatAndPoultryList, productId);
						meatAndPoultryRepository.deleteById(meatAndPoultryId);
					}
				}
				
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error createProductNew: " + e.getMessage());
		}
		return productDetails;
	}


}
