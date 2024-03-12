package com.nt.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class ExamResultJobListener implements JobExecutionListener {

	private Long startTime, endTime;

	@Override
	public void beforeJob(JobExecution jobExecution) {
     startTime=System.currentTimeMillis();
     System.out.println("job executes at:"+startTime);
	}
	@Override
	public void afterJob(JobExecution jobExecution) {
		endTime=System.currentTimeMillis();
		BatchStatus status = jobExecution.getStatus();
		System.out.println("job status:"+status);
		System.out.println("time taken to complete "+(endTime-startTime));
	}
}
