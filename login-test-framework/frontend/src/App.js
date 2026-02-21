import React, { useState } from 'react';
import LoginForm from './components/LoginForm';
import CreateAccount from './components/CreateAccount';
import Welcome from './components/Welcome';
import './App.css';

function App() {
  const [currentPage, setCurrentPage] = useState('login');
  const [userEmail, setUserEmail] = useState('');

  const handleCreateAccountClick = () => {
    setCurrentPage('createAccount');
  };

  const handleBackToLogin = () => {
    setCurrentPage('login');
  };

  const handleLoginSuccess = (email) => {
    setUserEmail(email);
    setCurrentPage('welcome');
  };

  const handleAccountCreated = () => {
    setCurrentPage('login');
  };

  return (
    <div className="app-container">
      {currentPage === 'login' && (
        <LoginForm 
          onCreateAccountClick={handleCreateAccountClick}
          onLoginSuccess={handleLoginSuccess}
        />
      )}
      {currentPage === 'createAccount' && (
        <CreateAccount 
          onBackClick={handleBackToLogin}
          onAccountCreated={handleAccountCreated}
        />
      )}
      {currentPage === 'welcome' && (
        <Welcome email={userEmail} />
      )}
    </div>
  );
}

export default App;
