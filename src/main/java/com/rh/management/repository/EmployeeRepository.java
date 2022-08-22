package com.rh.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rh.management.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	public List<Employee> findByFirstNameOrLastNameOrPosition( String firstName, String lastName, String position);
	public List<Employee> findByFirstNameAndLastName( String firstName, String lastName);
	public List<Employee> findByFirstNameAndPosition( String firstName, String position);
	public List<Employee> findByLastNameAndPosition( String lastName, String position);
	public List<Employee> findByFirstNameAndLastNameAndPosition( String firstName, String lastName, String position);
	
}
