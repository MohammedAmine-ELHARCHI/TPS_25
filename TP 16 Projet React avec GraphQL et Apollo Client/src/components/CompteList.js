import React from "react";
import { useQuery } from "@apollo/client";
import { GET_ALL_COMPTES } from "../graphql/queries";

/**
 * Component for displaying a list of all bank accounts.
 * 
 * Features:
 * - Displays all accounts with their details (ID, type, balance, creation date)
 * - Manual refresh capability
 * - Visual distinction between account types (COURANT vs EPARGNE)
 * - Responsive grid layout
 * 
 * @returns {JSX.Element} The account list component
 */
const CompteList = () => {
    const { loading, error, data, refetch } = useQuery(GET_ALL_COMPTES);

    // Loading state
    if (loading) {
        return (
            <div className="bg-white rounded-lg shadow-md p-6">
                <div className="flex items-center justify-center space-x-2">
                    <div className="animate-spin rounded-full h-6 w-6 border-b-2 border-blue-500"></div>
                    <p className="text-center text-gray-500">Loading accounts...</p>
                </div>
            </div>
        );
    }

    // Error state
    if (error) {
        return (
            <div className="bg-white rounded-lg shadow-md p-6">
                <div className="bg-red-50 border border-red-200 rounded-lg p-4">
                    <p className="text-red-700 font-semibold">Error loading accounts</p>
                    <p className="text-red-600 text-sm mt-1">{error.message}</p>
                </div>
            </div>
        );
    }

    const accounts = data?.allComptes || [];

    return (
        <div className="bg-white rounded-lg shadow-md p-6">
            <div className="flex justify-between items-center mb-4">
                <h2 className="text-2xl font-bold text-gray-800">Accounts List</h2>
                <button
                    onClick={() => refetch()}
                    className="bg-gray-200 hover:bg-gray-300 text-gray-700 px-4 py-2 rounded-lg transition duration-200"
                    aria-label="Refresh accounts list"
                >
                    🔄 Refresh
                </button>
            </div>

            {accounts.length === 0 ? (
                <div className="text-center py-12">
                    <p className="text-gray-500 text-lg">No accounts available</p>
                    <p className="text-gray-400 text-sm mt-2">Create your first account to get started</p>
                </div>
            ) : (
                <>
                    <div className="space-y-4">
                        {accounts.map((account) => (
                            <article
                                key={account.id}
                                className="border border-gray-200 p-4 rounded-lg hover:shadow-md transition duration-200"
                            >
                                <div className="grid grid-cols-2 gap-4">
                                    <div>
                                        <p className="text-sm text-gray-500">Account ID</p>
                                        <p className="font-mono text-xs" title={account.id}>
                                            {account.id.substring(0, 8)}...
                                        </p>
                                    </div>
                                    <div>
                                        <p className="text-sm text-gray-500">Type</p>
                                        <span
                                            className={`inline-block px-2 py-1 rounded text-sm font-semibold ${account.type === 'COURANT'
                                                    ? 'bg-blue-100 text-blue-800'
                                                    : 'bg-green-100 text-green-800'
                                                }`}
                                        >
                                            {account.type === 'COURANT' ? '💳 Checking' : '💰 Savings'}
                                        </span>
                                    </div>
                                    <div>
                                        <p className="text-sm text-gray-500">Balance</p>
                                        <p className="text-xl font-bold text-gray-800">
                                            €{account.solde.toLocaleString('fr-FR', {
                                                minimumFractionDigits: 2,
                                                maximumFractionDigits: 2
                                            })}
                                        </p>
                                    </div>
                                    <div>
                                        <p className="text-sm text-gray-500">Created on</p>
                                        <p className="text-sm">
                                            {new Date(account.dateCreation).toLocaleDateString('en-US', {
                                                year: 'numeric',
                                                month: 'short',
                                                day: 'numeric'
                                            })}
                                        </p>
                                    </div>
                                </div>
                            </article>
                        ))}
                    </div>

                    <div className="mt-4 pt-4 border-t border-gray-200">
                        <p className="text-sm text-gray-600 text-center">
                            Total: <span className="font-semibold">{accounts.length}</span> account(s)
                        </p>
                    </div>
                </>
            )}
        </div>
    );
};

export default CompteList;
