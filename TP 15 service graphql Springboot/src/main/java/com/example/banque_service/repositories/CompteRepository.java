package com.example.banque_service.repositories;

import com.example.banque_service.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Compte (Account) entity persistence.
 * 
 * <p>Provides standard CRUD operations and custom queries for account management.
 * Supports balance aggregation across all accounts.</p>
 * 
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0
 * @since 2025-01-01
 */
@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {
    
    /**
     * Calculates the sum of all account balances in the system.
     * 
     * <p>Uses JPQL aggregation to compute total across all accounts.
     * Returns 0 if no accounts exist.</p>
     * 
     * @return Total sum of all account balances
     */
    @Query("SELECT COALESCE(SUM(c.balance), 0) FROM Compte c")
    double sumBalances();
}
