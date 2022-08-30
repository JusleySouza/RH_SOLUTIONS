package com.rh.management.model.dto;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CompensationSearchDTO {

	private Date dataInicio;
	private Date dataFim;
	
}
