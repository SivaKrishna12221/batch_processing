package com.siva.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

	private Integer studentNo;
	private String name;
	private String address;
	private Double fees;
	private Double discount;
	private Double totalfees;
}
