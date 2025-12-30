package com.example.banque_service.repositories;

import com.example.banque_service.entities.Compte;
import com.example.banque_service.entities.Transaction;
import com.example.banque_service.enums.TypeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Transaction entity persistence.
 * 
 * <p>Provides transaction-specific queries including:</p>
 * <ul>
 *   <li>Finding transactions by account</li>
 *   <li>Aggregating transaction amounts by type</li>
 * </ul>
 * 
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0
 * @since 2025-01-01
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    /**
     * Finds all transactions associated with a specific account.
     * 
     * @param account The account to find transactions for
     * @return List of transactions for the specified account
     */
    List<Transaction> findByAccount(Compte account);
    
    /**
     * Calculates the sum of transaction amounts for a specific transaction type.
     * 
     * <p>Uses COALESCE to return 0 when no transactions of the specified type exist,
     * preventing null pointer issues.</p>
     * 
     * @param transactionType The type of transaction to sum (DEPOT or RETRELHARCHI)
     * @return Total amount for the specified transaction type, or 0 if none exist
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.transactionType = :type")
    double sumByType(@Param("type") TypeTransaction transactionType);
}
