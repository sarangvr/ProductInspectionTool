package com.ensat.services;

import com.ensat.model.InspectionDetails;

public interface InspectionService {

	InspectionDetails getProductInspection(Long id);
	
	InspectionDetails editProductInspection(InspectionDetails inspection);
	
	boolean autoInspectProducts();
	
}
