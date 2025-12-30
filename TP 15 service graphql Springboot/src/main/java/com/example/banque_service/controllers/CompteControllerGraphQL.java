package com.example.banque_service.controllers;

import com.example.banque_service.dto.TransactionRequest;
import com.example.banque_service.entities.Compte;
import com.example.banque_service.entities.Transaction;
import com.example.banque_service.enums.TypeTransaction;
import com.example.banque_service.repositories.CompteRepository;
import com.example.banque_service.repositories.TransactionRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * GraphQL controller for managing bank accounts and transactions.
 * 
 * <p>Provides GraphQL queries and mutations for:</p>
 * <ul>
 *   <li>Account CRUD operations</li>
 *   <li>Transaction recording and retrieval</li>
 *   <li>Account and transaction statistics</li>
 * </ul>
 * 
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0
 * @since 2025-01-01
 */
@Controller
@AllArgsConstructor
public class CompteControllerGraphQL {
    
    private static final Logger logger = LoggerFactory.getLogger(CompteControllerGraphQL.class);
    private static final String DATE_FORMAT_PATTERN = "yyyy/MM/dd";
    private static final String ACCOUNT_NOT_FOUND_MESSAGE = "Account with ID %d not found";
    
    private final CompteRepository compteRepository;
    private final TransactionRepository transactionRepository;
    
    /**
     * Retrieves all accounts.
     * 
     * @return List of all Compte entities
     */
    @QueryMapping
    public List<Compte> allAccounts() {
        logger.debug("Fetching all accounts");
        List<Compte> accounts = compteRepository.findAll();
        logger.info("Retrieved {} accounts", accounts.size());
        return accounts;
    }
    
    /**
     * Retrieves a single account by its ID.
     * 
     * @param id The account ID
     * @return The Compte entity
     * @throws RuntimeException if account not found
     */
    @QueryMapping
    public Compte accountById(@Argument Long id) {
        logger.debug("Fetching account with ID: {}", id);
        return compteRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Account not found: ID={}", id);
                    return new RuntimeException(String.format(ACCOUNT_NOT_FOUND_MESSAGE, id));
                });
    }
    
    /**
     * Creates or updates an account.
     * 
     * @param compte The account data to save
     * @return The saved Compte entity
     */
    @MutationMapping
    public Compte saveAccount(@Argument Compte compte) {
        logger.info("Saving account: {}", compte);
        Compte savedAccount = compteRepository.save(compte);
        logger.info("Account saved successfully: ID={}", savedAccount.getId());
        return savedAccount;
    }
    
    /**
     * Calculates total balance statistics across all accounts.
     * 
     * @return Map containing count, sum, and average of all account balances
     */
    @QueryMapping
    public Map<String, Object> totalBalanceStats() {
        logger.debug("Calculating total balance statistics");
        
        long accountCount = compteRepository.count(); 
        double totalBalance = compteRepository.sumBalances();
        double averageBalance = accountCount > 0 ? totalBalance / accountCount : 0;
        
        logger.info("Balance stats: count={}, sum={}, average={}", accountCount, totalBalance, averageBalance);
        
        return Map.of(
            "count", accountCount,
            "sum", totalBalance,
            "average", averageBalance
        );
    }
    
    // ==================== Transaction Operations ====================
    
    /**
     * Records a new transaction for an account.
     * 
     * @param transactionRequest The transaction data
     * @return The created Transaction entity
     * @throws RuntimeException if account not found or date format invalid
     */
    @MutationMapping
    public Transaction addTransaction(@Argument TransactionRequest transactionRequest) {
        logger.info("Adding transaction: {}", transactionRequest);
        
        Compte account = compteRepository.findById(transactionRequest.getAccountId())
            .orElseThrow(() -> {
                logger.error("Cannot add transaction: account {} not found", transactionRequest.getAccountId());
                return new RuntimeException(String.format(ACCOUNT_NOT_FOUND_MESSAGE, transactionRequest.getAccountId()));
            });
        
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transactionRequest.getAmount());
        newTransaction.setTransactionType(transactionRequest.getTransactionType());
        newTransaction.setAccount(account);
        
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
            dateFormat.setLenient(false);
            Date parsedDate = dateFormat.parse(transactionRequest.getDate());
            newTransaction.setTransactionDate(parsedDate);
        } catch (ParseException e) {
            logger.error("Invalid date format: {}. Expected: {}", transactionRequest.getDate(), DATE_FORMAT_PATTERN);
            throw new RuntimeException("Invalid date format. Use " + DATE_FORMAT_PATTERN);
        }
        
        Transaction savedTransaction = transactionRepository.save(newTransaction);
        logger.info("Transaction added successfully: ID={}", savedTransaction.getId());
        return savedTransaction;
    }
    
    /**
     * Retrieves all transactions for a specific account.
     * 
     * @param id The account ID
     * @return List of transactions for the account
     * @throws RuntimeException if account not found
     */
    @QueryMapping
    public List<Transaction> accountTransactions(@Argument Long id) {
        logger.debug("Fetching transactions for account ID: {}", id);
        
        Compte account = compteRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Cannot fetch transactions: account {} not found", id);
                return new RuntimeException(String.format(ACCOUNT_NOT_FOUND_MESSAGE, id));
            });
        
        List<Transaction> transactions = transactionRepository.findByAccount(account);
        logger.info("Retrieved {} transactions for account {}", transactions.size(), id);
        return transactions;
    }
    
    /**
     * Retrieves all transactions in the system.
     * 
     * @return List of all Transaction entities
     */
    @QueryMapping
    public List<Transaction> allTransactions() {
        logger.debug("Fetching all transactions");
        List<Transaction> transactions = transactionRepository.findAll();
        logger.info("Retrieved {} transactions", transactions.size());
        return transactions;
    }
    
    /**
     * Calculates transaction statistics grouped by type.
     * 
     * @return Map containing total count, sum of deposits, and sum of withdrawals
     */
    @QueryMapping
    public Map<String, Object> transactionStats() {
        logger.debug("Calculating transaction statistics");
        
        long totalCount = transactionRepository.count();
        double totalDeposits = transactionRepository.sumByType(TypeTransaction.DEPOT);
        double totalWithdrawals = transactionRepository.sumByType(TypeTransaction.RETRELHARCHI);
        
        logger.info("Transaction stats: count={}, deposits={}, withdrawals={}", 
                    totalCount, totalDeposits, totalWithdrawals);
        
        return Map.of(
            "count", totalCount,
            "sumDeposits", totalDeposits,
            "sumWithdrawals", totalWithdrawals
        );
    }
}
