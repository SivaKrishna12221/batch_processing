package com.nt.document;

import java.sql.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "csv_exam_result")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OExamResult {

	@Id
	private String uid;
	private Integer id;
	private Date dob;
	private Double percentage;
	private Integer semestar;
}
