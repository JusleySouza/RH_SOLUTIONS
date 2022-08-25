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

	private String fullName;
	
	public void getFullName(Employee emp) {
		this.fullName =  emp.getFirstName() + " " + emp.getMiddleName() + " " + emp.getLastName();
	}
	
}
