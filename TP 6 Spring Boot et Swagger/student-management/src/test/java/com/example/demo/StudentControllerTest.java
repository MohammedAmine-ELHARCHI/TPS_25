package com.example.demo;

import com.example.demo.controllers.StudentController;
import com.example.demo.entities.Student;
import com.example.demo.services.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class StudentControllerTest {
    
    @Mock
    private StudentService studentService;
    
    @InjectMocks
    private StudentController studentController;
    
    @Test
    void testSaveStudent() {
        // Given
        Student student = new Student("LACHGAR", "Mohamed", new Date());
        student.setId(1);
        
        // When
        when(studentService.save(any(Student.class))).thenReturn(student);
        ResponseEntity<Student> response = studentController.save(student);
        
        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("LACHGAR", response.getBody().getNom());
    }
    
    @Test
    void testDeleteStudent() {
        // Given
        int studentId = 1;
        
        // When
        when(studentService.delete(studentId)).thenReturn(true);
        ResponseEntity<Void> response = studentController.delete(studentId);
        
        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
    
    @Test
    void testFindAllStudents() {
        // Given
        List<Student> students = Arrays.asList(
            new Student("LACHGAR", "Mohamed", new Date()),
            new Student("ALAMI", "Ahmed", new Date())
        );
        
        // When
        when(studentService.findAll()).thenReturn(students);
        ResponseEntity<List<Student>> response = studentController.findAll();
        
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }
    
    @Test
    void testCountStudents() {
        // Given
        long count = 10L;
        
        // When
        when(studentService.countStudents()).thenReturn(count);
        ResponseEntity<Long> response = studentController.count();
        
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10L, response.getBody());
    }
    
    @Test
    void testFindByYear() {
        // Given
        Collection<Object[]> result = Arrays.asList();
        
        // When
        when(studentService.findNbrStudentByYear()).thenReturn(result);
        ResponseEntity<Collection<Object[]>> response = studentController.findByYear();
        
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }
}
