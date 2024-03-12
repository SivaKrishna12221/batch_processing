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
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.siva.event.StudentDataProcessingEventHandlerr;
import com.siva.mapper.StudentMapper;
import com.siva.model.Student;
import com.siva.processor.StudentDataProcessing;

@Configuration
@EnableBatchProcessing
public class BatchConfigRead {

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
		System.out.println("BatchConfigRead.createItemReader()");
		JdbcCursorItemReader<Student> reader = new JdbcCursorItemReader<Student>();
		reader.setDataSource(ds);
		reader.setSql("SELECT STUDENT_NO,NAME,ADDRESS,FEES,DISCOUNT,TOTAL_FEES FROM BATCH_PROCESS_STUDENT_INFO");
		reader.setRowMapper(new StudentMapper());
		return reader;
	}

	/*@Bean("writer")
	public ItemWriter<Student> createItemWriter() {
	
		FlatFileItemWriter<Student> writer = new FlatFileItemWriter<Student>();
		writer.setResource(new FileSystemResource("D:/springBatch/db_student.csv"));
		// we have to create line aggregator to arrange records line by line
		// for this we have to p
		DelimitedLineAggregator<Student> dlAggregator = new DelimitedLineAggregator<Student>();// this class extends the
																								// abstract line
																								// aggregator that
																								// implements
																								// LineAggregator
																								// interface ->it will
																								// taken care to create
																								// rows
		// so when we create a object for the child we can all the methods
		dlAggregator.setDelimiter(",");// it will the separate the values
		// field extractor convert the object[] into columns
		// this need FieldExtractor<T> object
		// for this we have to create object the feild set extractor implementation
		// class
		BeanWrapperFieldExtractor<Student> beanWrapper = new BeanWrapperFieldExtractor<Student>();
		beanWrapper.setNames(new String[] { "studentNo", "name", "address", "fees", "discount", "totalfees" });
		// filed Extrator needs FieldExtractorInterface object it will get the
		// implementation class
		dlAggregator.setFieldExtractor(beanWrapper);
		writer.setLineAggregator(dlAggregator);
		return writer;
	}
	*/
	@Bean
	public ItemWriter<Student> createItemWriter() {
		System.out.println("BatchConfigRead.createItemWriter()");
		FlatFileItemWriter<Student> writer = new FlatFileItemWriter<Student>();
		writer.setResource(new FileSystemResource("D:/springBatch/db_student_info.csv"));
		writer.setLineAggregator(new DelimitedLineAggregator<Student>() {
			{
				setDelimiter(",");
				setFieldExtractor(new BeanWrapperFieldExtractor<Student>() {
					{
						setNames(new String[] { "studentNo", "name", "address", "fees", "discount", "totalfees" });// here
																													// this
																													// column
																													// name
																													// should
																													// match
																													// with
																													// model
																													// class
					}
				});
			}
		});
		return writer;
	}

	@Bean("step1")
	public Step createStep() {
		return stepFactory.get("step1").<Student, Student>chunk(10).reader(createItemReader()).processor(processor)
				.writer(createItemWriter()).build();
	}

	@Bean("job")
	public Job createJob() {
		return jobFactory.get("job").incrementer(new RunIdIncrementer()).listener(listener).start(createStep()).build();
	}
}