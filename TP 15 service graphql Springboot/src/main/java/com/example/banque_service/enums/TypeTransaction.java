package com.example.banque_service.enums;

/**
 * Enumeration for transaction types.
 * 
 * <p>Transactions can be either deposits or withdrawals:</p>
 * <ul>
 *   <li><b>DEPOT</b>: Money added to account (deposit)</li>
 *   <li><b>RETRELHARCHI</b>: Money removed from account (withdrawal)</li>
 * </ul>
 * 
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0
 * @since 2025-01-01
 */
public enum TypeTransaction {
    
    /** Deposit transaction - adds funds to account */
    DEPOT,
    
    /** Withdrawal transaction - removes funds from account */
    RETRELHARCHI
}
