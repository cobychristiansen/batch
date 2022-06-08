package com.example.grade_spring_batch.configuration.batch;

import com.example.grade_spring_batch.model.Student;
import com.example.grade_spring_batch.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.batch.item.ItemWriter;


import java.util.List;

    @Component
    public class StudentWriter implements ItemWriter<Student> {

        @Autowired
        StudentRepository studentRepository;
        @Override
        public void write(List<? extends Student> list) throws Exception {
            for (Student student : list) {
                System.out.println(String.format("StudentWriter:\n\tWriting data: %s %s %s %s", student.getFirstName(), student.getLastName(), student.getSsn(), student.getGrade()));
                studentRepository.save(student);
            }
        }
    }

