package com.rh.management.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CompensationDateDTO {

	private double total;
	private String mes;
	private String ano;
	
}
