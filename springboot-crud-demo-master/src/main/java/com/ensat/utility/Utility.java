package com.ensat.utility;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ensat.category.entities.Bakery;
import com.ensat.category.entities.Beverages;
import com.ensat.category.entities.Dairy;
import com.ensat.category.entities.Grocery;
import com.ensat.category.entities.MeatAndPoultry;
import com.ensat.entities.Inspection_DTLS;
import com.ensat.entities.Quality;
import com.ensat.entities.QualityMetric;
import com.ensat.model.ProductDetails;
import com.ensat.services.InspectionServiceImpl;

public class Utility implements Constants {
	private static final Logger logger = LoggerFactory.getLogger(Utility.class);
	public static long findInspectionIdByProductId(List<Inspection_DTLS> inspDtls, Long productId) {
		for (Inspection_DTLS inspection : inspDtls) {
            if (inspection.getProduct().getProductId() == productId) {
                return inspection.getInspectionId();
            }
        }
        return 0; 
    }
	
	public static long findQualityMetricIdByProductId(List<QualityMetric> qualityDtls, Long productId) {
		for (QualityMetric quality : qualityDtls) {
            if (quality.getProduct().getProductId() == productId) {
                return quality.getId();
            }
        }
        return 0; 
    }
	
	public static boolean validateEmptyString(String variable) {
		if(variable != null && variable != Constants.EMPTY_STRING) {
			return true;
		}
		return false;
	}

	public static long findGroceryIdByProductId(List<Grocery> groceryList, long productId) {
		for (Grocery grocery : groceryList) {
            if (grocery.getProduct().getProductId() == productId) {
                return grocery.getId();
            }
        }
		return 0;
	}

	public static long findDairyIdByProductId(List<Dairy> dairyList, long productId) {
		for (Dairy dairy : dairyList) {
			if (dairy.getProduct().getProductId() == productId) {
				return dairy.getId();
			}
		}
		return 0;
	}

	public static long getBakeryIdFromProductId(List<Bakery> bakeryList, long productId) {
		for (Bakery bakery : bakeryList) {
			if (bakery.getProduct().getProductId() == productId) {
				return bakery.getId();
			}
		}
		return 0;
	}
	
	public static long getBeveragesIdFromProductId(List<Beverages> beveragesList, long productId) {
		for (Beverages beverages : beveragesList) {
			if (beverages.getProduct().getProductId() == productId) {
				return beverages.getId();
			}
		}
		return 0;
	}
	
	public static long getMeatAndPoulTryIdFromProductId(List<MeatAndPoultry> meatAndPoultryList, long productId) {
		for (MeatAndPoultry meatAndPoultry : meatAndPoultryList) {
			if (meatAndPoultry.getProduct().getProductId() == productId) {
				return meatAndPoultry.getId();
			}
		}
		return 0;
	}

	public static ProductDetails deriveQuality(ProductDetails productDetails) {
		try {
			String categoryName = productDetails.getCategory();

			if (categoryName.equals(GROCERY)) {
				if (productDetails.getFreshnessGrocery().equals(HIGH)
						&& productDetails.getNutrientContent().equals(HIGH)) {
					productDetails.setGroceryQuality(Quality.HIGH);
				} else if (productDetails.getFreshnessGrocery().equals(MEDIUM)
						&& productDetails.getNutrientContent().equals(MEDIUM)) {
					productDetails.setGroceryQuality(Quality.MEDIUM);
				} else if (productDetails.getFreshnessGrocery().equals(LOW)
						&& productDetails.getNutrientContent().equals(LOW)) {
					productDetails.setGroceryQuality(Quality.LOW);
				} else if (productDetails.getFreshnessGrocery().equals(HIGH)
						&& productDetails.getNutrientContent().equals(MEDIUM)) {
					productDetails.setGroceryQuality(Quality.HIGH);
				} else if (productDetails.getFreshnessGrocery().equals(HIGH)
						&& productDetails.getNutrientContent().equals(LOW)) {
					productDetails.setGroceryQuality(Quality.MEDIUM);
				} else if (productDetails.getFreshnessGrocery().equals(MEDIUM)
						&& productDetails.getNutrientContent().equals(HIGH)) {
					productDetails.setGroceryQuality(Quality.HIGH);
				} else if (productDetails.getFreshnessGrocery().equals(MEDIUM)
						&& productDetails.getNutrientContent().equals(LOW)) {
					productDetails.setGroceryQuality(Quality.LOW);
				} else if (productDetails.getFreshnessGrocery().equals(LOW)
						&& productDetails.getNutrientContent().equals(HIGH)) {
					productDetails.setGroceryQuality(Quality.MEDIUM);
				} else if (productDetails.getFreshnessGrocery().equals(LOW)
						&& productDetails.getNutrientContent().equals(MEDIUM)) {
					productDetails.setGroceryQuality(Quality.LOW);
				}
			} else if (categoryName.equals(DAIRY)) {
				if (productDetails.getFreshnessDairy().equals(HIGH)
						&& productDetails.getFatContent().equals(HIGH)) {
					productDetails.setDairyQuality(Quality.HIGH);
				} else if (productDetails.getFreshnessDairy().equals(MEDIUM)
						&& productDetails.getFatContent().equals(MEDIUM)) {
					productDetails.setDairyQuality(Quality.MEDIUM);
				} else if (productDetails.getFreshnessDairy().equals(LOW)
						&& productDetails.getFatContent().equals(LOW)) {
					productDetails.setDairyQuality(Quality.LOW);
				} else if (productDetails.getFreshnessDairy().equals(HIGH)
						&& productDetails.getFatContent().equals(MEDIUM)) {
					productDetails.setDairyQuality(Quality.HIGH);
				} else if (productDetails.getFreshnessDairy().equals(HIGH)
						&& productDetails.getFatContent().equals(LOW)) {
					productDetails.setDairyQuality(Quality.MEDIUM);
				} else if (productDetails.getFreshnessDairy().equals(MEDIUM)
						&& productDetails.getFatContent().equals(HIGH)) {
					productDetails.setDairyQuality(Quality.HIGH);
				} else if (productDetails.getFreshnessDairy().equals(MEDIUM)
						&& productDetails.getFatContent().equals(LOW)) {
					productDetails.setDairyQuality(Quality.LOW);
				} else if (productDetails.getFreshnessDairy().equals(LOW)
						&& productDetails.getFatContent().equals(HIGH)) {
					productDetails.setDairyQuality(Quality.MEDIUM);
				} else if (productDetails.getFreshnessDairy().equals(LOW)
						&& productDetails.getFatContent().equals(MEDIUM)) {
					productDetails.setDairyQuality(Quality.LOW);
				}
			} else if (categoryName.equals(BAKERY)) {
				if (productDetails.getFreshnessBakery().equals(HIGH)
						&& productDetails.getMoistureContent().equals(HIGH)) {
					productDetails.setBakeryQuality(Quality.HIGH);
				} else if (productDetails.getFreshnessBakery().equals(MEDIUM)
						&& productDetails.getMoistureContent().equals(MEDIUM)) {
					productDetails.setBakeryQuality(Quality.MEDIUM);
				} else if (productDetails.getFreshnessBakery().equals(LOW)
						&& productDetails.getMoistureContent().equals(LOW)) {
					productDetails.setBakeryQuality(Quality.LOW);
				} else if (productDetails.getFreshnessBakery().equals(HIGH)
						&& productDetails.getMoistureContent().equals(MEDIUM)) {
					productDetails.setBakeryQuality(Quality.HIGH);
				} else if (productDetails.getFreshnessBakery().equals(HIGH)
						&& productDetails.getMoistureContent().equals(LOW)) {
					productDetails.setBakeryQuality(Quality.MEDIUM);
				} else if (productDetails.getFreshnessBakery().equals(MEDIUM)
						&& productDetails.getMoistureContent().equals(HIGH)) {
					productDetails.setBakeryQuality(Quality.HIGH);
				} else if (productDetails.getFreshnessBakery().equals(MEDIUM)
						&& productDetails.getMoistureContent().equals(LOW)) {
					productDetails.setBakeryQuality(Quality.HIGH);
				} else if (productDetails.getFreshnessBakery().equals(LOW)
						&& productDetails.getMoistureContent().equals(HIGH)) {
					productDetails.setBakeryQuality(Quality.MEDIUM);
				} else if (productDetails.getFreshnessBakery().equals(LOW)
						&& productDetails.getMoistureContent().equals(MEDIUM)) {
					productDetails.setBakeryQuality(Quality.LOW);
				}
			} else if (categoryName.equals(BEVERAGES)) {
				if (productDetails.getNaturalIngredients().equals(YES)
						&& productDetails.getShelfLife().equals(HIGH)) {
					productDetails.setBeveragesQuality(Quality.HIGH);
				} else if (productDetails.getNaturalIngredients().equals(YES)
						&& productDetails.getShelfLife().equals(MEDIUM)) {
					productDetails.setBeveragesQuality(Quality.MEDIUM);
				} else if (productDetails.getNaturalIngredients().equals(YES)
						&& productDetails.getShelfLife().equals(LOW)) {
					productDetails.setBeveragesQuality(Quality.LOW);
				} else if (productDetails.getNaturalIngredients().equals(NO)
						&& productDetails.getShelfLife().equals(HIGH)) {
					productDetails.setBeveragesQuality(Quality.MEDIUM);
				} else if (productDetails.getNaturalIngredients().equals(NO)
						&& productDetails.getShelfLife().equals(MEDIUM)) {
					productDetails.setBeveragesQuality(Quality.MEDIUM);
				} else if (productDetails.getNaturalIngredients().equals(NO)
						&& productDetails.getShelfLife().equals(LOW)) {
					productDetails.setBeveragesQuality(Quality.LOW);
				}
			} else if (categoryName.equals(MEAT_AND_POULTRY)) {
				if (productDetails.getOdor().equals(UNCHANGED) && productDetails.getFreshnessMeat().equals(HIGH)) {
					productDetails.setMeatAndPoultryQuality(Quality.HIGH);
				} else if (productDetails.getOdor().equals(UNCHANGED)
						&& productDetails.getFreshnessMeat().equals(MEDIUM)) {
					productDetails.setMeatAndPoultryQuality(Quality.MEDIUM);
				} else if (productDetails.getOdor().equals(UNCHANGED)
						&& productDetails.getFreshnessMeat().equals(LOW)) {
					productDetails.setMeatAndPoultryQuality(Quality.LOW);
				} else if (productDetails.getOdor().equals(CHANGED)
						&& productDetails.getFreshnessMeat().equals(HIGH)) {
					productDetails.setMeatAndPoultryQuality(Quality.MEDIUM);
				} else if (productDetails.getOdor().equals(CHANGED)
						&& productDetails.getFreshnessMeat().equals(MEDIUM)) {
					productDetails.setMeatAndPoultryQuality(Quality.MEDIUM);
				} else if (productDetails.getOdor().equals(CHANGED)
						&& productDetails.getFreshnessMeat().equals(LOW)) {
					productDetails.setMeatAndPoultryQuality(Quality.LOW);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(ERROR_DERIVE_QUALITY + e.getMessage());
			logger.error(ERROR_MESSAGE, e);
		}
		return productDetails;
	}
	

}
