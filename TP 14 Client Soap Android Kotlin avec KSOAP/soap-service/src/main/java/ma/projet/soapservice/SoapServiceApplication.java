package ma.projet.soapservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class for the SOAP Service.
 * 
 * <p>This application provides a SOAP web service for managing bank accounts (Comptes).
 * The service is accessible at http://localhost:8082/services/ws with WSDL at
 * http://localhost:8082/services/ws?wsdl</p>
 * 
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0
 * @since 2025-01-01
 */
@SpringBootApplication
public class SoapServiceApplication {
    
    private static final Logger logger = LoggerFactory.getLogger(SoapServiceApplication.class);
    
    private static final String SERVICE_BASE_URL = "http://localhost:8082/services/ws";
    private static final String WSDL_URL = SERVICE_BASE_URL + "?wsdl";
    
    /**
     * Application entry point.
     * Starts the Spring Boot application and logs service endpoints.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(SoapServiceApplication.class, args);
        
        logger.info("=".repeat(60));
        logger.info("SOAP Service started successfully");
        logger.info("Service endpoint: {}", SERVICE_BASE_URL);
        logger.info("WSDL available at: {}", WSDL_URL);
        logger.info("=".repeat(60));
    }
}
