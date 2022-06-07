package com.example.grade_spring_batch.services;

import com.example.grade_spring_batch.mapper.TestMapper;
import com.example.grade_spring_batch.model.Student;
import com.example.grade_spring_batch.model.Test;
import com.example.grade_spring_batch.repository.StudentRepository;
import com.example.grade_spring_batch.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    TestRepository testRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    StudentRepository studentRepository;

    @Override
    public Test saveOrUpdate(Test test) {
        if(test.getId() != null){
            Test testData = testRepository.getOne(test.getId());
            if(testData == null) {
                return null;
            }
            testData = TestMapper.toUpdateTest(testData, test);
            if (test.getAccountId() != null){
                Student student = studentService.findById(test.getAccountId());
                test.setStudentAccount(student);
            }
            return testRepository.save(testData);
        }
        if (test.getAccountId() != null)
        {
            Student student = studentService.findById(test.getAccountId());
            test.setStudentAccount(student);
        }
        return testRepository.save(test);
    }

    @Override
    public Optional<Test> findById(Long id) {
        return testRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        testRepository.deleteById(id);
    }

    @Override
    public List<Test> findByStudentAccount(Long studentId) {
        Student student = studentService.findById(studentId);
        return testRepository.findByStudentAccount(student);
    }
}
