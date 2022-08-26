package com.rh.management.model.dto;

import java.text.ParseException;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.rh.management.model.Compensation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CompensationDTO {

	private String type;
	private double amount;
	private String description;
	@DateTimeFormat(pattern = "yyyy-MM")
	private Date date;

	public Compensation DTOtoModel() throws ParseException {
		Compensation compensation = new Compensation();
		compensation.setType(this.type);
		compensation.setAmount(this.amount);
		compensation.setDescription(this.description);
		compensation.setDate(this.date);
		return compensation;
	}

}
