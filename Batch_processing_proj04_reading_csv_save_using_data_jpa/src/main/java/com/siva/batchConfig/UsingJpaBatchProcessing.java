package com.siva.batchConfig;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.siva.entity.BatchEntity;
import com.siva.listener.JpaJobExecution;
import com.siva.model.Employee;
import com.siva.processor.JpaEmployeeProcessor;

@Configuration
@EnableBatchProcessing
public class UsingJpaBatchProcessing {

	@Autowired
	private StepBuilderFactory stepFactory;
	@Autowired
	private JobBuilderFactory jobFactory;

	@Autowired
	private EntityManagerFactory entityManager;

	@Autowired
	private JpaEmployeeProcessor processor;

	@Autowired
	private JpaJobExecution listener;

	public ItemReader<Employee> createItemReader() {

		return new FlatFileItemReaderBuilder<Employee>().name("file-reader").resource(new ClassPathResource("MOCK_DATA.csv")).delimited().delimiter(",")
				.names("id", "name", "salary", "address").targetType(Employee.class).build();
	}

	public ItemWriter<BatchEntity> createItemWriter() {
		return new JpaItemWriterBuilder<BatchEntity>().entityManagerFactory(entityManager).build();
	}

	@Bean("step1")
	public Step createStep() {
		return stepFactory.get("step1").<Employee, BatchEntity>chunk(5).reader(createItemReader()).processor(processor)
				.writer(createItemWriter()).build();
	}

	@Bean("job")
	public Job createJob() {
		return jobFactory.get("job").incrementer(new RunIdIncrementer()).listener(listener).start(createStep()).build();
	}
}
