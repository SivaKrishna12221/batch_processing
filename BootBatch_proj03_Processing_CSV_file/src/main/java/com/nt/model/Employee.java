package com.nt.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {

	private Integer empno;
	private String name;
	private Integer salary;
	private String address;
	private Integer grossSalary;
	private Integer netSalary;

	public Employee() {

	}
}
