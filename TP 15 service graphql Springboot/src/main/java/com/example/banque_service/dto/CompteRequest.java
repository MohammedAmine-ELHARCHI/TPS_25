package com.example.banque_service.dto;

import com.example.banque_service.enums.TypeCompte;
import lombok.Data;

/**
 * Data Transfer Object for creating or updating account requests.
 * 
 * <p>Used in GraphQL mutations to capture account creation data.
 * Fields are validated at the service layer.</p>
 * 
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0
 * @since 2025-01-01
 */
@Data
public class CompteRequest {
    
    /** Initial balance for the account */
    private double balance;
    
    /** Creation date in format yyyy/MM/dd */
    private String creationDate;
    
    /** Type of account (COURANT or EPARGNE) */
    private TypeCompte accountType;
}
