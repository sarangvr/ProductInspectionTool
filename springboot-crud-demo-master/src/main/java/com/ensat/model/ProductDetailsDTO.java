package com.ensat.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ensat.entities.Category;
import com.ensat.entities.Quality;
import com.ensat.entities.Result;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Component
@Service
public class ProductDetailsDTO {
	
//	private long id;
	private long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
    private LocalDate manufacturingDate;
    private LocalDate expiryDate;
    private String category;
    private double weight;
    private String colour;
	public ProductDetailsDTO(long productId, String name, String description, BigDecimal price, int quantity,
			LocalDate manufacturingDate, LocalDate expiryDate, String category, double weight, String colour) {
		super();
		this.productId = productId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.manufacturingDate = manufacturingDate;
		this.expiryDate = expiryDate;
		this.category = category;
		this.weight = weight;
		this.colour = colour;
	}
	
	
}
