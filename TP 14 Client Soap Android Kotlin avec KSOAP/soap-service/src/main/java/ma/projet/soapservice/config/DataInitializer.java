package ma.projet.soapservice.config;

import ma.projet.soapservice.entity.Compte;
import ma.projet.soapservice.entity.TypeCompte;
import ma.projet.soapservice.repository.CompteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for initializing sample data in the database.
 * 
 * <p>This class creates a CommandLineRunner bean that populates the database
 * with test accounts when the application starts. Useful for development
 * and demonstration purposes.</p>
 * 
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0
 * @since 2025-01-01
 */
@Configuration
public class DataInitializer {
    
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    
    // Sample account balances
    private static final double CHECKING_ACCOUNT_BALANCE_1 = 2000.0;
    private static final double CHECKING_ACCOUNT_BALANCE_2 = 30007.0;
    private static final double SAVINGS_ACCOUNT_BALANCE = 45999.688;
    
    /**
     * Creates a CommandLineRunner that initializes the database with sample accounts.
     * 
     * <p>Creates three accounts:</p>
     * <ul>
     *   <li>Two checking accounts (COURANT)</li>
     *   <li>One savings account (EPARGNE)</li>
     * </ul>
     * 
     * @param repository The CompteRepository for saving accounts
     * @return CommandLineRunner that executes during application startup
     */
    @Bean
    CommandLineRunner initializeDatabase(CompteRepository repository) {
        return args -> {
            logger.info("Initializing database with sample accounts...");
            
            // Create sample checking accounts
            Compte checkingAccount1 = repository.save(
                new Compte(CHECKING_ACCOUNT_BALANCE_1, TypeCompte.COURANT)
            );
            logger.debug("Created checking account: {}", checkingAccount1);
            
            Compte checkingAccount2 = repository.save(
                new Compte(CHECKING_ACCOUNT_BALANCE_2, TypeCompte.COURANT)
            );
            logger.debug("Created checking account: {}", checkingAccount2);
            
            // Create sample savings account
            Compte savingsAccount = repository.save(
                new Compte(SAVINGS_ACCOUNT_BALANCE, TypeCompte.EPARGNE)
            );
            logger.debug("Created savings account: {}", savingsAccount);
            
            long accountCount = repository.count();
            logger.info("Database initialized with {} accounts", accountCount);
            
            // Log all accounts for verification
            logger.info("All accounts in database:");
            repository.findAll().forEach(account -> 
                logger.info("  - {}", account)
            );
        };
    }
}
