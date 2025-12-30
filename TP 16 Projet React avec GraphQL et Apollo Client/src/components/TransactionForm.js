import React, { useState } from 'react';
import { useMutation, useQuery } from '@apollo/client';
import { ADD_TRANSACTION } from '../graphql/mutations';
import { GET_ALL_TRANSACTIONS, GET_ALL_COMPTES } from '../graphql/queries';

/**
 * Component for creating new transactions (deposits or withdrawals).
 * 
 * Features:
 * - Transaction type selection (deposit/withdrawal)
 * - Account selection from available accounts
 * - Amount validation
 * - Visual feedback based on transaction type
 * - Auto-refresh of transactions and accounts after creation
 * 
 * @returns {JSX.Element} The transaction form component
 */
const TransactionForm = () => {
    const [transactionType, setTransactionType] = useState('DEPOT');
    const [amount, setAmount] = useState('');
    const [accountId, setAccountId] = useState('');
    const [validationError, setValidationError] = useState('');

    // Fetch accounts for account selector
    const { data: accountsData, loading: accountsLoading } = useQuery(GET_ALL_COMPTES);

    const [addTransaction, { loading, error }] = useMutation(ADD_TRANSACTION, {
        refetchQueries: [
            { query: GET_ALL_TRANSACTIONS },
            { query: GET_ALL_COMPTES }
        ],
        onCompleted: () => {
            setAmount('');
            setValidationError('');
        },
    });

    /**
     * Validates transaction amount.
     * @param {string} value - The amount to validate
     * @returns {boolean} True if valid
     */
    const validateAmount = (value) => {
        const numericValue = parseFloat(value);

        if (isNaN(numericValue) || numericValue <= 0) {
            setValidationError('Amount must be greater than zero');
            return false;
        }

        setValidationError('');
        return true;
    };

    /**
     * Handles form submission.
     * @param {Event} e - Form submit event
     */
    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!validateAmount(amount)) return;

        if (!accountId) {
            setValidationError('Please select an account');
            return;
        }

        try {
            await addTransaction({
                variables: {
                    transactionRequest: {
                        type: transactionType,
                        montant: parseFloat(amount),
                        compteId: accountId,
                    },
                },
            });
        } catch (err) {
            console.error('Error creating transaction:', err);
        }
    };

    const isDeposit = transactionType === 'DEPOT';

    return (
        <div className="bg-white rounded-lg shadow-md p-6">
            <h2 className="text-2xl font-bold mb-4 text-gray-800">New Transaction</h2>

            <form onSubmit={handleSubmit} className="space-y-4" noValidate>
                <div>
                    <label
                        htmlFor="transaction-type"
                        className="block text-sm font-medium text-gray-700 mb-2"
                    >
                        Transaction Type *
                    </label>
                    <select
                        id="transaction-type"
                        value={transactionType}
                        onChange={(e) => setTransactionType(e.target.value)}
                        className="w-full border border-gray-300 rounded-lg p-3 focus:ring-2 focus:ring-blue-500 focus:border-transparent transition"
                        required
                    >
                        <option value="DEPOT">📥 Deposit</option>
                        <option value="RETRELHARCHI">📤 Withdrawal</option>
                    </select>
                </div>

                <div>
                    <label
                        htmlFor="account-select"
                        className="block text-sm font-medium text-gray-700 mb-2"
                    >
                        Account *
                    </label>
                    <select
                        id="account-select"
                        value={accountId}
                        onChange={(e) => setAccountId(e.target.value)}
                        className="w-full border border-gray-300 rounded-lg p-3 focus:ring-2 focus:ring-blue-500 focus:border-transparent transition"
                        required
                        disabled={accountsLoading}
                    >
                        <option value="">-- Select an account --</option>
                        {accountsData?.allComptes.map((account) => (
                            <option key={account.id} value={account.id}>
                                {account.type} - €{account.solde.toFixed(2)}
                            </option>
                        ))}
                    </select>
                </div>

                <div>
                    <label
                        htmlFor="amount-input"
                        className="block text-sm font-medium text-gray-700 mb-2"
                    >
                        Amount *
                    </label>
                    <input
                        id="amount-input"
                        type="number"
                        step="0.01"
                        min="0.01"
                        value={amount}
                        onChange={(e) => {
                            setAmount(e.target.value);
                            if (validationError) validateAmount(e.target.value);
                        }}
                        onBlur={(e) => validateAmount(e.target.value)}
                        className={`w-full border rounded-lg p-3 focus:ring-2 focus:border-transparent transition ${validationError ? 'border-red-300 focus:ring-red-500' : 'border-gray-300 focus:ring-blue-500'
                            }`}
                        placeholder="Ex: 50.00"
                        required
                        aria-describedby="amount-error"
                        aria-invalid={!!validationError}
                    />
                    {validationError && (
                        <p id="amount-error" className="mt-1 text-sm text-red-600">
                            {validationError}
                        </p>
                    )}
                </div>

                {error && (
                    <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg" role="alert">
                        <strong>Error:</strong> {error.message}
                    </div>
                )}

                <button
                    type="submit"
                    disabled={loading || !!validationError}
                    className={`w-full text-white font-semibold py-3 px-4 rounded-lg transition duration-200 ${loading || validationError
                            ? 'bg-gray-400 cursor-not-allowed'
                            : isDeposit
                                ? 'bg-green-500 hover:bg-green-600 active:bg-green-700'
                                : 'bg-red-500 hover:bg-red-600 active:bg-red-700'
                        }`}
                    aria-busy={loading}
                >
                    {loading
                        ? '⏳ Processing...'
                        : isDeposit ? '💰 Make Deposit' : '💸 Make Withdrawal'}
                </button>
            </form>
        </div>
    );
};

export default TransactionForm;
