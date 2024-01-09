package com.ensat.services;

import java.util.List;

import com.ensat.model.InspectionDetails;
import com.ensat.model.InspectionDetailsDTO;
import com.ensat.model.ReportsDto;

public interface InspectionService {
	
	List<InspectionDetailsDTO> listAllInspection();
	
	boolean autoInspectProducts();

	ReportsDto getReportsDetails();
	
}
