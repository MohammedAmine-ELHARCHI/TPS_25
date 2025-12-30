package com.example.GateWay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main application class for the API Gateway.
 *
 * <p>This class represents the entry point for the API Gateway, which acts
 * as a single point of entry for all microservice clients. The gateway
 * routes requests to appropriate services and can apply cross-cutting
 * policies such as authentication, authorization, logging, etc.</p>
 *
 * <p>Core Features:</p>
 * <ul>
 *   <li>Intelligent request routing to microservices</li>
 *   <li>Automatic service discovery via Eureka</li>
 *   <li>Load balancing between service instances</li>
 *   <li>Centralized security policy management</li>
 *   <li>Request monitoring and metrics</li>
 * </ul>
 *
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0.0
 * @since 2025-12-09
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GateWayApplication {

    private static final Logger logger = LoggerFactory.getLogger(GateWayApplication.class);

    /**
     * Main entry point for the Gateway application.
     *
     * <p>This method initializes and starts the API Gateway using Spring Boot
     * and Spring Cloud Gateway. It automatically configures all necessary
     * components for routing and service discovery.</p>
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
        logger.info("API Gateway started successfully and is ready to route requests.");
    }

}
