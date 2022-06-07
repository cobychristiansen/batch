package com.example.grade_spring_batch.mapper;

import com.example.grade_spring_batch.model.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.*;

public class TestMapper {
    public static Test toTest(TestDTO testDTO) {
        if (testDTO == null) {
            return null;
        }
        return Test.builder()
                .test1(testDTO.getTest1())
                .test2(testDTO.getTest2())
                .test3(testDTO.getTest3())
                .test4(testDTO.getTest4())
                .studentAccount(StudentMapper.toStudent(testDTO.getStudentAccount()))
                .accountId(testDTO.getAccountId())
                .build();
    }

    public static TestDTO toTestDTO(Test test) {
        if (test == null) {
            return null;
        }
        return TestDTO.builder()
                .test1(test.getTest1())
                .test2(test.getTest2())
                .test3(test.getTest3())
                .test4(test.getTest4())
                .studentAccount(StudentMapper.toStudentDTO(test.getStudentAccount()))
                .accountId(test.getAccountId())
                .build();
    }

    public static Test toUpdateTest(Test test, Test updateTest) {
        test.setAccountId(updateTest.getAccountId() == null ? test.getAccountId() : updateTest.getAccountId());
        test.setTest1(updateTest.getTest1() == null ? test.getTest1() : updateTest.getTest1());
        test.setTest2(updateTest.getTest2() == null ? test.getTest2() : updateTest.getTest2());
        test.setTest3(updateTest.getTest3() == null ? test.getTest3() : updateTest.getTest3());
        test.setTest4(updateTest.getTest4() == null ? test.getTest4() : updateTest.getTest4());
        return test;
    }

    public static List<Test> toTests (List<TestDTO> testDTOs){
        return testDTOs.stream().map(a -> toTest(a)).collect(Collectors.toList());
    }

    public static List<TestDTO> toTestDTOs (List<Test> tests){
        return tests.stream().map(a -> toTestDTO(a)).collect(Collectors.toList());
    }
}
