package com.siva.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.siva.model.Student;

@Component
public class StudentDataProcessing implements ItemProcessor<Student, Student> {

	@Override
	public Student process(Student item) throws Exception {

		if (item.getName().startsWith("S")) {
			System.out.println("StudentDataProcessing.process()");
			return item;
		} else {
			return null;
		}
	}
}
