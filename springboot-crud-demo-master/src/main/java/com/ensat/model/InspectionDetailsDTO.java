package com.ensat.model;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
    private String comments;
    private String result;
	public InspectionDetailsDTO(long productId, String productName, LocalDate date, String inspector, String comments,
			String result) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.date = date;
		this.inspector = inspector;
		this.comments = comments;
		this.result = result;
	}
    
    
}
