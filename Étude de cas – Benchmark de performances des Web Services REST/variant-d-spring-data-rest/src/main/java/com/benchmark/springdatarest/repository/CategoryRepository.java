package com.benchmark.springdatarest.repository;

import com.benchmark.springdatarest.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "categories", path = "categories")
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    @RestResource(path = "by-code", rel = "by-code")
    Optional<Category> findByCode(@Param("code") String code);
    
    @RestResource(exported = false)
    boolean existsByCode(String code);
    
    @RestResource(path = "search-name", rel = "search-name")
    @Query("SELECT c FROM Category c WHERE c.name LIKE %:name%")
    Page<Category> findByNameContaining(@Param("name") String name, Pageable pageable);
    
    @RestResource(exported = false)
    @Query("SELECT COUNT(c) FROM Category c")
    long countCategories();
}