package com.ensat.services;

import java.util.List;

import com.ensat.model.InspectionDetails;
import com.ensat.model.InspectionDetailsDTO;

public interface InspectionService {
	
	List<InspectionDetailsDTO> listAllInspection();

	InspectionDetails getProductInspection(Long id);
	
	InspectionDetails editProductInspection(InspectionDetails inspection);
	
	boolean autoInspectProducts();
	
}
