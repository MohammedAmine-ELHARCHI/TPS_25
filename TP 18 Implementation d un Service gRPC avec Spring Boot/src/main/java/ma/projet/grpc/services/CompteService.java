package ma.projet.grpc.services;

import ma.projet.grpc.entities.Compte;
import ma.projet.grpc.repositories.CompteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for managing bank account operations.
 * 
 * <p>Provides business logic for account management including CRUD operations
 * and statistical queries. This service is used by the gRPC controller layer.</p>
 * 
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0
 * @since 2025-01-01
 */
@Service
public class CompteService {
    
    private static final Logger logger = LoggerFactory.getLogger(CompteService.class);
    
    private final CompteRepository compteRepository;

    public CompteService(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    /**
     * Retrieves all accounts from the database.
     * 
     * @return List of all Compte entities
     */
    public List<Compte> findAllComptes() {
        logger.debug("Fetching all accounts");
        List<Compte> accounts = compteRepository.findAll();
        logger.info("Retrieved {} accounts", accounts.size());
        return accounts;
    }

    /**
     * Finds a specific account by its ID.
     * 
     * @param id The account ID to search for
     * @return The Compte entity if found, null otherwise
     */
    public Compte findCompteById(String id) {
        logger.debug("Fetching account with ID: {}", id);
        return compteRepository.findById(id)
                .map(account -> {
                    logger.info("Account found: {}", id);
                    return account;
                })
                .orElseGet(() -> {
                    logger.warn("Account not found: {}", id);
                    return null;
                });
    }

    /**
     * Saves an account (creates new or updates existing).
     * 
     * @param compte The account to save
     * @return The saved Compte entity
     */
    public Compte saveCompte(Compte compte) {
        boolean isNew = compte.getId() == null;
        logger.info("{} account with balance: {}", 
                    isNew ? "Creating" : "Updating", 
                    compte.getBalance());
        
        Compte savedAccount = compteRepository.save(compte);
        logger.info("Account saved successfully with ID: {}", savedAccount.getId());
        return savedAccount;
    }

    /**
     * Deletes an account by its ID.
     * 
     * @param id The ID of the account to delete
     */
    public void deleteCompte(String id) {
        logger.info("Deleting account with ID: {}", id);
        if (compteRepository.existsById(id)) {
            compteRepository.deleteById(id);
            logger.info("Account deleted successfully: {}", id);
        } else {
            logger.warn("Cannot delete: account not found with ID: {}", id);
        }
    }

    /**
     * Counts the total number of accounts.
     * 
     * @return Total count of accounts in the database
     */
    public long countComptes() {
        long count = compteRepository.count();
        logger.debug("Total accounts count: {}", count);
        return count;
    }
}
