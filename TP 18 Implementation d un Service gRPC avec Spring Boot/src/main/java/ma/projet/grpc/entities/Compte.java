package ma.projet.grpc.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

/**
 * Entity representing a bank account for gRPC service.
 * 
 * <p>This entity is mapped to the database and used in gRPC service operations.
 * Account types supported: COURANT (checking) and EPARGNE (savings).</p>
 * 
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0
 * @since 2025-01-01
 */
@Entity
public class Compte {
    
    /**
     * Unique identifier (UUID) for the account.
     * Auto-generated using UUID strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    /**
     * Current account balance.
     */
    @Column(nullable = false)
    private float balance;
    
    /**
     * Account creation date in ISO format (YYYY-MM-DD).
     */
    @Column(nullable = false)
    private String creationDate;
    
    /**
     * Account type: COURANT (checking) or EPARGNE (savings).
     */
    @Column(nullable = false)
    private String accountType;

    /**
     * Default constructor required by JPA.
     */
    public Compte() {
    }

    /**
     * Parameterized constructor for creating account with all fields.
     * 
     * @param id Unique identifier  
     * @param balance Initial balance
     * @param creationDate Creation date
     * @param accountType Type of account (COURANT or EPARGNE)
     */
    public Compte(String id, float balance, String creationDate, String accountType) {
        this.id = id;
        this.balance = balance;
        this.creationDate = creationDate;
        this.accountType = accountType;
    }

    // Getters and Setters
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "Compte{" +
                "id='" + id + '\'' +
                ", balance=" + balance +
                ", creationDate='" + creationDate + '\'' +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}
