package com.example.Client;

import com.example.Client.entities.Client;
import com.example.Client.repositories.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

/**
 * Main application class for the Client Service.
 *
 * <p>This class represents the client management microservice in the
 * microservices architecture. The service provides CRUD operations
 * for Client entities and automatically registers itself with the
 * Eureka server for service discovery.</p>
 *
 * <p>Core Features:</p>
 * <ul>
 *   <li>Client Management (CRUD operations)</li>
 *   <li>Automatic registration with Eureka</li>
 *   <li>Database initialization with test data at startup</li>
 *   <li>Exposition of REST APIs for client operations</li>
 * </ul>
 *
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0.0
 * @since 2025-12-09
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ClientApplication {

    private static final Logger logger = LoggerFactory.getLogger(ClientApplication.class);

    /**
     * Main entry point for the Client Service application.
     *
     * <p>This method initializes and starts the Client microservice using
     * Spring Boot. It automatically configures all necessary components
     * for CRUD operations and Eureka registration.</p>
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
        logger.info("Client Service started successfully.");
    }

    /**
     * CommandLineRunner bean for H2 database initialization.
     *
     * <p>This method creates and configures a bean that runs at application
     * startup to initialize the H2 database with test data. Useful for
     * demonstrations and testing.</p>
     *
     * @param clientRepository Repository for Client data access
     * @return Configured CommandLineRunner for initialization
     */
    @Bean
    CommandLineRunner initializeH2Database(ClientRepository clientRepository) {
        return args -> {
            logger.info("Initializing H2 database with test clients...");
            clientRepository.save(new Client(null, "Rabab SELIMANI", 23f));
            clientRepository.save(new Client(null, "Amal RAMI", 22f));
            clientRepository.save(new Client(null, "Samir SAFI", 22f));
            logger.info("Database initialization complete.");
        };
    }

}
