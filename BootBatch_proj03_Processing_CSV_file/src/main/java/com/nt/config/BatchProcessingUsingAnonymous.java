package com.nt.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import com.nt.listener.CsvJob;
import com.nt.model.Employee;
import com.nt.processor.EmployeeCsvProcessor;

//@Configuration
//@EnableBatchProcessing
public class BatchProcessingUsingAnonymous {

	@Autowired
	private StepBuilderFactory stepFactory;
	@Autowired
	private JobBuilderFactory jobFactory;

	@Autowired
	private DataSource ds;

	@Autowired
	private CsvJob listener;

	@Autowired
	private EmployeeCsvProcessor csvProcessor;

	@Bean("reader1")
	public ItemReader<Employee> createItemReader() {
		FlatFileItemReader<Employee> reader = new FlatFileItemReader<Employee>();
		reader.setResource(new ClassPathResource("MOCK_DATA.csv"));
		reader.setLineMapper(new DefaultLineMapper<Employee>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setDelimiter(",");
						setNames("empno", "name", "salary", "address");
					}
				});

				setFieldSetMapper(new BeanWrapperFieldSetMapper<Employee>() {
					{
						setTargetType(Employee.class);
					}
				});
			}
		});
		return reader;
	}

	@Bean("itemReader")
	public ItemWriter<Employee> createItemWriter() {
		JdbcBatchItemWriter<Employee> jdbcBatchItemWriter = new JdbcBatchItemWriter<Employee>();
		jdbcBatchItemWriter.setDataSource(ds);
		jdbcBatchItemWriter.setSql(
				"INSERT INTO CSV_EMPLOYEE(EMPNO,NAME,SALARY,ADDRESS,GROSSSALARY,NETSALARY) VALUES(:empno,:name,:salary,:address,:grossSalary,:netSalary)");
		jdbcBatchItemWriter
				.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Employee>());
		return jdbcBatchItemWriter;
	}

	@Bean("step1")
	public Step createStep1() {
		return stepFactory.get("step1").<Employee, Employee>chunk(3).reader(createItemReader()).processor(csvProcessor)
				.writer(createItemWriter()).build();
	}

	@Bean("job1")
	public Job createJob() {
		return jobFactory.get("job1").listener(listener).incrementer(new RunIdIncrementer()).start(createStep1())
				.build();

	}

}