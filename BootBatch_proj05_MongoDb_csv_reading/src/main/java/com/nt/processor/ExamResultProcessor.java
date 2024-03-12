package com.nt.processor;

import java.sql.Date;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.nt.document.OExamResult;
import com.nt.model.ExamResult;

@Component
public class ExamResultProcessor implements ItemProcessor<ExamResult, OExamResult> {

	@Override
	public OExamResult process(ExamResult item) throws Exception {
		System.out.println("ExamResultProcessor.process()");
		OExamResult processResult = null;
		if (item.getPercentage() > 70) {
			System.out.println("ExamResultProcessor.process() condition satishfy");
			processResult = new OExamResult();
			/*processResult.setId(Integer.valueOf(item.getId()));
			processResult.setPercentage(Double.valueOf(item.getPercentage()));
			processResult.setDob(Date.valueOf(item.getDob()));
			processResult.setSemestar(Integer.valueOf(item.getSemestar()));
			*/
			processResult.setDob(Date.valueOf(item.getDob()));
			processResult.setId(item.getId());
			processResult.setPercentage(item.getPercentage());
			processResult.setSemestar(item.getSemestar());
			return processResult;
		} else {
			return null;
		}

	}

}
