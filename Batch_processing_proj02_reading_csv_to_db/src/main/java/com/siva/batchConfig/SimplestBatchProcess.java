package com.siva.batchConfig;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.siva.events.StudentDataProcessingEventHandlerr;
import com.siva.model.Student;
import com.siva.processor.StudentDataProcessing;

@EnableBatchProcessing
@Configuration
public class SimplestBatchProcess {

	@Autowired
	private StepBuilderFactory stepFactory;

	@Autowired
	private JobBuilderFactory jobFactory;
	@Autowired
	private DataSource ds;

	@Autowired
	private StudentDataProcessing processor;

	@Autowired
	private StudentDataProcessingEventHandlerr listener;

	@Bean("reader")
	public ItemReader<Student> createItemReader() {
		return new FlatFileItemReaderBuilder<Student>().resource(new ClassPathResource("student_info.csv")).delimited()
				.delimiter(",").names("studentNo", "name", "address", "fees").targetType(Student.class).build();
	}

	@Bean("writer")
	public ItemWriter<Student> createItemWriter() {
		return new JdbcBatchItemWriterBuilder<Student>().dataSource(ds).sql(
				"INSERT INTO BATCH_PROCESS_STUDENT_INFO(STUDENT_NO,NAME,ADDRESS,FEES,DISCOUNT,TOTAL_FEES) VALUES(:studentNo,:name,:address,:fees,:discount,:totalfees)")
				.beanMapped().build();

	}

	@Bean("step")
	public Step createStep() {
		return stepFactory.get("step").<Student, Student>chunk(10).reader(createItemReader()).processor(processor)
				.writer(createItemWriter()).build();
	}

	@Bean("job")
	public Job createJob() {
		return jobFactory.get("job").incrementer(new RunIdIncrementer()).listener(listener).start(createStep()).build();
	}
}
