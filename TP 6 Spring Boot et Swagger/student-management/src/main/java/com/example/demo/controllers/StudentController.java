package com.example.demo.controllers;

import com.example.demo.entities.Student;
import com.example.demo.services.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("students")
@Tag(name = "Student Management", description = "APIs for managing students")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    /**
     * Save a new student
     */
    @PostMapping("/save")
    @Operation(summary = "Create a new student", description = "Saves a new student to the database")
    public ResponseEntity<Student> save(@RequestBody Student student) {
        Student savedStudent = studentService.save(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }
    
    /**
     * Delete a student by ID
     */
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a student", description = "Deletes a student by ID")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean deleted = studentService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    /**
     * Get all students
     */
    @GetMapping("/all")
    @Operation(summary = "Get all students", description = "Retrieves a list of all students")
    public ResponseEntity<List<Student>> findAll() {
        List<Student> students = studentService.findAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    
    /**
     * Count total students
     */
    @GetMapping("/count")
    @Operation(summary = "Count students", description = "Returns the total number of students")
    public ResponseEntity<Long> count() {
        long count = studentService.countStudents();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    
    /**
     * Get student count grouped by birth year
     */
    @GetMapping("/byYear")
    @Operation(summary = "Get students by year", description = "Returns the number of students grouped by birth year")
    public ResponseEntity<Collection<Object[]>> findByYear() {
        Collection<Object[]> result = studentService.findNbrStudentByYear();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
