package com.rh.management.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class EmployeeSearchDTO {

	private String firstName;
	private String lastName;
	private String position;
}
