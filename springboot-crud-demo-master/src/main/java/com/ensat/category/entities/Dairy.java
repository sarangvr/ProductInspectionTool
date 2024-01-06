package com.ensat.category.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ensat.entities.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Dairy {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	@Column
	private String freshnessDairy;
	@Column
	private String purity;
	@Column
	private String fatContent;
	@Column
	private String homogenization;
	@Column
	private String pasteurization;

}
