package com.example.grade_spring_batch.configuration.batch;

import com.example.grade_spring_batch.model.Student;
import com.example.grade_spring_batch.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.batch.item.ItemWriter;


import java.util.List;

    @Component
    public class DBWriter implements ItemWriter<Student> {

        private StudentRepository studentRepository;

        @Autowired
        public DBWriter (StudentRepository studentRepository) {
            this.studentRepository = studentRepository;
        }

        @Override
        public void write(List<? extends Student> students) throws Exception{
            System.out.println("Data Saved for Users: " + students);
            studentRepository.saveAll(students);
        }
    }

