package com.example.banque_service.exceptions;

import graphql.ErrorClassification;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Global exception handler for GraphQL operations.
 * 
 * <p>Converts Java exceptions into GraphQL errors with appropriate
 * formatting and logging. Ensures consistent error responses across
 * all GraphQL queries and mutations.</p>
 * 
 * @author MOHAMMED AMINE ELHARCHI
 * @version 1.0
 * @since 2025-01-01
 */
@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {
    
    private static final Logger logger = LoggerFactory.getLogger(GraphQLExceptionHandler.class);
    
    /**
     * Resolves exceptions thrown during GraphQL data fetching.
     * 
     * <p>Converts runtime exceptions into properly formatted GraphQL errors.
     * Logs the exception for debugging and monitoring purposes.</p>
     * 
     * @param exception The exception that occurred during data fetching
     * @param environment The GraphQL data fetching environment context
     * @return GraphQLError representation of the exception
     */
    @Override
    protected GraphQLError resolveToSingleError(Throwable exception, DataFetchingEnvironment environment) {
        logger.error("GraphQL error in {}: {}", 
                    environment.getField().getName(), 
                    exception.getMessage(), 
                    exception);
        
        return new GraphQLError() {
            @Override
            public String getMessage() {
                return exception.getMessage();
            }
            
            @Override
            public List<SourceLocation> getLocations() {
                return environment.getField().getSourceLocation() != null 
                    ? List.of(environment.getField().getSourceLocation())
                    : null;
            }
            
            @Override
            public ErrorClassification getErrorType() {
                return ErrorType.DataFetchingException;
            }
        };
    }
}
