package com.siva.batchConfig;

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

import com.siva.events.StudentDataProcessingEventHandlerr;
import com.siva.model.Student;
import com.siva.processor.StudentDataProcessing;

//@Configuration
//@EnableBatchProcessing
public class BatchConfigurationFile {

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
		FlatFileItemReader<Student> reader = new FlatFileItemReader<Student>();
		reader.setResource(new ClassPathResource("student_info.csv"));
		DefaultLineMapper<Student> mapper = new DefaultLineMapper<Student>();// processing line by line
		DelimitedLineTokenizer deli = new DelimitedLineTokenizer();
		deli.setDelimiter(",");// seperate it
		deli.setNames("studentNo", "name", "address", "fees"); // putting names
		BeanWrapperFieldSetMapper<Student> fieldSet = new BeanWrapperFieldSetMapper<Student>();
		fieldSet.setTargetType(Student.class);
		mapper.setLineTokenizer(deli);
		mapper.setFieldSetMapper(fieldSet);
		reader.setLineMapper(mapper);
		return reader;

	}

	@Bean("writer")
	public ItemWriter<Student> createItemWriter() {

		JdbcBatchItemWriter<Student> writer = new JdbcBatchItemWriter<Student>();
		writer.setDataSource(ds);
		writer.setSql(

				"INSERT INTO BATCH_PROCESS_STUDENT_INFO(STUDENT_NO,NAME,ADDRESS,FEES,DISCOUNT,TOTAL_FEES) VALUES(:studentNo,:name,:address,:fees,:discount,:totalfees)");
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Student>());
		return writer;
	}

	@Bean("step1")
	public Step createStep() {
		return stepFactory.get("step1").<Student, Student>chunk(10).reader(createItemReader())
				.writer(createItemWriter()).processor(processor).build();
	}

	@Bean("job")
	public Job createJob() {
		return jobFactory.get("job").incrementer(new RunIdIncrementer()).listener(listener).start(createStep()).build();
	}

}
