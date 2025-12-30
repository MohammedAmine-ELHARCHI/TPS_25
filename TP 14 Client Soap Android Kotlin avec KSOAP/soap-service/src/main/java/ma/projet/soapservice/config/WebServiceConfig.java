package ma.projet.soapservice.config;

import jakarta.xml.ws.Endpoint;
import ma.projet.soapservice.ws.CompteService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for SOAP Web Service endpoints.
 * 
 * <p>This configuration sets up Apache CXF to publish the CompteService
 * as a SOAP web service. The service will be available at the /ws endpoint.</p>
 * 
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0
 * @since 2025-01-01
 */
@Configuration
public class WebServiceConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(WebServiceConfig.class);
    private static final String WEB_SERVICE_PATH = "/ws";
    
    @Autowired
    private Bus cxfBus;
    
    @Autowired
    private CompteService compteService;
    
    /**
     * Creates and publishes the SOAP web service endpoint.
     * 
     * <p>Configures Apache CXF to expose the CompteService at the specified path.
     * The endpoint will be accessible at http://localhost:8082/services/ws</p>
     * 
     * @return The configured SOAP endpoint
     */
    @Bean
    public Endpoint soapEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(cxfBus, compteService);
        endpoint.publish(WEB_SERVICE_PATH);
        
        logger.info("SOAP Web Service published successfully at path: {}", WEB_SERVICE_PATH);
        logger.debug("Service implementation: {}", compteService.getClass().getName());
        
        return endpoint;
    }
}
