package com.rh.management.services;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rh.management.model.Compensation;
import com.rh.management.model.dto.CompensationDTO;

@Service
public interface CompensationServices {

	ResponseEntity<Compensation> create( Long id, CompensationDTO compensation);
	
	List<Compensation> display( Long id, Date dataInicio, Date dataFim);

}
