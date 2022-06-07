package com.example.grade_spring_batch.configuration;

import com.example.grade_spring_batch.model.Student;
import com.example.grade_spring_batch.repository.StudentRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@EnableBatchProcessing
@Configuration
public class BatchConfiguration {
    @Autowired
    StepBuilderFactory stepBuilderFactory;
    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StudentRepository studentRepository;

    @Bean
    public Job job() {
        Step step = stepBuilderFactory.get("ETL-file-load")
                .<Student, Student>chunk(100)
                .reader(this.reader())
                .writer()
                .build();
        return jobBuilderFactory.get("ETL-load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }
    @Bean
    public Step step1(Student student) {
        if(student.getId() != null) {
            Student studentData = studentRepository.getOne(student.getId());
            if(studentData == null) {
                return null;
            } else {
                studentData.setFirstName(student.getFirstName());
                studentData.setGrade(student.getGrade());
                studentData.setLastName(student.getLastName());
                studentData.setSsn(student.getSsn());
                studentData.setId(student.getId());
                studentData.setTests(student.getTests());
                Student studentReturnObject = new Student();
                try {
                    studentReturnObject = studentRepository.save(studentData);
                } catch (Exception e) {
                    System.out.println(e.getStackTrace());
;               }
                return stepBuilderFactory.get("step1").;
            }
        }
    }
    @Bean
    public FlatFileItemReader<Student> reader(@Value("${input}") Resource resource) {
        FlatFileItemReader<Student> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(resource);
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }
    @Bean
    public LineMapper<Student> lineMapper() {
        DefaultLineMapper<Student> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[] {"Last name", "First name", "SSN", "Test1", "Test2", "Test3", "Test4", "Final Grade"});
        BeanWrapperFieldSetMapper<Student> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Student.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}
