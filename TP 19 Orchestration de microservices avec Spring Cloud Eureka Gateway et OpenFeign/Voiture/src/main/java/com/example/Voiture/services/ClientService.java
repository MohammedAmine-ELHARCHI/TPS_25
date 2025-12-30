package com.example.Voiture.services;

import com.example.Voiture.models.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign Client for communicating with the Client Service.
 *
 * <p>This interface defines the contract for calling the Client microservice
 * endpoints. Spring Cloud OpenFeign generates the implementation at runtime.</p>
 *
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0.0
 * @since 2025-12-09
 */
@FeignClient(name = "SERVICE-CLIENT")
public interface ClientService {

    /**
     * Retrieves a client by its ID from the Client Service.
     *
     * @param id The unique identifier of the client
     * @return The Client model retrieved from the service
     */
    @GetMapping(path = "/client/{id}")
    Client getClientById(@PathVariable Long id);
}
