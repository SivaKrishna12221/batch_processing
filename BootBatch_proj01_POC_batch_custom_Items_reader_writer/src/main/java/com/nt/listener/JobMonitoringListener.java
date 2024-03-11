package com.nt.listener;

import java.time.LocalDate;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component("jmsListener")
public class JobMonitoringListener implements JobExecutionListener {

	private Long startTime, endTime;

	public JobMonitoringListener() {
		// TODO Auto-generated constructor stub

		System.out.println("JobMonitoringListener.no -arg constructor");

	}

	@Override
	public void beforeJob(JobExecution jobExecution) {

		System.out.println("job is beging at " + LocalDate.now());
		startTime = System.currentTimeMillis();
		System.out.println("job status " + jobExecution.getStatus());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {

		System.out.println(" job end  at" + LocalDate.now());
		endTime = System.currentTimeMillis();
		System.out.println("Time taken for the job is:" + (startTime - endTime));
		System.out.println("job status" + jobExecution.getStatus());
		System.out.println("job exist status:" + jobExecution.getExitStatus());
	}
}
