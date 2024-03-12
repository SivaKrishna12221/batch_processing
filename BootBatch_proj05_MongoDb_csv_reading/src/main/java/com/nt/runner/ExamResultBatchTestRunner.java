package com.nt.runner;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ExamResultBatchTestRunner implements CommandLineRunner {

	@Autowired
	private Job job;
	@Autowired
	private JobLauncher launcher;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("ExamResultBatchTestRunner.run()");
		JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
				.toJobParameters();
		System.out.println("ExamResultBatchTestRunner.run()");
		launcher.run(job, jobParameters);
	}

}
