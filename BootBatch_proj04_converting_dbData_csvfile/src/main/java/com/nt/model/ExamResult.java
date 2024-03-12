package com.nt.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExamResult {

	private int id;
	private Date dob;
	private double percentage;
	private int semestar;
}
