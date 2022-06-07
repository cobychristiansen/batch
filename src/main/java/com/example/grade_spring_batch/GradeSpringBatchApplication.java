package com.example.grade_spring_batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class GradeSpringBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(GradeSpringBatchApplication.class, args);
	}

}
