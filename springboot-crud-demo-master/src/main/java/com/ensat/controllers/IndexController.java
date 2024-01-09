package com.ensat.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import com.ensat.model.ProductDetailsDTO;
import com.ensat.model.ReportsDto;
import com.ensat.category.entities.CategoryDtlsDto;
import com.ensat.category.entities.CategoryDto;
import com.ensat.config.CustomObjectMapper;
import com.ensat.model.InspectionDetailsDTO;
import com.ensat.services.InspectionService;
import com.ensat.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Homepage controller.
 */
@CrossOrigin
@Controller
public class IndexController {
	@Autowired
	private ProductService productService;

	@Autowired
	private InspectionService inspectionService;

	@GetMapping("/")
	public String showLoginForm() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}
		System.out.println("User is already authenticated, redirecting to /products");
		return "redirect:/home";
	}

	@GetMapping("/home")
	public String showHomePage() {
		return "homeSideBar";
	}

	@GetMapping("/getProducts")
	public String getProducts(Model model) {
		try {
			List<ProductDetailsDTO> productList = productService.listAllProducts();
			model.addAttribute("products", productList);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error getProducts: " + e.getMessage());
		}
		return "productsSideBar";

	}

	@GetMapping("/getProductInspection")
	public String getInspectionDetails(Model model) {
		try {
			List<InspectionDetailsDTO> inspectionList = inspectionService.listAllInspection();
			model.addAttribute("inspections", inspectionList);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error getProducts: " + e.getMessage());
		}
		return "inspectionSideBar";
	}
	
	@GetMapping("/doAutoInspect")
	public String autoInspectProducts(Model model) {
	    try {
	        boolean flag = inspectionService.autoInspectProducts();

	        if (flag) {
	            // If auto inspection is successful, redirect to "/getProductInspection"
	            return "redirect:/getProductInspection";
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Error during autoInspectProducts: " + e.getMessage());
	    }

	    // If there's an error or auto inspection fails, return to "inspectionSideBar"
	    return "inspectionSideBar";
	}
	
	@GetMapping("/inspection")
	public String showInspectionPage() {
		return "inspection"; // inspection.html
	}

	@GetMapping("/reports")
	public String showReportsPage() {
		return "reportsSideBar"; // reports.html
	}
	@GetMapping("/getReports")
	public ResponseEntity<ReportsDto> showReportsDeatails(Model model) {
		ReportsDto reportsDto = new ReportsDto();
		try {
			reportsDto = inspectionService.getReportsDetails();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error getCategoryDtls: " + e.getMessage());
			return new ResponseEntity<ReportsDto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ReportsDto>(reportsDto, HttpStatus.OK);
	}
}