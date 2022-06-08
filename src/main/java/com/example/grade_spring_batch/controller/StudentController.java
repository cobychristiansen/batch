package com.example.grade_spring_batch.controller;

import com.example.grade_spring_batch.mapper.StudentDTO;
import com.example.grade_spring_batch.mapper.StudentMapper;
import com.example.grade_spring_batch.model.Student;
import com.example.grade_spring_batch.services.StudentService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    ApplicationContext applicationContext;

    @PostMapping("createStudent")
    public Student createStudent(@RequestBody Student student) {
        return studentService.saveOrUpdate(student);
    }

    @GetMapping("findStudentById/{studentId}")
    public StudentDTO findStudentById(@PathVariable("studentId") long studentId) {
        Student studentData = studentService.findById(studentId);
        return StudentMapper.toStudentDTO(studentData);
    }

    @GetMapping("/testBatch")
    public void testBatch() {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("startTime", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(map);
        try {
//            System.out.println(applicationContext.containsBean("ETL"));
            jobLauncher.run((Job)applicationContext.getBean("ETL"), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            throw new RuntimeException(e);
//            AppLog.build()
//                    ...
        }
    }

    @DeleteMapping("deleteStudentById/{studentId}")
    public void deleteStudentById(@PathVariable("studentId") long studentId){
        studentService.deleteById(studentId);
    }
}
