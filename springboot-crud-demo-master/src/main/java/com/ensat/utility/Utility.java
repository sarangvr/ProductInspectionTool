package com.ensat.utility;

import java.util.List;

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
	
	

}
