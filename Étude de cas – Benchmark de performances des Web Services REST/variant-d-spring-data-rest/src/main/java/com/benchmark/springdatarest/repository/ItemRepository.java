package com.benchmark.springdatarest.repository;

import com.benchmark.springdatarest.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "items", path = "items")
public interface ItemRepository extends JpaRepository<Item, Long> {
    
    @RestResource(path = "by-sku", rel = "by-sku")
    Optional<Item> findBySku(@Param("sku") String sku);
    
    @RestResource(exported = false)
    boolean existsBySku(String sku);
    
    @RestResource(path = "by-category", rel = "by-category")
    Page<Item> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);
    
    @RestResource(path = "by-category-with-details", rel = "by-category-with-details")
    @Query("SELECT i FROM Item i JOIN FETCH i.category WHERE i.category.id = :categoryId")
    Page<Item> findByCategoryIdWithCategory(@Param("categoryId") Long categoryId, Pageable pageable);
    
    @RestResource(path = "search-name", rel = "search-name")
    @Query("SELECT i FROM Item i WHERE i.name LIKE %:name%")
    Page<Item> findByNameContaining(@Param("name") String name, Pageable pageable);
    
    @RestResource(exported = false)
    @Query("SELECT COUNT(i) FROM Item i WHERE i.category.id = :categoryId")
    long countByCategoryId(@Param("categoryId") Long categoryId);
    
    @RestResource(exported = false)
    @Query("SELECT COUNT(i) FROM Item i")
    long countItems();
}