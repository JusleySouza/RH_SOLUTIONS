package com.rh.management.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rh.management.model.Employee;
import com.rh.management.model.dto.EmployeeDTO;
import com.rh.management.model.dto.EmployeeSearchDTO;

@Service
public interface EmployeeServices {


	ResponseEntity<Employee> create(EmployeeDTO employee);
	
	List<Employee> search(EmployeeSearchDTO employeeSearch);
	
	Employee edit(Long id);
	
	ResponseEntity<Employee> update(Employee employee);
		
	Employee findById(Long id);
	
}
