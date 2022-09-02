package com.rh.management.model.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CompensationSearchDTO {

	@DateTimeFormat(pattern = "yyyy-MM")
	private Date dataInicio;
	@DateTimeFormat(pattern = "yyyy-MM")
	private Date dataFim;
	
}
