package ma.projet.soapservice.entity;

/**
 * Enumeration representing the different types of bank accounts.
 * 
 * <p>Supported account types:</p>
 * <ul>
 *   <li><b>COURANT</b>: Checking account (Compte Courant)</li>
 *   <li><b>EPARGNE</b>: Savings account (Compte Épargne)</li>
 * </ul>
 * 
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0
 * @since 2025-01-01
 */
public enum TypeCompte {
    
    /**
     * Checking account - used for everyday transactions.
     */
    COURANT,
    
    /**
     * Savings account - used for saving money with potential interest.
     */
    EPARGNE
}
