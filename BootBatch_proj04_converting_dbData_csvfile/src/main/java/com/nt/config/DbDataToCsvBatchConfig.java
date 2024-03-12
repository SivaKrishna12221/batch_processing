package com.nt.config;

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

import com.nt.listener.ExamResultListener;
import com.nt.model.ExamResult;
import com.nt.processor.ExamResultProcess;
import com.nt.rowMapper.ExamResultMap;

@Configuration
@EnableBatchProcessing
public class DbDataToCsvBatchConfig {
	@Autowired
	private StepBuilderFactory stepFactory;

	@Autowired
	private DataSource ds;
	@Autowired
	private JobBuilderFactory jobFactory;
	@Autowired
	private ExamResultListener listener;
	@Autowired
	private ExamResultProcess processor;

	@Bean
	public ItemReader<ExamResult> createItemReader() {
		JdbcCursorItemReader<ExamResult> reader = new JdbcCursorItemReader<ExamResult>();
		reader.setDataSource(ds);
		reader.setSql("select id,dob,percentage,semestar from springbatch.exam_result");
		reader.setRowMapper(new ExamResultMap());
		return reader;
	}

	@Bean
	public ItemWriter<ExamResult> createItemWrite() {
		FlatFileItemWriter<ExamResult> writer = new FlatFileItemWriter<ExamResult>();
		writer.setResource(new FileSystemResource("D:/springbatch/topPercentage.csv"));
		writer.setLineAggregator(new DelimitedLineAggregator<ExamResult>() {
			{
				setDelimiter(",");
				setFieldExtractor(new BeanWrapperFieldExtractor<ExamResult>() {
					{
						setNames(new String[] { "id", "dob", "percentage", "semestar" });
					}
				});
			}
		});
		return writer;
	}

	@Bean("step1")
	public Step createStep1() {
		return stepFactory.get("step1").<ExamResult, ExamResult>chunk(3).reader(createItemReader()).processor(processor)
				.writer(createItemWrite()).build();
	}

	@Bean("job1")
	public Job createJob() {
		return jobFactory.get("job1").listener(listener).incrementer(new RunIdIncrementer()).start(createStep1())
				.build();
	}
}
