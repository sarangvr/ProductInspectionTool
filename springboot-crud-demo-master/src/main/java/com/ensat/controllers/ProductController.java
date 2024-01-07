package com.ensat.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ensat.category.entities.CategoryDtlsDto;
import com.ensat.category.entities.CategoryDto;
import com.ensat.config.CustomObjectMapper;
import com.ensat.entities.Product;
import com.ensat.model.ProductDetails;
import com.ensat.model.ProductDetailsDTO;
import com.ensat.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;




/**
 * Product controller.
 */
@RestController("/")
//@Controller
public class ProductController {
     @Autowired
     private ProductService productService;

		/*
		 * @PostMapping("/createProduct") public ResponseEntity<Product>
		 * createProduct(@RequestBody ProductDetails productDetails) { Product product =
		 * productService.saveProduct(productDetails); return
		 * ResponseEntity.ok(product); }
		 */

	/*
	 * @PutMapping("/edit") public ProductDetails edit(@RequestBody ProductDetails
	 * productDetails) { productService.editProductById(productDetails); return
	 * productDetails; }
	 */
    
	/*
	 * @GetMapping("/listAllProducts") public
	 * ResponseEntity<List<ProductDetailsDTO>> listAllProducts() {
	 * List<ProductDetailsDTO> productDetailsList =
	 * productService.listAllProducts(); return
	 * ResponseEntity.ok(productDetailsList); }
	 */

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
    
	/*
	 * @GetMapping("/product") public ProductDetails getProductDetails(@RequestParam
	 * Long id) { return productService.getProductById(id); }
	 */
    
    @PostMapping("/register")
    public String addEditProduct(@RequestBody String json, Model model) {
        System.out.println("createProductNew : " + json);
        try {
        	ObjectMapper objectMapper = CustomObjectMapper.getObjectMapper();
            ProductDetails productDetails = objectMapper.readValue(json, ProductDetails.class);
            if(productDetails.getMode().equals("add")) {
            	Product product = productService.saveProduct(productDetails);
            	System.out.println("register ProductDetails: " + productDetails.toString());
            }else if(productDetails.getMode().equals("edit")) {
            	 ProductDetails product = productService.editProductById(productDetails);
            	 System.out.println("register ProductDetails: " + productDetails.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error createProductNew: " + e.getMessage());
        }

        return "redirect:/products";
    }
    
	@PostMapping("/getCategoryDtls")
	public ResponseEntity<CategoryDtlsDto> getCategoryDtls(@RequestBody String json, Model model) {
    	CategoryDtlsDto categoryDtlsDto = new CategoryDtlsDto();
		try {
			ObjectMapper objectMapper = CustomObjectMapper.getObjectMapper();
			CategoryDto categoryDto = objectMapper.readValue(json, CategoryDto.class);
			categoryDtlsDto = productService.getAllCategoryDetails(categoryDto);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error getCategoryDtls: " + e.getMessage());
			return new ResponseEntity<CategoryDtlsDto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CategoryDtlsDto>(categoryDtlsDto, HttpStatus.OK);

	}

}
