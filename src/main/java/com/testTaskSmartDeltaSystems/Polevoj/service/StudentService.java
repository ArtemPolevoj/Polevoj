package com.testTaskSmartDeltaSystems.Polevoj.service;

import com.testTaskSmartDeltaSystems.Polevoj.model.Student;
import com.testTaskSmartDeltaSystems.Polevoj.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(String id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public void updateStudent(Student student) {
        Student updateStudent = getStudentById(student.getId());
        updateStudent.setLastName(student.getLastName());
        updateStudent.setFirstName(student.getFirstName());
        updateStudent.setMiddleName(student.getMiddleName());
        updateStudent.setGroup(student.getGroup());
        updateStudent.setAverageGrade(student.getAverageGrade());
        studentRepository.save(student);
    }

    public void deleteStudent(String id) {
        studentRepository.deleteById(id);
    }
}
