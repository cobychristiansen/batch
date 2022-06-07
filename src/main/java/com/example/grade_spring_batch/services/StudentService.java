package com.example.grade_spring_batch.services;

import com.example.grade_spring_batch.model.Student;

public interface StudentService {

    Student saveOrUpdate (Student student);

    Student findById(Long id);

    void deleteById(Long id);
}
