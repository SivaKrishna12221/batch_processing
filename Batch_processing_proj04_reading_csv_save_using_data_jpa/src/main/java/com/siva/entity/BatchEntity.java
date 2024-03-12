package com.siva.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class BatchEntity {

	@Id
	@GeneratedValue
	private Integer id;
	private Integer empno;
	@Column(length = 20)
	private String name;
	private Double salary;
	private String addrees;
}
