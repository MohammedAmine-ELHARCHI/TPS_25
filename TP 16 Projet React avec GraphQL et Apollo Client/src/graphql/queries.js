import { gql } from '@apollo/client';

/**
 * GraphQL Queries for Banking Application.
 * Defines all query operations for fetching data from the GraphQL server.
 */

/** Query to retrieve all bank accounts */
export const GET_ALL_COMPTES = gql`
  query GetAllAccounts {
    allComptes {
      id
      solde
      dateCreation
      type
    }
  }
`;

/** Query to retrieve a single account by ID */
export const GET_COMPTE_BY_ID = gql`
  query GetAccountById($id: ID!) {
    compteById(id: $id) {
      id
      solde
      dateCreation
      type
    }
  }
`;

/** Query to calculate total balance statistics across all accounts */
export const GET_TOTAL_SOLDE = gql`
  query GetTotalBalanceStats {
    totalSolde {
      count
      sum
      average
    }
  }
`;

/** Query to retrieve accounts filtered by type (COURANT or EPARGNE) */
export const GET_COMPTE_BY_TYPE = gql`
  query GetAccountsByType($type: TypeCompte!) {
    findCompteByType(type: $type) {
      id
      solde
      dateCreation
      type
    }
  }
`;

/** Query to retrieve all transactions for a specific account */
export const GET_COMPTE_TRANSACTIONS = gql`
  query GetAccountTransactions($id: ID!) {
    compteTransactions(id: $id) {
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

/** Query to retrieve all transactions in the system */
export const GET_ALL_TRANSACTIONS = gql`
  query GetAllTransactions {
    allTransactions {
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

/** Query to retrieve transaction statistics (count, sum by type) */
export const GET_TRANSACTION_STATS = gql`
  query GetTransactionStats {
    transactionStats {
      count
      sumDepots
      sumRetraits
    }
  }
`;
