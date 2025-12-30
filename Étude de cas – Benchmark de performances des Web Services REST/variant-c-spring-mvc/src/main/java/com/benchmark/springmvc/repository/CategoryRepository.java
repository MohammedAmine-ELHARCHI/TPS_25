package com.benchmark.springmvc.repository;

import com.benchmark.springmvc.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    Optional<Category> findByCode(String code);
    
    boolean existsByCode(String code);
    
    @Query("SELECT c FROM Category c WHERE c.name LIKE %:name%")
    Page<Category> findByNameContaining(@Param("name") String name, Pageable pageable);
    
    @Query("SELECT COUNT(c) FROM Category c")
    long countCategories();
}