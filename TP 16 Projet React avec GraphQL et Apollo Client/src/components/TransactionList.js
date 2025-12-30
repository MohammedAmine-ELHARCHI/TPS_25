import React from "react";
import { useQuery } from "@apollo/client";
import { GET_ALL_TRANSACTIONS } from "../graphql/queries";

/**
 * Component for displaying transaction history.
 * 
 * Features:
 * - Displays all transactions in chronological order
 * - Visual distinction between deposits (green) and withdrawals (red)
 * - Shows account info and balances
 * - Scrollable list for many transactions
 * - Manual refresh capability
 * 
 * @returns {JSX.Element} The transaction list component
 */
const TransactionList = () => {
    const { loading, error, data, refetch } = useQuery(GET_ALL_TRANSACTIONS);

    if (loading) {
        return (
            <div className="bg-white rounded-lg shadow-md p-6">
                <div className="flex items-center justify-center space-x-2">
                    <div className="animate-spin rounded-full h-6 w-6 border-b-2 border-blue-500"></div>
                    <p className="text-center text-gray-500">Loading transactions...</p>
                </div>
            </div>
        );
    }

    if (error) {
        return (
            <div className="bg-white rounded-lg shadow-md p-6">
                <div className="bg-red-50 border border-red-200 rounded-lg p-4">
                    <p className="text-red-700 font-semibold">Error loading transactions</p>
                    <p className="text-red-600 text-sm mt-1">{error.message}</p>
                </div>
            </div>
        );
    }

    const transactions = data?.allTransactions || [];

    return (
        <div className="bg-white rounded-lg shadow-md p-6">
            <div className="flex justify-between items-center mb-4">
                <h2 className="text-2xl font-bold text-gray-800">Transaction History</h2>
                <button
                    onClick={() => refetch()}
                    className="bg-gray-200 hover:bg-gray-300 text-gray-700 px-4 py-2 rounded-lg transition duration-200"
                    aria-label="Refresh transactions"
                >
                    🔄 Refresh
                </button>
            </div>

            {transactions.length === 0 ? (
                <div className="text-center py-12">
                    <p className="text-gray-500 text-lg">No transactions yet</p>
                    <p className="text-gray-400 text-sm mt-2">Create your first transaction to see it here</p>
                </div>
            ) : (
                <>
                    <div className="space-y-3 max-h-96 overflow-y-auto pr-2">
                        {transactions.map((transaction) => {
                            const isDeposit = transaction.type === 'DEPOT';

                            return (
                                <article
                                    key={transaction.id}
                                    className={`border-l-4 p-4 rounded-lg transition duration-200 ${isDeposit
                                            ? 'border-green-500 bg-green-50 hover:bg-green-100'
                                            : 'border-red-500 bg-red-50 hover:bg-red-100'
                                        }`}
                                >
                                    <div className="flex justify-between items-start">
                                        <div>
                                            <p className="font-semibold text-lg">
                                                {isDeposit ? '📥 Deposit' : '📤 Withdrawal'}
                                            </p>
                                            <p className="text-sm text-gray-600 mt-1">
                                                Account: {transaction.compte.type === 'COURANT' ? 'Checking' : 'Savings'}
                                            </p>
                                            <p className="text-xs text-gray-500 mt-1" title={new Date(transaction.date).toISOString()}>
                                                {new Date(transaction.date).toLocaleString('en-US', {
                                                    year: 'numeric',
                                                    month: 'short',
                                                    day: 'numeric',
                                                    hour: '2-digit',
                                                    minute: '2-digit'
                                                })}
                                            </p>
                                        </div>
                                        <div className="text-right">
                                            <p className={`text-2xl font-bold ${isDeposit ? 'text-green-600' : 'text-red-600'
                                                }`}>
                                                {isDeposit ? '+' : '-'}€{transaction.montant.toLocaleString('fr-FR', {
                                                    minimumFractionDigits: 2,
                                                    maximumFractionDigits: 2
                                                })}
                                            </p>
                                            <p className="text-sm text-gray-600 mt-1">
                                                Balance: €{transaction.compte.solde.toLocaleString('fr-FR', {
                                                    minimumFractionDigits: 2,
                                                    maximumFractionDigits: 2
                                                })}
                                            </p>
                                        </div>
                                    </div>
                                </article>
                            );
                        })}
                    </div>

                    <div className="mt-4 pt-4 border-t border-gray-200">
                        <p className="text-sm text-gray-600 text-center">
                            Total: <span className="font-semibold">{transactions.length}</span> transaction(s)
                        </p>
                    </div>
                </>
            )}
        </div>
    );
};

export default TransactionList;
