package com.nt.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.nt.listener.CsvJob;
import com.nt.model.Employee;
import com.nt.processor.EmployeeCsvProcessor;

//@Configuration
//@EnableBatchProcessing
public class BatchConfigSimplestForm {

	@Autowired
	private DataSource ds;

	@Autowired
	private StepBuilderFactory stepFactory;

	@Autowired
	private JobBuilderFactory jobFactory;

	@Autowired
	private EmployeeCsvProcessor processor;

	@Autowired
	private CsvJob listener;

	@Bean("itemReader")
	public ItemReader<Employee> createItemReader() {

		return new FlatFileItemReaderBuilder<Employee>().name("file-reader").delimited().delimiter(",")
				.names("empno", "name", "salary", "address").targetType(Employee.class).build();
	}

	@Bean("itemWriter")
	public ItemWriter<Employee> createItemWriter() {
		return new JdbcBatchItemWriterBuilder<Employee>().dataSource(ds).sql(
				"INSERT INTO CSV_EMPLOYEE (EMPNO,NAME,SALARY,ADDRESS,GROSSSALARY,NETSALARY) VALUES(:empno,:name,:salary,:address,:grossSalary,:netSalary)")
				.beanMapped().build();

	}

	@Bean("step1")
	public Step createStep() {
		return stepFactory.get("step1").<Employee, Employee>chunk(3).reader(createItemReader()).processor(processor)
				.writer(createItemWriter()).build();
	}

	@Bean("job1")
	public Job creatJob() {
		return jobFactory.get("job1").incrementer(new RunIdIncrementer()).listener(listener).start(createStep())
				.build();

	}
}
