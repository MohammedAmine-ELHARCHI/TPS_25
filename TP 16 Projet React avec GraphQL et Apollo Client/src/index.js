import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';

/**
 * Application Entry Point.
 * 
 * Renders the React application into the DOM with StrictMode enabled
 * for additional development checks and warnings.
 */
const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
    <React.StrictMode>
        <App />
    </React.StrictMode>
);
