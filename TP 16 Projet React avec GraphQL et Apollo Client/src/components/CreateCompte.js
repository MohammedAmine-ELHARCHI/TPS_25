import React, { useState } from 'react';
import { useMutation } from '@apollo/client';
import { SAVE_COMPTE } from '../graphql/mutations';
import { GET_ALL_COMPTES } from '../graphql/queries';

/**
 * Component for creating new bank accounts.
 * 
 * Features:
 * - Form validation for balance input
 * - Account type selection (Checking/Savings)
 * - Auto-refresh accounts list after creation
 * - Loading and error states
 * 
 * @returns {JSX.Element} The account creation form component
 */
const CreateCompte = () => {
    const [balance, setBalance] = useState('');
    const [accountType, setAccountType] = useState('COURANT');
    const [validationError, setValidationError] = useState('');

    const [saveCompte, { loading, error }] = useMutation(SAVE_COMPTE, {
        refetchQueries: [{ query: GET_ALL_COMPTES }],
        onCompleted: () => {
            // Reset form on successful creation
            setBalance('');
            setAccountType('COURANT');
            setValidationError('');
        },
    });

    /**
     * Validates balance input before submission.
     * 
     * @param {number} value - The balance value to validate
     * @returns {boolean} True if valid, false otherwise
     */
    const validateBalance = (value) => {
        const numericValue = parseFloat(value);

        if (isNaN(numericValue)) {
            setValidationError('Please enter a valid number');
            return false;
        }

        if (numericValue < 0) {
            setValidationError('Balance cannot be negative');
            return false;
        }

        setValidationError('');
        return true;
    };

    /**
     * Handles form submission for creating a new account.
     * 
     * @param {Event} e - The form submission event
     */
    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!validateBalance(balance)) {
            return;
        }

        try {
            await saveCompte({
                variables: {
                    compte: {
                        solde: parseFloat(balance),
                        type: accountType,
                    },
                },
            });
        } catch (err) {
            // Error is handled by Apollo's error state
            console.error('Error creating account:', err);
        }
    };

    return (
        <div className="bg-white rounded-lg shadow-md p-6">
            <h2 className="text-2xl font-bold mb-4 text-gray-800">Create Account</h2>

            <form onSubmit={handleSubmit} className="space-y-4" noValidate>
                <div>
                    <label
                        htmlFor="balance-input"
                        className="block text-sm font-medium text-gray-700 mb-2"
                    >
                        Initial Balance *
                    </label>
                    <input
                        id="balance-input"
                        type="number"
                        step="0.01"
                        min="0"
                        value={balance}
                        onChange={(e) => {
                            setBalance(e.target.value);
                            if (validationError) {
                                validateBalance(e.target.value);
                            }
                        }}
                        onBlur={(e) => validateBalance(e.target.value)}
                        className={`w-full border rounded-lg p-3 focus:ring-2 focus:ring-blue-500 focus:border-transparent transition ${validationError ? 'border-red-300 focus:ring-red-500' : 'border-gray-300'
                            }`}
                        placeholder="Ex: 1000.00"
                        required
                        aria-describedby="balance-error"
                        aria-invalid={!!validationError}
                    />
                    {validationError && (
                        <p id="balance-error" className="mt-1 text-sm text-red-600">
                            {validationError}
                        </p>
                    )}
                </div>

                <div>
                    <label
                        htmlFor="account-type-select"
                        className="block text-sm font-medium text-gray-700 mb-2"
                    >
                        Account Type *
                    </label>
                    <select
                        id="account-type-select"
                        value={accountType}
                        onChange={(e) => setAccountType(e.target.value)}
                        className="w-full border border-gray-300 rounded-lg p-3 focus:ring-2 focus:ring-blue-500 focus:border-transparent transition"
                        required
                    >
                        <option value="COURANT">💳 Checking Account</option>
                        <option value="EPARGNE">💰 Savings Account</option>
                    </select>
                </div>

                {error && (
                    <div
                        className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg"
                        role="alert"
                    >
                        <strong>Error:</strong> {error.message}
                    </div>
                )}

                <button
                    type="submit"
                    disabled={loading || !!validationError}
                    className={`w-full text-white font-semibold py-3 px-4 rounded-lg transition duration-200 ${loading || validationError
                            ? 'bg-gray-400 cursor-not-allowed'
                            : 'bg-blue-500 hover:bg-blue-600 active:bg-blue-700'
                        }`}
                    aria-busy={loading}
                >
                    {loading ? (
                        <span className="flex items-center justify-center">
                            <svg className="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                                <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                                <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                            </svg>
                            Creating account...
                        </span>
                    ) : (
                        '➕ Create Account'
                    )}
                </button>
            </form>
        </div>
    );
};

export default CreateCompte;
