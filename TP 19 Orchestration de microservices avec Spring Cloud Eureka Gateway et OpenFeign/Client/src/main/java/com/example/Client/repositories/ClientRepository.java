package com.example.Client.repositories;

import com.example.Client.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for the Client entity.
 *
 * <p>This interface extends JpaRepository to provide standard database
 * operations (CRUD) on the Client entity. Spring Data JPA automatically
 * generates the implementation at runtime.</p>
 *
 * <p>Automatically available operations:</p>
 * <ul>
 *   <li>save(Client): Save a client</li>
 *   <li>findById(Long): Find a client by ID</li>
 *   <li>findAll(): Retrieve all clients</li>
 *   <li>deleteById(Long): Delete a client by ID</li>
 *   <li>count(): Count the number of clients</li>
 *   <li>existsById(Long): Check if a client exists</li>
 * </ul>
 *
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0.0
 * @since 2025-12-09
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
