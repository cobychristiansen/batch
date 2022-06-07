package com.example.grade_spring_batch.services;

import com.example.grade_spring_batch.model.Test;
import com.example.grade_spring_batch.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public interface TestService {

    Test saveOrUpdate(Test test);

    Optional<Test> findById(Long id);

    void deleteById(Long id);

    List<Test> findByStudentAccount(Long studentId);
}
