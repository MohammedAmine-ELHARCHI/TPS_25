package com.example.Eureka_Server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Main application class for the Eureka Service Discovery Server.
 *
 * <p>This class serves as the entry point for the Eureka Server application,
 * which acts as a service discovery server in a microservices architecture.
 * The Eureka server allows services to register themselves and discover
 * other services dynamically.</p>
 *
 * <p>Core Features:</p>
 * <ul>
 *   <li>Netflix Eureka Service Discovery Server</li>
 *   <li>Automatic registration of microservices</li>
 *   <li>Dynamic service discovery by clients</li>
 *   <li>Web interface for monitoring registered services</li>
 * </ul>
 *
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0.0
 * @since 2025-12-09
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {

    private static final Logger logger = LoggerFactory.getLogger(EurekaServerApplication.class);

    /**
     * Main entry point for the Eureka Server application.
     *
     * <p>This method initializes and starts the Eureka server using Spring Boot.
     * It automatically configures all necessary components for the discovery
     * server to function correctly.</p>
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
        logger.info("Eureka Server started successfully and is ready for service registration.");
    }

}
