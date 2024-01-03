package com.ensat.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
//import ch.qos.logback.core.model.Model;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import com.ensat.model.ProductDetailsDTO;
import com.ensat.services.ProductService;


/**
 * Homepage controller.
 */
@CrossOrigin
@Controller
public class IndexController {
	@Autowired
    private ProductService productService;

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
		return "homeSideBar"; // This should match the name of your homeSideBar.html file
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
    public String getInspectionDetails() {
    	return "inspectionSideBar";
    }
    
    @GetMapping("/inspection")
	public String showInspectionPage() {
		return "inspection"; // This should match the name of your inspection.html file
	}
    
    @GetMapping("/reports")
	public String showReportsPage() {
		return "reports"; // This should match the name of your reports.html file
	}

}