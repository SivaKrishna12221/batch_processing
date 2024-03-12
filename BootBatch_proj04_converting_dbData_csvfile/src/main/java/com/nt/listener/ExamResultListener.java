package com.nt.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class ExamResultListener implements JobExecutionListener {
	private Long startTime, endTime;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		startTime = System.currentTimeMillis();
		System.out.println("job starts at:" + startTime);
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		endTime = System.currentTimeMillis();
		System.out.println("job ends time:" + endTime);
		System.out.println("Time taken to complete job is;" + (endTime - startTime));
	}

}
