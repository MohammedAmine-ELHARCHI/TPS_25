package ma.projet.grpc.controllers;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import ma.projet.grpc.services.CompteService;
import ma.projet.grpc.stubs.*;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * gRPC service implementation for account management operations.
 * 
 * <p>This controller handles all gRPC requests for account operations including:
 * - Retrieving all accounts or specific account by ID
 * - Creating new accounts
 * - Calculating balance statistics across all accounts
 * </p>
 * 
 * <p>The service translates between JPA entities and Protobuf messages,
 * and uses StreamObserver pattern for asynchronous responses.</p>
 * 
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0
 * @since 2025-01-01
 */
@GrpcService
public class CompteServiceImpl extends CompteServiceGrpc.CompteServiceImplBase {
    
    private static final Logger logger = LoggerFactory.getLogger(CompteServiceImpl.class);
    private static final String ACCOUNT_NOT_FOUND_MSG = "Account not found with ID: %s";
    
    private final CompteService compteService;

    public CompteServiceImpl(CompteService compteService) {
        this.compteService = compteService;
    }

    /**
     * Retrieves all accounts from the database.
     * 
     * @param request Empty request (no parameters needed)
     * @param responseObserver Stream observer for sending response
     */
    @Override
    public void allComptes(GetAllComptesRequest request, StreamObserver<GetAllComptesResponse> responseObserver) {
        try {
            logger.info("gRPC request: getAllAccounts");
            
            List<Compte> grpcAccounts = compteService.findAllComptes().stream()
                    .map(this::convertToGrpcCompte)
                    .collect(Collectors.toList());

            GetAllComptesResponse response = GetAllComptesResponse.newBuilder()
                    .addAllComptes(grpcAccounts)
                    .build();
                    
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
            logger.info("Returned {} accounts", grpcAccounts.size());
        } catch (Exception e) {
            logger.error("Error fetching all accounts", e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error fetching accounts: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    /**
     * Finds a specific account by ID.
     * 
     * @param request Request containing account ID
     * @param responseObserver Stream observer for sending response
     */
    @Override
    public void compteById(GetCompteByIdRequest request, StreamObserver<GetCompteByIdResponse> responseObserver) {
        try {
            String accountId = request.getId();
            logger.info("gRPC request: getAccountById with ID: {}", accountId);
            
            ma.projet.grpc.entities.Compte account = compteService.findCompteById(accountId);

            if (account != null) {
                Compte grpcAccount = convertToGrpcCompte(account);
                
                GetCompteByIdResponse response = GetCompteByIdResponse.newBuilder()
                        .setCompte(grpcAccount)
                        .build();
                        
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                logger.info("Account found and returned: {}", accountId);
            } else {
                logger.warn("Account not found: {}", accountId);
                responseObserver.onError(Status.NOT_FOUND
                        .withDescription(String.format(ACCOUNT_NOT_FOUND_MSG, accountId))
                        .asRuntimeException());
            }
        } catch (Exception e) {
            logger.error("Error fetching account by ID", e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error fetching account: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    /**
     * Calculates balance statistics across all accounts.
     * 
     * @param request Empty request (no parameters needed)
     * @param responseObserver Stream observer for sending response
     */
    @Override
    public void totalSolde(GetTotalSoldeRequest request, StreamObserver<GetTotalSoldeResponse> responseObserver) {
        try {
            logger.info("gRPC request: getTotalBalanceStats");
            
            List<ma.projet.grpc.entities.Compte> accounts = compteService.findAllComptes();
            int accountCount = accounts.size();
            
            // Calculate sum using stream for better readability
            float totalBalance = (float) accounts.stream()
                    .mapToDouble(ma.projet.grpc.entities.Compte::getBalance)
                    .sum();
            
            float averageBalance = accountCount > 0 ? totalBalance / accountCount : 0;

            SoldeStats stats = SoldeStats.newBuilder()
                    .setCount(accountCount)
                    .setSum(totalBalance)
                    .setAverage(averageBalance)
                    .build();

            GetTotalSoldeResponse response = GetTotalSoldeResponse.newBuilder()
                    .setStats(stats)
                    .build();
                    
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
            logger.info("Balance stats calculated: count={}, sum={}, avg={}", 
                       accountCount, totalBalance, averageBalance);
        } catch (Exception e) {
            logger.error("Error calculating balance statistics", e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error calculating stats: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    /**
     * Creates a new account.
     * 
     * @param request Request containing account details
     * @param responseObserver Stream observer for sending response
     */
    @Override
    public void saveCompte(SaveCompteRequest request, StreamObserver<SaveCompteResponse> responseObserver) {
        try {
            Compte grpcAccountRequest = request.getCompte();
            logger.info("gRPC request: saveAccount with balance: {}", grpcAccountRequest.getSolde());

            // Create JPA entity from Protobuf message
            ma.projet.grpc.entities.Compte account = new ma.projet.grpc.entities.Compte();
            account.setBalance(grpcAccountRequest.getSolde());
            account.setCreationDate(grpcAccountRequest.getDateCreation());
            account.setAccountType(grpcAccountRequest.getType().name());

            // Save to database
            ma.projet.grpc.entities.Compte savedAccount = compteService.saveCompte(account);

            // Convert back to Protobuf message
            Compte grpcAccountResponse = convertToGrpcCompte(savedAccount);

            SaveCompteResponse response = SaveCompteResponse.newBuilder()
                    .setCompte(grpcAccountResponse)
                    .build();
                    
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
            logger.info("Account created successfully with ID: {}", savedAccount.getId());
        } catch (Exception e) {
            logger.error("Error saving account", e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error saving account: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    /**
     * Converts a JPA Compte entity to a Protobuf Compte message.
     * Helper method to reduce code duplication.
     * 
     * @param account JPA entity to convert
     * @return Protobuf Compte message
     */
    private Compte convertToGrpcCompte(ma.projet.grpc.entities.Compte account) {
        return Compte.newBuilder()
                .setId(account.getId())
                .setSolde(account.getBalance())
                .setDateCreation(account.getCreationDate())
                .setType(TypeCompte.valueOf(account.getAccountType()))
                .build();
    }
}
