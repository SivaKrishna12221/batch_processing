package com.nt.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.nt.model.Employee;

@Component("emp")
public class EmployeeCsvProcessor implements ItemProcessor<Employee, Employee> {

	@Override
	public Employee process(Employee emp) throws Exception {

		if (emp.getSalary() >= 90000) {
			emp.setGrossSalary(Math.round(emp.getSalary() + emp.getSalary() * 0.4f));
			emp.setNetSalary(Math.round(emp.getGrossSalary() - emp.getGrossSalary() * 0.2f));
			return emp;
		} else {
			return null;
		}
	}
}
