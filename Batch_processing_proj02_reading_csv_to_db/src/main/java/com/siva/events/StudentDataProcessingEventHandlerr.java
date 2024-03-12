package com.siva.events;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component("events")
public class StudentDataProcessingEventHandlerr implements JobExecutionListener {

	private Long startTime,endTime;
	@Override
	public void afterJob(JobExecution jobExecution) {

		startTime=System.currentTimeMillis();
		System.out.println("job starts at:"+startTime);
		System.out.println(jobExecution.getStatus());
	}
	@Override
	public void beforeJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		endTime=System.currentTimeMillis();
		System.out.println("job ends at:"+endTime);
		System.out.println(jobExecution.getExitStatus());
	}
}
