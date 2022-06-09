package com.example.grade_spring_batch.configuration.batch;

import com.example.grade_spring_batch.model.Student;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class StudentReader extends FlatFileItemReader<Student> implements ItemReader<Student> {
    public StudentReader() {
        setResource(new FileSystemResource("grades.csv"));
        setLinesToSkip(1);
        setLineMapper(getDefaultLineMapper());
    }
    public DefaultLineMapper<Student> getDefaultLineMapper() {
        DefaultLineMapper<Student> defaultLineMapper = new DefaultLineMapper<Student>();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();

        delimitedLineTokenizer.setNames(new String[] {
                "lastName",
                "firstName",
                "ssn",
                "grade"
        });

        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

        BeanWrapperFieldSetMapper<Student> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<Student>();
        beanWrapperFieldSetMapper.setTargetType(Student.class);

        beanWrapperFieldSetMapper.setDistanceLimit(1);

        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        return defaultLineMapper;

    }
}

