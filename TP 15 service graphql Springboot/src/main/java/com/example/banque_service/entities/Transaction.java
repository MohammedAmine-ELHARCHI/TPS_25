package com.example.banque_service.entities;

import com.example.banque_service.enums.TypeTransaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entity representing a financial transaction for a bank account.
 * 
 * <p>Transactions record deposits (DEPOT) or withdrawals (RETRELHARCHI) with
 * an amount, date, and reference to the associated account.</p>
 * 
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0
 * @since 2025-01-01
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "amount")
    private double amount;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "transaction_date")
    private Date transactionDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TypeTransaction transactionType;
    
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Compte account;
}
