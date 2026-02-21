import React from 'react';
import './Welcome.css';

const Welcome = ({ email }) => {
  const handleLogout = () => {
    localStorage.removeItem('authToken');
    window.location.reload();
  };

  const getFirstName = (email) => {
    return email.split('@')[0].charAt(0).toUpperCase() + email.split('@')[0].slice(1);
  };

  return (
    <div className="welcome-container">
      <div className="welcome-card">
        <div className="welcome-icon">✓</div>
        <h1 className="welcome-title">Welcome!</h1>
        <p className="welcome-subtitle">Successfully Logged In</p>
        
        <div className="success-details">
          <p className="success-message">Congratulations! You have successfully logged in.</p>
          <p className="user-info">
            <span className="user-label">Logged in as:</span>
            <span className="user-email">{email}</span>
          </p>
        </div>

        <div className="welcome-features">
          <h2>What's Next?</h2>
          <ul>
            <li>Explore your account dashboard</li>
            <li>Update your profile information</li>
            <li>Access secure resources</li>
            <li>Manage your security settings</li>
          </ul>
        </div>

        <button 
          className="logout-button"
          onClick={handleLogout}
        >
          Logout
        </button>
      </div>

      <div className="decoration decoration-1"></div>
      <div className="decoration decoration-2"></div>
    </div>
  );
};

export default Welcome;
