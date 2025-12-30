package ma.projet.soapservice.repository;

import ma.projet.soapservice.entity.Compte;
import ma.projet.soapservice.entity.TypeCompte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Compte entities.
 * 
 * <p>Provides standard CRUD operations through JpaRepository and custom
 * query methods for account-specific searches.</p>
 * 
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0
 * @since 2025-01-01
 */
@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {
    
    /**
     * Finds all accounts of a specific type.
     * 
     * @param accountType The type of accounts to search for (COURANT or EPARGNE)
     * @return List of accounts matching the specified type
     */
    List<Compte> findByAccountType(TypeCompte accountType);
    
    /**
     * Finds all accounts with a balance greater than the specified amount.
     * 
     * @param balance The minimum balance threshold
     * @return List of accounts with balance exceeding the threshold
     */
    List<Compte> findByBalanceGreaterThan(Double balance);
}
