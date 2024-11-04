package com.testTaskSmartDeltaSystems.Polevoj;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testTaskSmartDeltaSystems.Polevoj.controller.StudentController;
import com.testTaskSmartDeltaSystems.Polevoj.model.Student;
import com.testTaskSmartDeltaSystems.Polevoj.service.StudentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class StudentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    public void testGetAllStudentsSuccess() throws Exception {
        List<Student> students = Collections.singletonList(new Student("1", "Иванов", "Иван", "Иванович", "101", 4.5));

        when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lastName", is("Иванов")))
                .andExpect(jsonPath("$[0].firstName", is("Иван")))
                .andExpect(jsonPath("$[0].middleName", is("Иванович")))
                .andExpect(jsonPath("$[0].group", is("101")))
                .andExpect(jsonPath("$[0].averageGrade", is(4.5)));
    }

    @Test
    public void testGetAllStudentsNoData() throws Exception {
        when(studentService.getAllStudents()).thenThrow(NoSuchElementException.class);

        mockMvc.perform(get("/students"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateStudentSuccess() throws Exception {
        Student student = new Student("1", "Петров", "Петр", "Петрович", "102", 4.8);

        String jsonString = objectMapper.writeValueAsString(student);

        when(studentService.createStudent(any())).thenReturn(student);

        mockMvc.perform(put("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.lastName", is("Петров")))
                .andExpect(jsonPath("$.firstName", is("Петр")))
                .andExpect(jsonPath("$.middleName", is("Петрович")))
                .andExpect(jsonPath("$.group", is("102")))
                .andExpect(jsonPath("$.averageGrade", is(4.8)));
    }

    @Test
    public void testCreateStudentFailure() throws Exception {
        String invalidJson = "{dghdfgh}"; // Недопустимый JSON

        mockMvc.perform(put("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest()); // Ожидается ошибка из-за недопустимого JSON
    }

    @Test
    public void testUpdateStudentSuccess() throws Exception {
        Student oldStudent = new Student("1", "Иванов", "Иван", "Иванович", "101", 4.5);
        studentService.createStudent(oldStudent);

        Student updatedStudent = new Student(oldStudent.getId(), "Петров", "Петр", "Петрович", "201", 4.8);
        studentService.updateStudent(updatedStudent);
        when(studentService.getStudentById(oldStudent.getId())).thenReturn(updatedStudent);

        String updatedStudentJson = objectMapper.writeValueAsString(updatedStudent);

        mockMvc.perform(post("/students/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedStudentJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName", is("Петров")))
                .andExpect(jsonPath("$.firstName", is("Петр")))
                .andExpect(jsonPath("$.middleName", is("Петрович")))
                .andExpect(jsonPath("$.group", is("201")))
                .andExpect(jsonPath("$.averageGrade", is(4.8))); // Проверяем обновленные данные
    }

    @Test
    public void testUpdateStudentNotFound() throws Exception {
        when(studentService.getStudentById(any())).thenThrow(NoSuchElementException.class);

        Student student = new Student(null, "Петров", "Петр", "Петрович", null, 4.8);
        String jsonString = objectMapper.writeValueAsString(student);

        mockMvc.perform(post("/students/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteStudentSuccess() throws Exception {

        Student deletedStudent = new Student("1", "Иванов", "Иван", "Иванович", "101", 4.5);

        studentService.createStudent(deletedStudent);
        studentService.deleteStudent(deletedStudent.getId());

        mockMvc.perform(delete("/students/" + deletedStudent.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteStudentNotFound() throws Exception {
        when(studentService.getStudentById("12345")).thenThrow(new NoSuchElementException());

        mockMvc.perform(delete("/students/12345"))
                .andExpect(status().isNotFound());
    }
    @AfterEach
    public void resetDb() {
        studentService.deleteAllStudents();
    }

}