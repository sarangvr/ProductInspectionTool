package com.ensat.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Product entity.
 */
@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
public class Product {
	
	@Column(name = "id")
	private long id;
	@Id
	@Column(name = "product_id")
	private long productId;
	@Column(name = "name")
	private String name;
	@Column(name = "description")
	private String description;
	@Column(name = "price")
	private BigDecimal price;
	@Column(name = "quantity")
	private int quantity;
	@Column(name = "category")
	private String category;

}

