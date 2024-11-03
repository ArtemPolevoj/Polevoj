package com.testTaskSmartDeltaSystems.Polevoj;


import com.testTaskSmartDeltaSystems.Polevoj.model.Student;
import com.testTaskSmartDeltaSystems.Polevoj.repository.StudentRepository;
import com.testTaskSmartDeltaSystems.Polevoj.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Test
    void shouldReturnAllStudents() {

        List<Student> students = new ArrayList<>();
        students.add(new Student( "Иванов", "Иван", "Иванович", "101", 4.5));
        students.add(new Student( "Петров", "Петр", "Петрович", "102", 4.8));
        given(studentRepository.findAll()).willReturn(students);

         List<Student> result = studentService.getAllStudents();

        assertThat(result).hasSize(2);
        assertThat(result.get(0)).extracting("lastName", "firstName", "middleName", "group", "averageGrade")
                .containsExactly("Иванов", "Иван", "Иванович", "101", 4.5);
        verify(studentRepository).findAll();
    }

    @Test
    void shouldSaveStudent() {
         Student student = new Student("Иванов", "Иван", "Иванович", "101", 4.5);

        studentService.createStudent(student);

        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void shouldFindStudentById() {
        String id = "1";
        Student expectedStudent = new Student(id, "Иванов", "Иван", "Иванович", "101", 4.5);
        given(studentRepository.findById(id)).willReturn(Optional.of(expectedStudent));

        Student actualStudent = studentService.getStudentById(id);

        assertThat(actualStudent);;
        assertThat(actualStudent);
        verify(studentRepository).findById(id);
    }

    @Test
    void shouldDeleteStudent() {
        String id = "1";

        studentService.deleteStudent(id);
        verify(studentRepository).deleteById(id);
    }
}
