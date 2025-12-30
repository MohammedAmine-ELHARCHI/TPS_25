package com.example.Voiture;

import com.example.Voiture.entities.Voiture;
import com.example.Voiture.models.Client;
import com.example.Voiture.repositories.VoitureRepository;
import com.example.Voiture.services.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * Main application class for the Voiture Service.
 *
 * <p>This class represents the car management microservice. It manages
 * car entities, communicates with the Client service via Feign, and
 * registers with the Eureka server for service discovery.</p>
 *
 * <p>Core Features:</p>
 * <ul>
 *   <li>Car Management (CRUD operations)</li>
 *   <li>Inter-service communication via Feign</li>
 *   <li>Automatic registration with Eureka</li>
 *   <li>Database initialization with inter-service data retrieval</li>
 *   <li>Exposition of REST APIs for car operations</li>
 * </ul>
 *
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0.0
 * @since 2025-12-09
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class VoitureApplication {

    private static final Logger logger = LoggerFactory.getLogger(VoitureApplication.class);

    /**
     * Main entry point for the Voiture Service application.
     *
     * <p>This method initializes and starts the Voiture microservice using
     * Spring Boot. It configures all necessary components for CRUD
     * operations and inter-service communication.</p>
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(VoitureApplication.class, args);
        logger.info("Voiture Service started successfully.");
    }

    /**
     * CommandLineRunner bean for H2 database initialization.
     *
     * <p>This method runs at application startup to initialize the database
     * with test data. It demonstrates inter-service communication by
     * retrieving clients from the Client service via Feign.</p>
     *
     * @param voitureRepository Repository for car data access
     * @param clientService Feign client for communicating with Client service
     * @return Configured CommandLineRunner for initialization
     */
    @Bean
    CommandLineRunner initializeH2Database(VoitureRepository voitureRepository, ClientService clientService) {
        return args -> {
            logger.info("Initializing H2 database with test cars and fetching clients...");
            
            try {
                // Fetch clients from Client service via Feign
                Client c1 = clientService.getClientById(2L);
                Client c2 = clientService.getClientById(1L);

                logger.info("Fetched Client 1: ID={}, Name={}", c2.getId(), c2.getName());
                logger.info("Fetched Client 2: ID={}, Name={}, Age={}", c1.getId(), c1.getName(), c1.getAge());

                // Create and save test cars
                Voiture v1 = new Voiture(null, "Toyota", "A 25 333", "Corolla", 1L, c2);
                Voiture v2 = new Voiture(null, "Renault", "B 6 3456", "Megane", 1L, c2);
                Voiture v3 = new Voiture(null, "Peugeot", "A 55 4444", "301", 2L, c1);

                voitureRepository.save(v1);
                voitureRepository.save(v2);
                voitureRepository.save(v3);
                
                logger.info("Database initialization complete. 3 cars saved.");
            } catch (Exception e) {
                logger.error("Error during database initialization: {}", e.getMessage());
            }
        };
    }

}
