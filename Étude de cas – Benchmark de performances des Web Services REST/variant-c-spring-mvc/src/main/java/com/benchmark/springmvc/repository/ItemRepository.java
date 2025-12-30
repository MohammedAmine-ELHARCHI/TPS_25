package com.benchmark.springmvc.repository;

import com.benchmark.springmvc.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    
    Optional<Item> findBySku(String sku);
    
    boolean existsBySku(String sku);
    
    Page<Item> findByCategoryId(Long categoryId, Pageable pageable);
    
    @Query("SELECT i FROM Item i JOIN FETCH i.category WHERE i.category.id = :categoryId")
    Page<Item> findByCategoryIdWithCategory(@Param("categoryId") Long categoryId, Pageable pageable);
    
    @Query("SELECT i FROM Item i WHERE i.name LIKE %:name%")
    Page<Item> findByNameContaining(@Param("name") String name, Pageable pageable);
    
    @Query("SELECT COUNT(i) FROM Item i WHERE i.category.id = :categoryId")
    long countByCategoryId(@Param("categoryId") Long categoryId);
    
    @Query("SELECT COUNT(i) FROM Item i")
    long countItems();
}