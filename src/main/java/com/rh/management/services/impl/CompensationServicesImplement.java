package com.rh.management.services.impl;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rh.management.model.Compensation;
import com.rh.management.model.Employee;
import com.rh.management.model.dto.CompensationDTO;
import com.rh.management.model.dto.CompensationDateDTO;
import com.rh.management.model.dto.CompensationDetailsDTO;
import com.rh.management.model.dto.CompensationSearchDTO;
import com.rh.management.repository.CompensationRepository;
import com.rh.management.services.CompensationServices;
import com.rh.management.services.EmployeeServices;

@Service
public class CompensationServicesImplement implements CompensationServices {

	boolean exist = false;
	Compensation comp = new Compensation();
	List<Date> datas;

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
	public List<CompensationDateDTO> display(Long id, CompensationSearchDTO compensationSearch) {
		employee = services.findById(id);

		List<Compensation> compensations = repository.findByEmployeeAndDateBetweenOrderByDateDesc(employee,
				compensationSearch.getDataInicio(), compensationSearch.getDataFim());

		List<CompensationDateDTO> listCompensationDate = new ArrayList<>();

		datas = new ArrayList<>();
		compensations.forEach(c -> {
			if (!datas.contains(c.getDate())) {
				datas.add(c.getDate());
			}
		});

		for (Date data : datas) {
			CompensationDateDTO compensationDate = new CompensationDateDTO();
			ZoneId timeZone = ZoneId.systemDefault();
			LocalDate getLocalDate = data.toInstant().atZone(timeZone).toLocalDate();

			compensationDate.setMes(getLocalDate.getMonth().getValue() < 10 ? "0" + getLocalDate.getMonth().getValue()
					: "" + getLocalDate.getMonth().getValue());

			compensationDate.setAno("" + getLocalDate.getYear());
			compensationDate.setDate(data);
			for (Compensation compensation : compensations) {
				if (data.equals(compensation.getDate())) {
					compensationDate.setTotal(compensationDate.getTotal() + compensation.getAmount());
				}
			}
			listCompensationDate.add(compensationDate);
		}

		return listCompensationDate;
	}

	@Override
	public CompensationDetailsDTO details(Long id, Date date) {
		employee = services.findById(id);
		List<Compensation> compensations = repository.findByEmployeeAndDateOrderByTypeDesc(employee, date);
		
		CompensationDetailsDTO compensationDetails = new CompensationDetailsDTO();
		compensationDetails.setCompensations(compensations);
		
		CompensationDateDTO compensationDate = new CompensationDateDTO();
		ZoneId timeZone = ZoneId.systemDefault();
		LocalDate getLocalDate = date.toInstant().atZone(timeZone).toLocalDate();

		compensationDate.setMes(getLocalDate.getMonth().getValue() < 10 ? "0" + getLocalDate.getMonth().getValue()
				: "" + getLocalDate.getMonth().getValue());

		compensationDate.setAno("" + getLocalDate.getYear());
		
		for (Compensation compensation : compensations) {
				compensationDate.setTotal(compensationDate.getTotal() + compensation.getAmount());
		}
		
		compensationDetails.setCompensationDate(compensationDate);
		
		
		return compensationDetails;
	}
	
	@Override
	public Compensation edit(Long id) {
		comp = repository.findById(id).orElse(new Compensation());
		return comp;
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
