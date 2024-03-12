package com.siva.batchConfig;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.siva.customs.AddCustomItemProcessor;
import com.siva.customs.StringCustomsItemReader;
import com.siva.customs.StringItemWriter;
import com.siva.listeners.JobEventistener;

@Configuration
@EnableBatchProcessing
public class StringBatchConfig {

	@Autowired
	private StepBuilderFactory stepFactory;

	@Autowired
	private JobBuilderFactory jobFactory;
	@Autowired
	private JobEventistener listener;

	@Autowired
	private AddCustomItemProcessor processor;

	@Autowired
	private StringCustomsItemReader reader;
	@Autowired
	private StringItemWriter writer;

	/*@Bean("step1")
	public Step createStep(JobRepository repository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("step1", repository).<String, String>chunk(2, transactionManager)
				.allowStartIfComplete(true).reader(reader).processor(processor).writer(writer).build();
	}
	
	@Bean("job")
	public Job createJob(JobRepository repository, PlatformTransactionManager transactionManager) {
		return new JobBuilder("job", repository).listener(listener).flow(createStep(repository, transactionManager))
				.end().build();
	}*/
	@Bean("step1")
	public Step createStep() {

		return stepFactory.get("step1").<String, String>chunk(2).reader(reader).processor(processor).writer(writer)
				.build();
	}

	@Bean("job")
	public Job createJob() {
		return jobFactory.get("job").incrementer(new RunIdIncrementer()).listener(listener).start(createStep()).build();
	}

}
