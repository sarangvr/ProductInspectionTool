package com.ensat.model;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ensat.entities.Quality;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Component
@Service
public class InspectionDetailsDTO {
	private long productId;
    private String productName;
    private LocalDate date;
    private String inspector;
    private LocalDate manufacturingDate;
    private LocalDate expiryDate;
    private String category;
    private String comments;
    private Quality quality;
    private String result;
    
	public InspectionDetailsDTO(long productId, String productName, LocalDate date, String inspector,
			LocalDate manufacturingDate, LocalDate expiryDate, String category, String comments, Quality quality,
			String result) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.date = date;
		this.inspector = inspector;
		this.manufacturingDate = manufacturingDate;
		this.expiryDate = expiryDate;
		this.category = category;
		this.comments = comments;
		this.quality = quality;
		this.result = result;
	}
}
