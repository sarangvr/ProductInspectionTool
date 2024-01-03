package com.ensat.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ensat.entities.Inspection;
import com.ensat.entities.Result;
import com.ensat.model.InspectionDetails;
import com.ensat.model.ProductDetailsDTO;
import com.ensat.repositories.InspectionRepository;

@Service
public class InspectionServiceImpl implements InspectionService {
	
    @Autowired
    private InspectionRepository inspectionRepository;
	
	@Override
	public InspectionDetails getProductInspection(Long id) {
		InspectionDetails inspectionDetails = new InspectionDetails();
		Inspection inspection = inspectionRepository.findFirstByProduct_Id(id).get();
//		inspectionDetails.setId(inspection.getId());
		inspectionDetails.setComments(inspection.getComments());
		inspectionDetails.setDate(inspectionDetails.getDate());
		inspectionDetails.setInspector(inspection.getInspector());
//		inspectionDetails.setProduct_id(inspection.getProduct().getId());
//		inspectionDetails.setResult(inspection.getResult());
		return inspectionDetails;
	}

	@Override
	public InspectionDetails editProductInspection(InspectionDetails inspectionDetails) {
		Inspection inspection = inspectionRepository.findFirstByProduct_Id(1).get();
		inspection.setComments(inspectionDetails.getComments());
//		inspection.setDate(inspectionDetails.getDate());
		inspection.setInspector(inspectionDetails.getInspector());
//		inspection.setResult(inspectionDetails.getResult());
		inspectionRepository.save(inspection);
		return inspectionDetails;
	}

	@Override
	public boolean autoInspectProducts() {
		List<ProductDetailsDTO> productList = inspectionRepository.findAllProductDetailsByPendingStatus();
		Inspection inspection;
		for(ProductDetailsDTO productDetails : productList) {
			if(productDetails.getExpiryDate().isBefore(LocalDate.now()) || productDetails.getExpiryDate().isEqual(LocalDate.now())) {
				inspection = inspectionRepository.findFirstByProduct_Id(productDetails.getId()).get();
				inspection.setComments("Failed due to product is past expiry date");
				inspection.setDate(LocalDate.now());
				inspection.setInspector("SYSTEM");
				inspection.setResult(Result.FAIL);
				inspectionRepository.save(inspection);
			}
		}
		return true;
	}

}
