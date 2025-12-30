package com.example.banque_service.entities;

import com.example.banque_service.enums.TypeCompte;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entity representing a bank account in the GraphQL banking service.
 * 
 * <p>Accounts have a balance, creation date, and type (COURANT or EPARGNE).
 * This entity is exposed through GraphQL queries and mutations.</p>
 * 
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0
 * @since 2025-01-01
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compte {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "balance")
    private double balance;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "creation_date")
    private Date creationDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private TypeCompte accountType;
}
