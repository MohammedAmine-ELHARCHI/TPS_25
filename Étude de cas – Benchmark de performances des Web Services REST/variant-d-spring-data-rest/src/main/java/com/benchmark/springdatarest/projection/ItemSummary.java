package com.benchmark.springdatarest.projection;

import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Projection(name = "itemSummary", types = { com.benchmark.springdatarest.entity.Item.class })
public interface ItemSummary {
    Long getId();
    String getSku();
    String getName();
    BigDecimal getPrice();
    Integer getStock();
    LocalDateTime getUpdatedAt();
    
    // Projection simplifiée de la catégorie pour éviter la surcharge HAL
    CategorySummary getCategory();
    
    interface CategorySummary {
        Long getId();
        String getCode();
        String getName();
    }
}