package com.example.grade_spring_batch.repository;

import com.example.grade_spring_batch.model.Student;
import com.example.grade_spring_batch.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findByStudentAccount(Student studentAccount);
}
