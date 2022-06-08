package com.example.grade_spring_batch.configuration;

import com.example.grade_spring_batch.configuration.batch.StudentReader;
import com.example.grade_spring_batch.configuration.batch.StudentWriter;
import com.example.grade_spring_batch.model.Student;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

@EnableBatchProcessing
@Configuration
public class BatchConfiguration {
    @Autowired
    StepBuilderFactory stepBuilderFactory;
    @Autowired
    JobBuilderFactory jobBuilderFactory;
    @Autowired
    StudentReader studentReader;

    @Autowired
    StudentWriter studentWriter;

    @Bean("ETL")
    public Job jobRun() {
        return jobBuilderFactory.get("myJob")
                .incrementer(new RunIdIncrementer())
                .flow(step())
                .end()
                .build();
    }
    @Bean
    public Step step() {
        return stepBuilderFactory.get("myStep")
                .<Student, Student>chunk(1)
                .reader(reader())
                .writer(studentWriter)
                .build();
    }
    @Bean
    public ItemReader<Student> reader() {
        Resource[] resources = null;
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        try {
            resources = patternResolver.getResources("file:./data/*.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        MultiResourceItemReader<Student> reader = new MultiResourceItemReader<>();
        reader.setResources(resources);
        reader.setDelegate(studentReader);
        return reader;
    }
}
//make an api, csv as input
//populate sql table from input