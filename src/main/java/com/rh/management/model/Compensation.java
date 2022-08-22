package com.rh.management.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

public class Compensation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id", updatable=false, nullable=false)
	private Long id;
	
	@Column(name="type", nullable= false)
	private String type;
	
	@Column(name="amount", nullable= false)
	private double amount;
	
	@Column(name="description", nullable= false)
	private String description;
	
	@Column(name="date", nullable= false)
	@DateTimeFormat(pattern = "yyyy-MM")
	private Date date;
	
}
