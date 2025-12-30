package com.example.Client.controllers;

import com.example.Client.entities.Client;
import com.example.Client.repositories.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for client management.
 *
 * <p>This class exposes REST endpoints for read operations on Client
 * entities. It is part of the Client microservice in the microservices
 * architecture.</p>
 *
 * <p>Exposed Endpoints:</p>
 * <ul>
 *   <li>GET /clients: Retrieves all clients</li>
 *   <li>GET /client/{id}: Retrieves a client by its ID</li>
 * </ul>
 *
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0.0
 * @since 2025-12-09
 */
@RestController
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    /**
     * Repository for Client data access.
     * Automatically injected by Spring.
     */
    @Autowired
    private ClientRepository clientRepository;

    /**
     * Retrieves the list of all clients.
     *
     * @return List of all clients in the database
     */
    @GetMapping("/clients")
    public List<Client> findAll() {
        logger.info("Fetching all clients");
        return clientRepository.findAll();
    }

    /**
     * Retrieves a specific client by its identifier.
     *
     * @param id Unique identifier of the searched client
     * @return The client corresponding to the provided ID
     * @throws Exception If no client is found with this ID
     */
    @GetMapping("/client/{id}")
    public Client findById(@PathVariable Long id) throws Exception {
        logger.info("Fetching client with ID: {}", id);
        return clientRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Client not found with ID: {}", id);
                return new Exception("Client not found");
            });
    }
}
