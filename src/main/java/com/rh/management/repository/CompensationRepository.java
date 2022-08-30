package com.rh.management.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rh.management.model.Compensation;
import com.rh.management.model.Employee;

public interface CompensationRepository extends JpaRepository<Compensation, Long> {

	List<Compensation> findByEmployee(Employee employee);
	List<Compensation> findByEmployeeAndDateBetweenOrderByDateDesc(Employee employee, Date dataInicio, Date dataFim);
}
