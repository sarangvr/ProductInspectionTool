package com.ensat.model;

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
public class ReportsDto {
	private int low;
	private int medium;
	private int high;

}
