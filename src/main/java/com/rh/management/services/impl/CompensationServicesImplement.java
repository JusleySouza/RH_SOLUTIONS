package com.rh.management.services.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rh.management.model.Compensation;
import com.rh.management.model.Employee;
import com.rh.management.model.dto.CompensationDTO;
import com.rh.management.repository.CompensationRepository;
import com.rh.management.services.CompensationServices;
import com.rh.management.services.EmployeeServices;

@Service
public class CompensationServicesImplement implements CompensationServices {

	boolean exist = false;
	Compensation comp = new Compensation();

	@Autowired
	CompensationRepository repository;
	@Autowired
	EmployeeServices services;
	Employee employee;
	List<Compensation> compensations;

	@Override
	public ResponseEntity<Compensation> create(Long id, CompensationDTO compensation) {
		try {
			comp = compensation.DTOtoModel();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		employee = services.findById(id);
		compensations = repository.findByEmployee(employee);

		comp.setEmployee(employee);

		if (comp.getType().equals("Salario")) {
			compensations.forEach(c -> {
				if (comp.getDate().equals(c.getDate())) {
					exist = true;
				}
			});
			if (!exist) {
				if (employee.getPosition().equals("Administracao")) {
					comp.setAmount(calcularDeducao(comp.getAmount()));
				} else if (employee.getPosition().equals("Caixa")) {
					comp.setAmount(calcularDeducao(comp.getAmount()));
				} else if (employee.getPosition().equals("Estoquista")) {
					comp.setAmount(calcularDeducao(comp.getAmount()));
				} else if (employee.getPosition().equals("Gerente")) {
					comp.setAmount(calcularDeducao(comp.getAmount()));
				} else if (employee.getPosition().equals("Vendedor")) {
					comp.setAmount(calcularDeducao(comp.getAmount()));
				}

				repository.save(comp);
				return new ResponseEntity<Compensation>(HttpStatus.CREATED);
			} else {
				return new ResponseEntity<Compensation>(HttpStatus.NOT_ACCEPTABLE);
			}
		} else if (!comp.getType().equals("Salario")) {
			if (comp.getAmount() > 0 && comp.getDescription() != null && comp.getDescription() != "") {
				repository.save(comp);
				return new ResponseEntity<Compensation>(HttpStatus.CREATED);
			} else {
				return new ResponseEntity<Compensation>(HttpStatus.BAD_REQUEST);
			}
		}
		return null;

	}

	@Override
	public List<Compensation> display(Long id, Date dataInicio, Date dataFim) {
		employee = services.findById(id);
		
		List<Compensation> compensations = repository.findByEmployeeAndDateBetween(employee, dataInicio, dataFim);
		
		return compensations;
	}

	private double calcularDeducao(double compensacao) {

		if (compensacao <= 1212) {
			compensacao = compensacao - (compensacao * 0.075);
		}
		if (compensacao > 1212 && compensacao <= 2427.35) {
			compensacao = compensacao - (compensacao * 0.09);
		}
		if (compensacao > 2427.35 && compensacao <= 3641.03) {
			compensacao = compensacao - (compensacao * 0.12);
		}
		if (compensacao > 3641.03 && compensacao <= 7087.22) {
			compensacao = compensacao - (compensacao * 0.14);
		}

		return compensacao;
	}

}
