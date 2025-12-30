package ma.projet.soapservice.entity;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Entity representing a bank account (Compte) in the system.
 * This class is mapped to the "comptes" table in the database.
 * 
 * <p>Each account has a balance, creation date, and type (COURANT or EPARGNE).
 * The creation date is automatically set to the current date when the account is created.</p>
 * 
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0
 * @since 2025-01-01
 */
@Entity
@Table(name = "comptes")
public class Compte {
    
    /**
     * Unique identifier for the account.
     * Auto-generated using database identity strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Current account balance.
     * Cannot be null. Should be non-negative for most business rules.
     */
    @Column(nullable = false)
    private Double balance;
    
    /**
     * Date when the account was created.
     * Automatically set to current date upon instantiation.
     */
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    
    /**
     * Type of account (COURANT for checking, EPARGNE for savings).
     * Stored as string in database.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeCompte accountType;
    
    /**
     * Default constructor required by JPA.
     * Initializes creation date to current date.
     */
    public Compte() {
        this.creationDate = new Date();
    }
    
    /**
     * Parameterized constructor for creating an account with initial balance and type.
     * 
     * @param balance Initial account balance (should be non-negative)
     * @param accountType Type of account (COURANT or EPARGNE)
     */
    public Compte(Double balance, TypeCompte accountType) {
        this.balance = balance;
        this.accountType = accountType;
        this.creationDate = new Date();
    }
    
    // Getters and Setters
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Double getBalance() {
        return balance;
    }
    
    public void setBalance(Double balance) {
        this.balance = balance;
    }
    
    /**
     * Returns a defensive copy of the creation date to prevent external modification.
     * 
     * @return Copy of the account creation date
     */
    public Date getCreationDate() {
        return creationDate != null ? new Date(creationDate.getTime()) : null;
    }
    
    /**
     * Sets the creation date using a defensive copy.
     * 
     * @param creationDate The creation date to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate != null ? new Date(creationDate.getTime()) : null;
    }
    
    public TypeCompte getAccountType() {
        return accountType;
    }
    
    public void setAccountType(TypeCompte accountType) {
        this.accountType = accountType;
    }
    
    @Override
    public String toString() {
        return "Compte{" +
                "id=" + id +
                ", balance=" + balance +
                ", creationDate=" + creationDate +
                ", accountType=" + accountType +
                '}';
    }
}
