import { ApolloClient, InMemoryCache, createHttpLink } from '@apollo/client';

/**
 * GraphQL endpoint configuration.
 * Points to the backend GraphQL API at /graphql.
 */
const httpLink = createHttpLink({
    uri: '/graphql',
    credentials: 'include', // Include credentials for CORS requests
});

/**
 * Apollo Client instance for GraphQL operations.
 * 
 * Configured with:
 * - HTTP link to GraphQL endpoint
 * - In-memory cache for query results
 * - Network-only fetch policy for fresh data
 * 
 * @type {ApolloClient}
 */
export const client = new ApolloClient({
    link: httpLink,
    cache: new InMemoryCache({
        typePolicies: {
            Compte: {
                keyFields: ['id'],
            },
            Transaction: {
                keyFields: ['id'],
            },
        },
    }),
    defaultOptions: {
        watchQuery: {
            fetchPolicy: 'network-only',
            errorPolicy: 'all',
        },
        query: {
            fetchPolicy: 'network-only',
            errorPolicy: 'all',
        },
        mutate: {
            errorPolicy: 'all',
        },
    },
});
