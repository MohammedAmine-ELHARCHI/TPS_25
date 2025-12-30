package com.example.Voiture.controllers;

import com.example.Voiture.entities.Voiture;
import com.example.Voiture.repositories.VoitureRepository;
import com.example.Voiture.services.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for car (Voiture) management.
 *
 * <p>This class exposes REST endpoints for car operations. It uses
 * the VoitureRepository for database access and the ClientService
 * (Feign Client) to retrieve owner information from the Client service.</p>
 *
 * <p>Exposed Endpoints:</p>
 * <ul>
 *   <li>GET /voitures: Retrieves all cars with their owners</li>
 *   <li>GET /voiture/{id}: Retrieves a specific car by ID with its owner</li>
 *   <li>GET /voitures/client/{clientId}: Retrieves all cars owned by a specific client</li>
 * </ul>
 *
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0.0
 * @since 2025-12-09
 */
@RestController
public class VoitureController {

    private static final Logger logger = LoggerFactory.getLogger(VoitureController.class);

    @Autowired
    private VoitureRepository voitureRepository;

    @Autowired
    private ClientService clientService;

    /**
     * Retrieves the list of all cars, including owner details.
     *
     * @return List of all cars with populated client information
     */
    @GetMapping("/voitures")
    public List<Voiture> findAll() {
        logger.info("Fetching all cars with owner details");
        List<Voiture> cars = voitureRepository.findAll();
        cars.forEach(car -> {
            try {
                car.setClient(clientService.getClientById(car.getClientId()));
            } catch (Exception e) {
                logger.error("Error fetching client for car ID {}: {}", car.getId(), e.getMessage());
            }
        });
        return cars;
    }

    /**
     * Retrieves a specific car by its identifier.
     *
     * @param id Unique identifier of the car
     * @return The car with populated client information
     * @throws Exception If no car is found with this ID
     */
    @GetMapping("/voiture/{id}")
    public Voiture findById(@PathVariable Long id) throws Exception {
        logger.info("Fetching car with ID: {}", id);
        Voiture car = voitureRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Car not found with ID: {}", id);
                return new Exception("Car not found");
            });
        
        try {
            car.setClient(clientService.getClientById(car.getClientId()));
        } catch (Exception e) {
            logger.error("Error fetching client for car ID {}: {}", id, e.getMessage());
        }
        
        return car;
    }

    /**
     * Retrieves all cars belonging to a specific client.
     *
     * @param clientId Unique identifier of the client
     * @return List of cars owned by the specified client
     */
    @GetMapping("/voitures/client/{clientId}")
    public List<Voiture> findByClient(@PathVariable Long clientId) {
        logger.info("Fetching cars for client ID: {}", clientId);
        List<Voiture> cars = voitureRepository.findByClientId(clientId);
        cars.forEach(car -> {
            try {
                car.setClient(clientService.getClientById(car.getClientId()));
            } catch (Exception e) {
                logger.error("Error fetching client for car ID {}: {}", car.getId(), e.getMessage());
            }
        });
        return cars;
    }
}
