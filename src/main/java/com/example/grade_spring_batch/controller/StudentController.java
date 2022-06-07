package com.example.grade_spring_batch.controller;

import com.example.grade_spring_batch.mapper.StudentDTO;
import com.example.grade_spring_batch.mapper.StudentMapper;
import com.example.grade_spring_batch.model.Student;
import com.example.grade_spring_batch.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("createStudent")
    public Student createStudent(@RequestBody Student student) {
        return studentService.saveOrUpdate(student);
    }

    @GetMapping("findStudentById/{studentId}")
    public StudentDTO findStudentById(@PathVariable("studentId") long studentId) {
        Student studentData = studentService.findById(studentId);
        return StudentMapper.toStudentDTO(studentData);
    }

    @DeleteMapping("deleteStudentById/{studentId}")
    public void deleteStudentById(@PathVariable("studentId") long studentId){
        studentService.deleteById(studentId);
    }
}
