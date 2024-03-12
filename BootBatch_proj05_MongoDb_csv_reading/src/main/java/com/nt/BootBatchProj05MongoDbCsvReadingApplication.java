package com.nt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class BootBatchProj05MongoDbCsvReadingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootBatchProj05MongoDbCsvReadingApplication.class, args);
	}

}
