package com.siva.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.siva.entity.BatchEntity;
import com.siva.model.Employee;

@Component
public class JpaEmployeeProcessor implements ItemProcessor<Employee, BatchEntity> {

	@Override
	public BatchEntity process(Employee item) throws Exception {
		System.out.println("JpaEmployeeProcessor.process()");
		BatchEntity entity = null;
		if (item.getSalary() > 50000) {
			System.out.println("satish fy codition");
			entity = new BatchEntity();
			entity.setAddrees(item.getAddress());
			entity.setEmpno(item.getId());
			entity.setName(item.getName());
			entity.setSalary(item.getSalary());
			return entity;
		} else {
			return null;
		}
	}
}
