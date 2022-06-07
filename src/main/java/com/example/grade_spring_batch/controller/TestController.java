package com.example.grade_spring_batch.controller;

import com.example.grade_spring_batch.mapper.TestDTO;
import com.example.grade_spring_batch.mapper.TestMapper;
import com.example.grade_spring_batch.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestService testService;

    @PostMapping("addTest")
    public TestDTO addTest(@RequestBody TestDTO testDTO){
        return TestMapper.toTestDTO(testService.saveOrUpdate(TestMapper.toTest(testDTO)));
    }

    @GetMapping("findByStudentId/{studentId}")
    public List<TestDTO> findByUserId(@PathVariable("studentId") long studentId){
        return TestMapper.toTestDTOs(testService.findByStudentAccount(studentId));
    }

    @DeleteMapping("deleteByStudentId/{studentId}")
    public void deleteByStudentId(@PathVariable("studentId") long studentId){
        testService.deleteById(studentId);
    }
}
