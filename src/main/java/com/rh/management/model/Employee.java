package com.rh.management.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id", updatable=false, nullable=false)
	private Long id;
	
	@Column(name="firstName", nullable= false)
	private String firstName;
	
	@Column(name="middleName", nullable= true)
	private String middleName;
	
	@Column(name="lastName", nullable= false)
	private String lastName;
	
	@Column(name="birthDate", nullable= false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;
	
	@Column(name="position", nullable= false)
	private String position;
	
	@OneToMany(mappedBy = "compensation")
	private Compensation compensation;
	
}
