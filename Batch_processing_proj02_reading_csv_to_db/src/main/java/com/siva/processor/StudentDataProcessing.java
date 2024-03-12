package com.siva.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.siva.model.Student;

@Component("processor")
public class StudentDataProcessing implements ItemProcessor<Student, Student> {

	@Override
	public Student process(Student item) throws Exception {

		if (item.getFees() < 20000 && item.getFees() > 10000) {
			item.setDiscount(item.getFees() * 0.1);
			item.setTotalfees(item.getFees() - item.getDiscount());
			return item;
		} else {
			return null;
		}

	}
}
