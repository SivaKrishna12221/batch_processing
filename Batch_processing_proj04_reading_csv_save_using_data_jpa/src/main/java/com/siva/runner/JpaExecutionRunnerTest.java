package com.siva.runner;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JpaExecutionRunnerTest implements CommandLineRunner {

	@Autowired
	private JobLauncher laucher;
	@Autowired
	private Job job;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		JobParameters parameter = new JobParametersBuilder().addDate("date", new Date()).toJobParameters();
		laucher.run(job, parameter);
	}

}
