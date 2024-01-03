package com.ensat.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ensat.entities.Category;
import com.ensat.entities.Quality;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Component
public class ProductDetails {

	private long id;
	private String name;
	private String description;
	private BigDecimal price;
	private int quantity;
	private LocalDate manufacturingDate;
	private LocalDate expiryDate;
	private String category;
	private double weight;
	private String colour;
	private Quality quality;
	private String mode;

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
	@Override
	public String toString() {
		return "ProductDetails [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", quantity=" + quantity + ", manufacturingDate=" + manufacturingDate + ", expiryDate=" + expiryDate
				+ ", category=" + category + ", weight=" + weight + ", colour=" + colour + ", quality=" + quality
				+ ", mode=" + mode + ", organic=" + organic + ", nonGmo=" + nonGmo + ", wholeGrain=" + wholeGrain
				+ ", freshnessGrocery=" + freshnessGrocery + ", nutrientContent=" + nutrientContent
				+ ", freshnessDairy=" + freshnessDairy + ", purity=" + purity + ", fatContent=" + fatContent
				+ ", homogenization=" + homogenization + ", pasteurization=" + pasteurization + ", freshnessBakery="
				+ freshnessBakery + ", texture=" + texture + ", moistureContent=" + moistureContent + ", flavorBakery="
				+ flavorBakery + ", uniSizeShape=" + uniSizeShape + ", NaturalIngredients=" + naturalIngredients
				+ ", flavorBeverages=" + flavorBeverages + ", colorBeverages=" + colorBeverages + ", clarity=" + clarity
				+ ", shelfLife=" + shelfLife + ", freshnessMeat=" + freshnessMeat + ", marbling=" + marbling
				+ ", colorMeat=" + colorMeat + ", odor=" + odor + ", temperatureControl=" + temperatureControl + "]";
	}

}
