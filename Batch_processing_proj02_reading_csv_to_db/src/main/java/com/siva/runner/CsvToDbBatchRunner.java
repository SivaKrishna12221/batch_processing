package com.siva.runner;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CsvToDbBatchRunner implements CommandLineRunner {

	@Autowired
	private JobLauncher launcher;

	@Autowired
	private Job job;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		JobParameters params = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
		launcher.run(job, params);
	}
}
