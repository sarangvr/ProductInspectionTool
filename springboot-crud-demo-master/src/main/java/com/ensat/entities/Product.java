package com.ensat.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Product entity.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
    private long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private BigDecimal price;
    @Column
    private int quantity;
    //@Enumerated(EnumType.STRING)
	 private String category;

	}

