package com.ensat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ensat.model.InspectionDetails;
import com.ensat.services.InspectionService;

@RestController
public class InspectionController {
	
	@Autowired
	InspectionService inspectionService;
	
    @GetMapping("/getProductInspectionTest/{id}")
    public InspectionDetails getInspectionDetailsTest(@PathVariable Long id) {
    	return inspectionService.getProductInspection(id);
    }
    
    @PostMapping("/editProductInspection")
    public InspectionDetails editInspectionDetails(@RequestBody InspectionDetails inspectionDetails) {
 	   return inspectionService.editProductInspection(inspectionDetails);
    }
    
    @PostMapping("/autoInspectProducts")
    public boolean autoInspectProducts() {
 	   return inspectionService.autoInspectProducts();
    }
	
}
