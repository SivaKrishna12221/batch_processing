package com.nt.runner;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CsvEmployeeDetailsTestRunner implements CommandLineRunner {

	@Autowired
	private JobLauncher launcher;
	@Autowired
	private Job job;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("CsvEmployeeDetailsTestRunner.run()");
		JobParameters time = new JobParametersBuilder().addLong("systime", System.currentTimeMillis())
				.toJobParameters();
		launcher.run(job, time);
	}
}
