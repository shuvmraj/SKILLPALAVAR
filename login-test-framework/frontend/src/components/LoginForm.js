import React, { useState } from 'react';
import axios from 'axios';
import './LoginForm.css';

const LoginForm = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errors, setErrors] = useState({});
  const [successMessage, setSuccessMessage] = useState('');
  const [loading, setLoading] = useState(false);

  // Email validation regex
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  
  // Password strength regex - minimum 8 chars, 1 upper, 1 lower, 1 number, 1 special char
  const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

  const validateForm = () => {
    let newErrors = {};

    // Email validation
    if (!email.trim()) {
      newErrors.email = 'Email is required';
    } else if (email.length > 255) {
      newErrors.email = 'Email cannot exceed 255 characters';
    } else if (!emailRegex.test(email)) {
      newErrors.email = 'Invalid email format';
    }

    // Password validation
    if (!password) {
      newErrors.password = 'Password is required';
    } else if (password.length < 8) {
      newErrors.password = 'Password must be at least 8 characters';
    } else if (password.length > 128) {
      newErrors.password = 'Password cannot exceed 128 characters';
    } else if (!passwordRegex.test(password)) {
      newErrors.password = 'Password must contain uppercase, lowercase, number, and special character (@$!%*?&)';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSuccessMessage('');

    if (!validateForm()) {
      return;
    }

    setLoading(true);
    try {
      const response = await axios.post('http://localhost:5000/api/auth/login', {
        email: email.trim(),
        password
      });

      setSuccessMessage(response.data.message);
      setEmail('');
      setPassword('');
      setErrors({});

      // Store token if provided
      if (response.data.token) {
        localStorage.setItem('authToken', response.data.token);
      }
    } catch (error) {
      if (error.response && error.response.data) {
        setErrors(error.response.data.errors || { submit: error.response.data.message });
      } else {
        setErrors({ submit: 'An error occurred. Please try again.' });
      }
    } finally {
      setLoading(false);
    }
  };

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
    if (errors.email) {
      setErrors({ ...errors, email: undefined });
    }
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
    if (errors.password) {
      setErrors({ ...errors, password: undefined });
    }
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <h1 className="login-title">Secure Login</h1>
        
        {successMessage && (
          <div className="success-message" id="successMessage">
            {successMessage}
          </div>
        )}

        {errors.submit && (
          <div className="error-message" id="submitError">
            {errors.submit}
          </div>
        )}

        <form onSubmit={handleSubmit} className="login-form">
          <div className="form-group">
            <label htmlFor="email" className="form-label">Email Address</label>
            <input
              id="email"
              type="email"
              className={`form-input ${errors.email ? 'input-error' : ''}`}
              value={email}
              onChange={handleEmailChange}
              placeholder="Enter your email"
              maxLength="255"
              disabled={loading}
            />
            {errors.email && (
              <span className="error-text" id="emailError">{errors.email}</span>
            )}
          </div>

          <div className="form-group">
            <label htmlFor="password" className="form-label">Password</label>
            <input
              id="password"
              type="password"
              className={`form-input ${errors.password ? 'input-error' : ''}`}
              value={password}
              onChange={handlePasswordChange}
              placeholder="Enter your password"
              maxLength="128"
              disabled={loading}
            />
            {errors.password && (
              <span className="error-text" id="passwordError">{errors.password}</span>
            )}
          </div>

          <button 
            type="submit" 
            className="login-button"
            disabled={loading}
            id="loginButton"
          >
            {loading ? 'Logging in...' : 'Login'}
          </button>
        </form>

        <div className="password-requirements">
          <p>Password Requirements:</p>
          <ul>
            <li>At least 8 characters</li>
            <li>Contains uppercase letter (A-Z)</li>
            <li>Contains lowercase letter (a-z)</li>
            <li>Contains number (0-9)</li>
            <li>Contains special character (@$!%*?&)</li>
          </ul>
        </div>
      </div>
    </div>
  );
};

export default LoginForm;
