package com.benchmark.springdatarest.projection;

import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;

@Projection(name = "categorySummary", types = { com.benchmark.springdatarest.entity.Category.class })
public interface CategorySummary {
    Long getId();
    String getCode();
    String getName();
    LocalDateTime getUpdatedAt();
}