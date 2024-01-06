package com.ensat.utility;

import java.util.List;

import com.ensat.category.entities.Bakery;
import com.ensat.category.entities.Beverages;
import com.ensat.category.entities.Dairy;
import com.ensat.category.entities.Grocery;
import com.ensat.category.entities.MeatAndPoultry;
import com.ensat.entities.Inspection_DTLS;
import com.ensat.entities.QualityMetric;

public class Utility implements Constants {
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
	

}
