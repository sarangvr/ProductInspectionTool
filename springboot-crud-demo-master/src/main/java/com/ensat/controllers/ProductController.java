package com.ensat.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ensat.category.entities.CategoryDtlsDto;
import com.ensat.category.entities.CategoryDto;
import com.ensat.config.CustomObjectMapper;
import com.ensat.entities.Product;
import com.ensat.model.ProductDetails;
import com.ensat.model.QualityInspectionDetailsDto;
import com.ensat.services.ProductService;
import com.ensat.utility.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Product controller.
 */
@RestController("/")
//@Controller
public class ProductController implements Constants{
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	@Autowired
	private ProductService productService;

	@DeleteMapping("/delete/{id}")
	public boolean delete(@PathVariable Long id) {
		return productService.deleteProduct(id);
	}

	@PostMapping("/register")
	public String addEditProduct(@RequestBody String json, Model model) {
		System.out.println("createProductNew : " + json);
		try {
			ObjectMapper objectMapper = CustomObjectMapper.getObjectMapper();
			ProductDetails productDetails = objectMapper.readValue(json, ProductDetails.class);
			if (productDetails.getMode().equals("add")) {
				Product product = productService.saveProduct(productDetails);
				System.out.println("register ProductDetails: " + productDetails.toString());
			} else if (productDetails.getMode().equals("edit")) {
				ProductDetails product = productService.editProductById(productDetails);
				System.out.println("register ProductDetails: " + productDetails.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error createProductNew: " + e.getMessage());
			logger.error(ERROR_MESSAGE, e);
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
			logger.error(ERROR_MESSAGE, e);
			return new ResponseEntity<CategoryDtlsDto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CategoryDtlsDto>(categoryDtlsDto, HttpStatus.OK);

	}
	
	@PostMapping("/getQualityParam")
	public ResponseEntity<QualityInspectionDetailsDto> getQualityParam(@RequestBody String json, Model model) {
		QualityInspectionDetailsDto qualityInspectionDetailsDto = new QualityInspectionDetailsDto();
		try {
			ObjectMapper objectMapper = CustomObjectMapper.getObjectMapper();
			QualityInspectionDetailsDto qualityDto = objectMapper.readValue(json, QualityInspectionDetailsDto.class);
			qualityInspectionDetailsDto = productService.getQualityParameters(qualityDto);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error getCategoryDtls: " + e.getMessage());
			logger.error(ERROR_MESSAGE, e);
			return new ResponseEntity<QualityInspectionDetailsDto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<QualityInspectionDetailsDto>(qualityInspectionDetailsDto, HttpStatus.OK);

	}

}
