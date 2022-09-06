package com.rh.management.model.dto;

import java.util.List;

import com.rh.management.model.Compensation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CompensationDetailsDTO {

	List<Compensation> compensations;
	CompensationDateDTO compensationDate;
	
}
