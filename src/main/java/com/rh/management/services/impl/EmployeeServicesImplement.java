package com.rh.management.services.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rh.management.model.Employee;
import com.rh.management.model.dto.EmployeeDTO;
import com.rh.management.model.dto.EmployeeSearchDTO;
import com.rh.management.repository.EmployeeRepository;
import com.rh.management.services.EmployeeServices;

@Service
public class EmployeeServicesImplement implements EmployeeServices {
	boolean exist = false;
	Employee emp = new Employee();

	@Autowired
	EmployeeRepository repository;

	@Override
	public ResponseEntity<Employee> create(EmployeeDTO employee) {

		try {
			emp = employee.DTOtoModel();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		List<Employee> employees = repository.findAll();
		employees.forEach(e -> {
			if (emp.getFirstName().equalsIgnoreCase(e.getFirstName())
					&& emp.getMiddleName().equalsIgnoreCase(e.getMiddleName())
					&& emp.getLastName().equalsIgnoreCase(e.getLastName())
					&& emp.getBirthDate().equals(e.getBirthDate())) {
				exist = true;
				System.out.println("Exist = " + exist);
			}
		});

		if (!exist) {
			repository.save(emp);
			return new ResponseEntity<Employee>(HttpStatus.CREATED);
		} else if (exist) {
			return new ResponseEntity<Employee>(HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<Employee>(HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public List<Employee> search(EmployeeSearchDTO employeeSearch) {
		List<Employee> employees = new ArrayList<>();
		if((employeeSearch.getFirstName()!="" && employeeSearch.getLastName()=="" && employeeSearch.getPosition()=="")||
				(employeeSearch.getFirstName()=="" && employeeSearch.getLastName()!=null && employeeSearch.getPosition()=="") ||
				(employeeSearch.getFirstName()=="" && employeeSearch.getLastName()=="" && employeeSearch.getPosition()!="")) {
			 employees = repository.findByFirstNameOrLastNameOrPosition(employeeSearch.getFirstName(), employeeSearch.getLastName(), employeeSearch.getPosition());	
		}
		else if((employeeSearch.getFirstName()!="" && employeeSearch.getLastName()!=null && employeeSearch.getPosition()=="")) {
			 employees = repository.findByFirstNameAndLastName(employeeSearch.getFirstName(), employeeSearch.getLastName());	
		}
		else if((employeeSearch.getFirstName()!="" && employeeSearch.getLastName()=="" && employeeSearch.getPosition()!="")) {
			 employees = repository.findByFirstNameAndPosition(employeeSearch.getFirstName(), employeeSearch.getPosition());	
		}
		else if((employeeSearch.getFirstName()=="" && employeeSearch.getLastName()!=null && employeeSearch.getPosition()!="")) {
			 employees = repository.findByLastNameAndPosition(employeeSearch.getLastName(), employeeSearch.getPosition());	
		}
		else if((employeeSearch.getFirstName()!="" && employeeSearch.getLastName()!=null && employeeSearch.getPosition()!="")) {
			 employees = repository.findByFirstNameAndLastNameAndPosition(employeeSearch.getFirstName(),employeeSearch.getLastName(), employeeSearch.getPosition());	
		}
		
		return employees;
	}

	@Override
	public Employee edit(Long id) {
		Employee employee = repository.findById(id).orElse(new Employee());
		return employee;
	}

	@Override
	public ResponseEntity<Employee> update(Employee emp) {
		
		List<Employee> employees = repository.findAll();
		employees.forEach(e -> {
			if (emp.getFirstName().equalsIgnoreCase(e.getFirstName())
					&& emp.getMiddleName().equalsIgnoreCase(e.getMiddleName())
					&& emp.getLastName().equalsIgnoreCase(e.getLastName())
					&& emp.getBirthDate().equals(e.getBirthDate())) {
				exist = true;
				System.out.println("Exist = " + exist);
			}
		});

		if (!exist) {
			repository.save(emp);
			return new ResponseEntity<Employee>(HttpStatus.CREATED);
		} else if (exist) {
			return new ResponseEntity<Employee>(HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<Employee>(HttpStatus.BAD_REQUEST);
		}
	
	}


	@Override
	public Employee findById(Long id) {
		emp = repository.findById(id).orElse(new Employee());
		return emp;
	}


}
