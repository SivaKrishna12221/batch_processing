package com.nt.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nt.items.CustomItemProcessor;
import com.nt.items.CustomItemReader;
import com.nt.items.CustomItemWriter;
import com.nt.listener.JobMonitoringListener;

@Configuration
@EnableBatchProcessing
@SuppressWarnings("all")
public class BatchConfig {

	@Autowired
	private StepBuilderFactory stepFactory;

	@Autowired
	private CustomItemReader itemReader;

	@Autowired
	private CustomItemWriter itemWriter;

	@Autowired
	private CustomItemProcessor itemProcessor;

	@Autowired
	private JobBuilderFactory jobFactory;
	@Autowired
	private JobMonitoringListener listener;

	@Bean("step1")
	public Step createStep1() {
		return stepFactory.get("step1").<String, String>chunk(1).reader(itemReader).processor(itemProcessor)
				.writer(itemWriter).build();

	}

	@Bean("job1")
	public Job createJob1() {
		return jobFactory.get("job").incrementer(new RunIdIncrementer()).listener(listener).start(createStep1())
				.build();
	}

}
