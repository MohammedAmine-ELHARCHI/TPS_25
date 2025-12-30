package com.example.Voiture.repositories;

import com.example.Voiture.entities.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for the Voiture entity.
 *
 * <p>This interface extends JpaRepository to provide standard database
 * operations (CRUD) on the Voiture entity. It also includes a custom
 * query to find cars by client ID.</p>
 *
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0.0
 * @since 2025-12-09
 */
@Repository
public interface VoitureRepository extends JpaRepository<Voiture, Long> {

    /**
     * Finds all cars belonging to a specific client.
     *
     * @param clientId The unique identifier of the client
     * @return List of cars owned by the client
     */
    @Query("SELECT v FROM Voiture v WHERE v.clientId = :clientId")
    List<Voiture> findByClientId(@Param("clientId") Long clientId);
}
