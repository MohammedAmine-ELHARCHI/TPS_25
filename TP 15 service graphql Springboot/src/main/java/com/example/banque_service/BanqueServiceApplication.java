package com.example.banque_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application for the Banking GraphQL Service.
 * 
 * <p>This application exposes a GraphQL API for managing bank accounts
 * and transactions. The GraphQL endpoint is typically available at
 * /graphql with GraphiQL interface at /graphiql.</p>
 * 
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0
 * @since 2025-01-01
 */
@SpringBootApplication
public class BanqueServiceApplication {
    
    private static final Logger logger = LoggerFactory.getLogger(BanqueServiceApplication.class);
    
    /**
     * Application entry point.
     * Starts the Spring Boot application with GraphQL support.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(BanqueServiceApplication.class, args);
        logger.info("Banking GraphQL Service started successfully");
        logger.info("GraphQL endpoint available at: /graphql");
        logger.info("GraphiQL interface available at: /graphiql");
    }
}
