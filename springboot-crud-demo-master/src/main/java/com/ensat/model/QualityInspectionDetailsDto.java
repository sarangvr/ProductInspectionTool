package com.ensat.model;

import org.springframework.stereotype.Component;

import com.ensat.entities.Quality;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Component
public class QualityInspectionDetailsDto {
	private long productId;
	private String category;
	private String inspectorName;
	private String comments;
	private String result;
	private Quality quality;
}
