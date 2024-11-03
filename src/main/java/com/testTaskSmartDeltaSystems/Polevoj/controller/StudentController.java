package com.testTaskSmartDeltaSystems.Polevoj.controller;

import com.testTaskSmartDeltaSystems.Polevoj.model.Student;
import com.testTaskSmartDeltaSystems.Polevoj.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/students")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(studentService.getAllStudents());
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {

        try {
            studentService.createStudent(student);
            return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(student));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable("id") String id, @RequestBody Student updatedStudent) {


        try {
            studentService.getStudentById(id);
            updatedStudent.setId(id);
            studentService.updateStudent(updatedStudent);
            return ResponseEntity.status(HttpStatus.OK).body(studentService.getStudentById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable String id) {

        try {
            studentService.deleteStudent(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(studentService.getStudentById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
