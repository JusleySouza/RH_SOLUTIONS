package com.rh.management.services;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rh.management.model.Compensation;
import com.rh.management.model.dto.CompensationDTO;
import com.rh.management.model.dto.CompensationDateDTO;
import com.rh.management.model.dto.CompensationDetailsDTO;
import com.rh.management.model.dto.CompensationSearchDTO;

@Service
public interface CompensationServices {

	ResponseEntity<Compensation> create( Long id, CompensationDTO compensation);
	
	List<CompensationDateDTO> display( Long id, CompensationSearchDTO compensation);
	
	CompensationDetailsDTO details(Long id, Date date);
	
	Compensation edit(Long id);

}
