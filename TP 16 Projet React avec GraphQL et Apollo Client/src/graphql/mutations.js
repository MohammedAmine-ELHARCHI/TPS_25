import { gql } from '@apollo/client';

/**
 * GraphQL Mutations for Banking Application.
 * Defines all mutation operations for modifying data on the GraphQL server.
 */

/** Mutation to create or update a bank account */
export const SAVE_COMPTE = gql`
  mutation SaveAccount($compte: CompteRequest!) {
    saveCompte(compte: $compte) {
      id
      solde
      dateCreation
      type
    }
  }
`;

/**
 * Mutation to delete an account.
 * Returns boolean indicating success.
 */
export const DELETE_COMPTE = gql`
  mutation DeleteAccount($id: ID!) {
    deleteCompte(id: $id)
  }
`;

/**
 * Mutation to add a new transaction (deposit or withdrawal).
 * Returns the created transaction with updated account balance.
 */
export const ADD_TRANSACTION = gql`
  mutation AddTransaction($transactionRequest: TransactionRequest!) {
    addTransaction(transactionRequest: $transactionRequest) {
      id
      type
      montant
      date
      compte {
        id
        solde
        type
      }
    }
  }
`;
