package com.example.banque_service.dto;

import com.example.banque_service.enums.TypeTransaction;
import lombok.Data;

/**
 * Data Transfer Object for transaction creation requests.
 * 
 * <p>Captures transaction details submitted through GraphQL mutations.
 * Requires validation at the service layer before processing.</p>
 * 
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0
 * @since 2025-01-01
 */
@Data
public class TransactionRequest {
    
    /** ID of the account this transaction belongs to */
    private Long accountId;
    
    /** Transaction amount (positive for deposits, negative for withdrawals conceptually) */
    private double amount;
    
    /** Transaction date in format yyyy/MM/dd */
    private String date;
    
    /** Type of transaction (DEPOT or RETRELHARCHI) */
    private TypeTransaction transactionType;
}
