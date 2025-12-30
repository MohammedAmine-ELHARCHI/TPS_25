package ma.projet.soapservice.ws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import ma.projet.soapservice.entity.Compte;
import ma.projet.soapservice.entity.TypeCompte;
import ma.projet.soapservice.repository.CompteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * SOAP Web Service for managing bank accounts (Comptes).
 * 
 * <p>This service provides CRUD operations and type-based querying for accounts.
 * All methods are exposed as SOAP operations and can be invoked by SOAP clients.</p>
 * 
 * <p>Service operations include:</p>
 * <ul>
 *   <li>Retrieving all accounts or a single account by ID</li>
 *   <li>Creating new accounts</li>
 *   <li>Updating existing accounts</li>
 *   <li>Deleting accounts</li>
 *   <li>Querying accounts by type (COURANT or EPARGNE)</li>
 * </ul>
 * 
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0
 * @since 2025-01-01
 */
@Component
@WebService(serviceName = "CompteWS")
public class CompteService {
    
    private static final Logger logger = LoggerFactory.getLogger(CompteService.class);
    
    @Autowired
    private CompteRepository compteRepository;
    
    /**
     * Retrieves all accounts from the database.
     * 
     * @return List of all Compte entities, may be empty but never null
     */
    @WebMethod(operationName = "getAllAccounts")
    public List<Compte> getAllAccounts() {
        logger.info("Fetching all accounts");
        List<Compte> accounts = compteRepository.findAll();
        logger.info("Retrieved {} accounts", accounts.size());
        return accounts;
    }
    
    /**
     * Retrieves a single account by its unique identifier.
     * 
     * @param id The account ID to search for
     * @return The Compte entity if found, null otherwise
     */
    @WebMethod(operationName = "getAccountById")
    public Compte getAccountById(@WebParam(name = "id") Long id) {
        logger.info("Fetching account with ID: {}", id);
        
        if (id == null) {
            logger.warn("Attempted to fetch account with null ID");
            return null;
        }
        
        Optional<Compte> account = compteRepository.findById(id);
        if (account.isPresent()) {
            logger.info("Account found: {}", account.get());
            return account.get();
        } else {
            logger.warn("No account found with ID: {}", id);
            return null;
        }
    }
    
    /**
     * Creates a new account with the specified balance and type.
     * 
     * @param balance Initial balance for the account (recommended non-negative)
     * @param accountType Type of account as string ("COURANT" or "EPARGNE")
     * @return The newly created Compte entity, or null if creation failed
     */
    @WebMethod(operationName = "createAccount")
    public Compte createAccount(
            @WebParam(name = "balance") Double balance,
            @WebParam(name = "accountType") String accountType) {
        logger.info("Creating new account: balance={}, type={}", balance, accountType);
        
        if (balance == null || accountType == null || accountType.trim().isEmpty()) {
            logger.error("Invalid input: balance and accountType are required");
            return null;
        }
        
        try {
            TypeCompte type = TypeCompte.valueOf(accountType.toUpperCase().trim());
            Compte newAccount = new Compte(balance, type);
            Compte savedAccount = compteRepository.save(newAccount);
            logger.info("Account created successfully: {}", savedAccount);
            return savedAccount;
        } catch (IllegalArgumentException e) {
            logger.error("Invalid account type provided: '{}'. Must be COURANT or EPARGNE", accountType);
            return null;
        } catch (Exception e) {
            logger.error("Unexpected error creating account", e);
            return null;
        }
    }
    
    /**
     * Updates an existing account's balance and type.
     * 
     * @param id The ID of the account to update
     * @param balance New balance for the account
     * @param accountType New account type as string ("COURANT" or "EPARGNE")
     * @return true if update was successful, false otherwise
     */
    @WebMethod(operationName = "updateAccount")
    public boolean updateAccount(
            @WebParam(name = "id") Long id,
            @WebParam(name = "balance") Double balance,
            @WebParam(name = "accountType") String accountType) {
        logger.info("Updating account: id={}, balance={}, type={}", id, balance, accountType);
        
        if (id == null || balance == null || accountType == null) {
            logger.error("Invalid input: all parameters are required for update");
            return false;
        }
        
        try {
            Optional<Compte> existingAccountOpt = compteRepository.findById(id);
            
            if (existingAccountOpt.isEmpty()) {
                logger.warn("Cannot update: account with ID {} not found", id);
                return false;
            }
            
            Compte existingAccount = existingAccountOpt.get();
            TypeCompte type = TypeCompte.valueOf(accountType.toUpperCase().trim());
            
            existingAccount.setBalance(balance);
            existingAccount.setAccountType(type);
            
            compteRepository.save(existingAccount);
            logger.info("Account updated successfully: {}", existingAccount);
            return true;
        } catch (IllegalArgumentException e) {
            logger.error("Invalid account type: '{}'", accountType);
            return false;
        } catch (Exception e) {
            logger.error("Unexpected error updating account with ID: {}", id, e);
            return false;
        }
    }
    
    /**
     * Deletes an account by its ID.
     * 
     * @param id The ID of the account to delete
     * @return true if deletion was successful, false if account not found or deletion failed
     */
    @WebMethod(operationName = "deleteAccount")
    public boolean deleteAccount(@WebParam(name = "id") Long id) {
        logger.info("Attempting to delete account with ID: {}", id);
        
        if (id == null) {
            logger.error("Cannot delete: null ID provided");
            return false;
        }
        
        try {
            if (!compteRepository.existsById(id)) {
                logger.warn("Cannot delete: account with ID {} not found", id);
                return false;
            }
            
            compteRepository.deleteById(id);
            logger.info("Account deleted successfully: ID={}", id);
            return true;
        } catch (Exception e) {
            logger.error("Error deleting account with ID: {}", id, e);
            return false;
        }
    }
    
    /**
     * Retrieves all accounts of a specific type.
     * 
     * @param accountType Type of accounts to retrieve ("COURANT" or "EPARGNE")
     * @return List of Compte entities matching the type, empty list if none found or invalid type
     */
    @WebMethod(operationName = "getAccountsByType")
    public List<Compte> getAccountsByType(@WebParam(name = "accountType") String accountType) {
        logger.info("Fetching accounts of type: {}", accountType);
        
        if (accountType == null || accountType.trim().isEmpty()) {
            logger.error("Invalid input: accountType cannot be null or empty");
            return Collections.emptyList();
        }
        
        try {
            TypeCompte type = TypeCompte.valueOf(accountType.toUpperCase().trim());
            List<Compte> accounts = compteRepository.findByType(type);
            logger.info("Retrieved {} accounts of type {}", accounts.size(), type);
            return accounts;
        } catch (IllegalArgumentException e) {
            logger.error("Invalid account type: '{}'. Must be COURANT or EPARGNE", accountType);
            return Collections.emptyList();
        }
    }
}
