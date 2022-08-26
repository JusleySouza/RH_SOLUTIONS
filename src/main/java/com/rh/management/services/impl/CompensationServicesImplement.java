package com.rh.management.services.impl;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rh.management.model.Compensation;
import com.rh.management.model.dto.CompensationDTO;
import com.rh.management.repository.CompensationRepository;
import com.rh.management.services.CompensationServices;

@Service
public class CompensationServicesImplement implements CompensationServices {
	
	boolean exist = false;
	Compensation comp = new Compensation();
	
	@Autowired
	CompensationRepository repository;

	@Override
	public ResponseEntity<Compensation> create(CompensationDTO compensation) {
		try {
			comp = compensation.DTOtoModel();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//if()
		
		
		
		
		
		
		
		
		
		repository.save(comp);
		
		return new ResponseEntity<Compensation>(HttpStatus.CREATED);
	}

}
