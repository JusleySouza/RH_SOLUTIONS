package com.rh.management.model.dto;

import java.text.ParseException;
import java.time.LocalDate;

import com.rh.management.model.Employee;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class EmployeeDTO {

	private String firstName;
	private String middleName;
	private String lastName;
	private LocalDate birthDate;
	private String position;
	
	public Employee DTOtoModel() throws ParseException {
		Employee employee = new Employee();
		employee.setFirstName(this.firstName);
		employee.setMiddleName(this.middleName);
		employee.setLastName(this.lastName);
		employee.setBirthDate(this.birthDate);
		employee.setPosition(this.position);
		
		return employee;
		
	}
}

