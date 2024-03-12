package com.nt.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.nt.document.OExamResult;
import com.nt.listener.ExamResultJobListener;
import com.nt.model.ExamResult;
import com.nt.processor.ExamResultProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfigExamResult {

	@Autowired
	private StepBuilderFactory stepFactory;
	@Autowired
	private JobBuilderFactory jobFactory;
	@Autowired
	private ExamResultProcessor processer;
	@Autowired
	private ExamResultJobListener listener;

	@Autowired
	private MongoTemplate template;

	/*@Bean
	public ItemReader<ExamResult> createItemReader() {
		FlatFileItemReader<ExamResult> reader = new FlatFileItemReader<ExamResult>();
		reader.setResource(new ClassPathResource("topPercentage.csv"));
		reader.setLineMapper(new DefaultLineMapper<ExamResult>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setDelimiter(",");
						setNames("id", "dob", "percentage", "semestar");
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<ExamResult>() {
					{
						setTargetType(ExamResult.class);
					}
				});
			}
		});
		return reader;
	}*/

	@Bean
	public ItemReader<ExamResult> createItemReader() {
		System.out.println("BatchConfigExamResult.createItemReader()");

		FlatFileItemReader<ExamResult> reader = new FlatFileItemReader<ExamResult>();
		reader.setResource(new ClassPathResource("topPercentage.csv"));
		System.out.println("BatchConfigExamResult.createItemReader() Afer reading csv file");
		reader.setLineMapper(new DefaultLineMapper<ExamResult>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setDelimiter(",");
						setNames("id", "dob", "percentage", "semestar");

					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<ExamResult>() {
					{
						System.out.println("set target type");
						setTargetType(ExamResult.class);

					}
				});
			}
		});
		System.out.println("BatchConfigExamResult.createItemReader() item reader reading entire file and per");
		return reader;
	}

	/*@Bean
	public MongoItemWriter<OExamResult> createMongoWriter() {
		MongoItemWriter<OExamResult> writer = new MongoItemWriter<OExamResult>();
		writer.setCollection("top_brains_ex_result");
		writer.setTemplate(template);
		return writer;
	}*/
	@Bean
	public MongoItemWriter<OExamResult> createMongoWriter() {
		System.out.println("BatchConfigExamResult.createMongoItemWriter()");
		MongoItemWriter<OExamResult> mongoItemWriter = new MongoItemWriter<OExamResult>();
		mongoItemWriter.setTemplate(template);
		mongoItemWriter.setCollection("student_exam_result");
		System.out.println("BatchConfigExamResult.createMongoWriter() writer write data to mongo db");
		return mongoItemWriter;
	}

	@Bean("step1")
	public Step createStep1() {
		System.out.println("BatchConfigExamResult.createStep1()");
		return stepFactory.get("step1").<ExamResult, OExamResult>chunk(3).reader(createItemReader())
				.processor(processer).writer(createMongoWriter()).build();

	}

	@Bean("job1")
	public Job createJob1() {
		System.out.println("BatchConfigExamResult.createJob1()");
		return jobFactory.get("job1").listener(listener).incrementer(new RunIdIncrementer()).start(createStep1())
				.build();

	}
}
