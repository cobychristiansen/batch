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
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

@EnableBatchProcessing
@Configuration
public class BatchConfiguration {
    @Autowired
    StepBuilderFactory stepBuilderFactory;
    @Autowired
    JobBuilderFactory jobBuilderFactory;

//    @Autowired
//    DataSource dataSource;

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
//    @Bean
    public ItemReader<Student> reader() {
        Resource[] resources = null;
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        try {
            resources = patternResolver.getResources("grades.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        MultiResourceItemReader<Student> reader = new MultiResourceItemReader<>();
        reader.setResources(resources);
        reader.setDelegate(studentReader);
        return reader;
    }

//    @Bean
//    public FlatFileItemReader<Student> reader() {
//        return new FlatFileItemReaderBuilder<Student>()
//                .name("studentReader")
//                .resource(new ClassPathResource("grades.csv"))
//                .delimited()
//                .names(new String[]{"lastName",
//                        "firstName",
//                        "ssn",
//                        "test1",
//                        "test2",
//                        "test3",
//                        "test4",
//                        "grade"
//                })
//                .fieldSetMapper(new BeanWrapperFieldSetMapper<Student>() {{
//                    setTargetType(Student.class);
//                }})
//                .build();
//    }


}
//make an api, csv as input
//populate sql table from input