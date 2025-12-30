package ma.projet.grpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application for gRPC Account Service.
 * 
 * <p>This application exposes a gRPC service for managing bank accounts.
 * The gRPC server typically runs on port 9090 (configurable in application.yml).</p>
 * 
 * <p>Service operations include:</p>
 * <ul>
 *   <li>Retrieving all accounts or specific account by ID</li>
 *   <li>Creating new accounts</li>
 *   <li>Calculating balance statistics</li>
 * </ul>
 * 
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0
 * @since 2025-01-01
 */
@SpringBootApplication
public class Grpc2Application {
    
    private static final Logger logger = LoggerFactory.getLogger(Grpc2Application.class);
    
    /**
     * Application entry point.
     * Starts the Spring Boot application with gRPC server.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Grpc2Application.java, args);
        logger.info("gRPC Account Service started successfully");
        logger.info("gRPC server running on default port: 9090");
    }
}
