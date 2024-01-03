package com.ensat.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.stereotype.Component;

import com.ensat.entities.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Component
public class InspectionDetails {

//	private long id;
//	private LocalDate date;
//	private String inspector;
//	private Result result;
//	private String comments;
//	private long product_id;
    private Long inspectionId;
    private Product product;
    private Date date;
    private String productName;
    private String category;
    private String inspector;
    private String result;
    private String comments;
    
    private String organic;
	private String nonGmo;
	private String wholeGrain;
	private String freshnessGrocery;
	private String nutrientContent;

	private String freshnessDairy;
	private String purity;
	private String fatContent;
	private String homogenization;
	private String pasteurization;

	private String freshnessBakery;
	private String texture;
	private String moistureContent;
	private String flavorBakery;
	private String uniSizeShape;

	private String naturalIngredients;
	private String flavorBeverages;
	private String colorBeverages;
	private String clarity;
	private String shelfLife;

	private String freshnessMeat;
	private String marbling;
	private String colorMeat;
	private String odor;
	private String temperatureControl;

}
