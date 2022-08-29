package com.rh.management.model.dto;

import com.rh.management.model.Employee;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class EmployeeFullNameDTO {

	private Long id;
	private String fullName;
	private String position;
	
	public EmployeeFullNameDTO(Employee emp) {
		this.fullName =  emp.getFirstName() + " " + emp.getMiddleName() + " " + emp.getLastName();
		this.id = emp.getId();
		this.position = emp.getPosition();
	}
	
	
}
