package com.example.demo.services;

import com.example.demo.entities.Student;
import com.example.demo.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    /**
     * Save or update a student
     */
    public Student save(Student student) {
        return studentRepository.save(student);
    }
    
    /**
     * Delete a student by ID
     * Returns true if deleted, false if not found
     */
    public boolean delete(int id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            studentRepository.delete(student.get());
            return true;
        }
        return false;
    }
    
    /**
     * Find all students
     */
    public List<Student> findAll() {
        return studentRepository.findAll();
    }
    
    /**
     * Count total number of students
     */
    public long countStudents() {
        return studentRepository.count();
    }
    
    /**
     * Find number of students grouped by birth year
     */
    public Collection<Object[]> findNbrStudentByYear() {
        return studentRepository.findNbrStudentByYear();
    }
}
