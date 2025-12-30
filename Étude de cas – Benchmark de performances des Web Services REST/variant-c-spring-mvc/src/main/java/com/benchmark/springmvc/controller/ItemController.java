package com.benchmark.springmvc.controller;

import com.benchmark.springmvc.entity.Item;
import com.benchmark.springmvc.repository.CategoryRepository;
import com.benchmark.springmvc.repository.ItemRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<Page<Item>> getItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(required = false) Long categoryId) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<Item> items;
        
        if (categoryId != null) {
            items = itemRepository.findByCategoryIdWithCategory(categoryId, pageable);
        } else {
            items = itemRepository.findAll(pageable);
        }
        
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Optional<Item> item = itemRepository.findById(id);
        return item.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Item> createItem(@Valid @RequestBody Item item) {
        // Vérifier l'unicité du SKU
        if (itemRepository.existsBySku(item.getSku())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        
        // Vérifier que la catégorie existe
        if (item.getCategory() == null || !categoryRepository.existsById(item.getCategory().getId())) {
            return ResponseEntity.badRequest().build();
        }
        
        Item savedItem = itemRepository.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(
            @PathVariable Long id,
            @Valid @RequestBody Item updatedItem) {
        
        return itemRepository.findById(id)
                .map(item -> {
                    // Vérifier l'unicité du SKU si il a changé
                    if (!item.getSku().equals(updatedItem.getSku()) &&
                        itemRepository.existsBySku(updatedItem.getSku())) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).<Item>build();
                    }
                    
                    // Vérifier que la nouvelle catégorie existe si elle a changé
                    if (updatedItem.getCategory() != null && 
                        !updatedItem.getCategory().getId().equals(item.getCategory().getId()) &&
                        !categoryRepository.existsById(updatedItem.getCategory().getId())) {
                        return ResponseEntity.badRequest().<Item>build();
                    }
                    
                    item.setSku(updatedItem.getSku());
                    item.setName(updatedItem.getName());
                    item.setPrice(updatedItem.getPrice());
                    item.setStock(updatedItem.getStock());
                    if (updatedItem.getCategory() != null) {
                        item.setCategory(updatedItem.getCategory());
                    }
                    
                    Item saved = itemRepository.save(item);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        return itemRepository.findById(id)
                .map(item -> {
                    itemRepository.delete(item);
                    return ResponseEntity.<Void>ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}