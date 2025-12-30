import { ApolloProvider } from "@apollo/client";
import { client } from "./apollo/client";
import CompteList from "./components/CompteList";
import CreateCompte from "./components/CreateCompte";
import TransactionForm from "./components/TransactionForm";
import TransactionList from "./components/TransactionList";
import "./App.css";

/**
 * Main Application Component for Banking Management System.
 * 
 * Provides a React interface for managing bank accounts and transactions
 * using GraphQL and Apollo Client. The UI is divided into two main sections:
 * - Left: Account creation and listing
 * - Right: Transaction management
 * 
 * @returns {JSX.Element} The main application component wrapped in Apollo Provider
 */
function App() {
    return (
        <ApolloProvider client={client}>
            <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 py-8">
                <div className="container mx-auto px-4">
                    {/* Application Header */}
                    <header className="text-center mb-8">
                        <h1 className="text-5xl font-bold text-gray-800 mb-2">
                            🏦 Banking Management
                        </h1>
                        <p className="text-gray-600">
                            React Application with GraphQL and Apollo Client
                        </p>
                    </header>

                    {/* Main Content Grid */}
                    <main className="grid grid-cols-1 lg:grid-cols-2 gap-8">
                        {/* Accounts Section */}
                        <section className="space-y-6" aria-label="Account Management">
                            <CreateCompte />
                            <CompteList />
                        </section>

                        {/* Transactions Section */}
                        <section className="space-y-6" aria-label="Transaction Management">
                            <TransactionForm />
                            <TransactionList />
                        </section>
                    </main>

                    {/* Application Footer */}
                    <footer className="mt-8 text-center text-gray-600 text-sm">
                        <p>TP 16 - React GraphQL Apollo Client Project</p>
                        <p className="mt-1">Developed by MOHAMMED AMINE ELHARCHI</p>
                    </footer>
                </div>
            </div>
        </ApolloProvider>
    );
}

export default App;
