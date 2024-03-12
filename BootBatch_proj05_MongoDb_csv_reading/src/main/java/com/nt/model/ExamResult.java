package com.nt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamResult {
	private Integer id;
	private String dob;
	private Double percentage;
	private Integer semestar;
}
