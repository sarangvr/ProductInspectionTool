package com.ensat.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import com.ensat.entities.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class QualityMetric {
	
		@Id
    	@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private long id;
	    @Column
	    private String colour;
		 @Enumerated(EnumType.STRING)
		 private Quality quality;
	    @Column
        private double weight;
	    @Column
	    private LocalDate manufacturingDate;
	    @Column
	    private LocalDate expiryDate;
	    @ManyToOne
	    @JoinColumn(name = "product_id") 
	    private Product product;
		
}
