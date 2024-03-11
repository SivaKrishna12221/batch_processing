package com.nt.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component("listener")
public class CsvJob implements JobExecutionListener {

	private Long startTime, endTime;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		startTime = System.currentTimeMillis();
		System.out.println("Jobs starts from:" + startTime);
	}
	@Override
	public void afterJob(JobExecution jobExecution) {
	  endTime=System.currentTimeMillis();
	  System.out.println("job EndTime:"+endTime);
	  System.out.println("time taken to complete job is"+(endTime-startTime));
	}
}
