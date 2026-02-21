import React, { useState } from 'react';
import axios from 'axios';
import './CreateAccount.css';

const CreateAccount = ({ onBackClick, onAccountCreated }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
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

    // Confirm password validation
    if (!confirmPassword) {
      newErrors.confirmPassword = 'Please confirm your password';
    } else if (password !== confirmPassword) {
      newErrors.confirmPassword = 'Passwords do not match';
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
      const response = await axios.post('http://localhost:5000/api/auth/register', {
        email: email.trim(),
        password
      });

      setSuccessMessage(response.data.message);
      setEmail('');
      setPassword('');
      setConfirmPassword('');
      setErrors({});

      // Redirect to login page after a short delay
      setTimeout(() => {
        onAccountCreated();
      }, 1500);
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

  const handleConfirmPasswordChange = (e) => {
    setConfirmPassword(e.target.value);
    if (errors.confirmPassword) {
      setErrors({ ...errors, confirmPassword: undefined });
    }
  };

  // Check password strength rules
  const passwordRules = {
    length: password.length >= 8,
    uppercase: /[A-Z]/.test(password),
    lowercase: /[a-z]/.test(password),
    number: /\d/.test(password),
    special: /[@$!%*?&]/.test(password)
  };

  const allRulesMet = Object.values(passwordRules).every(rule => rule);

  return (
    <div className="create-account-container">
      <div className="create-account-card">
        <h1 className="create-account-title">Create Account</h1>
        
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

        <form onSubmit={handleSubmit} className="create-account-form">
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
            <div className="password-input-wrapper">
              <input
                id="password"
                type={showPassword ? 'text' : 'password'}
                className={`form-input ${errors.password ? 'input-error' : ''}`}
                value={password}
                onChange={handlePasswordChange}
                placeholder="Enter your password"
                maxLength="128"
                disabled={loading}
              />
              <button
                type="button"
                className="password-toggle"
                onClick={() => setShowPassword(!showPassword)}
                disabled={loading}
                title={showPassword ? 'Hide password' : 'Show password'}
              >
                {showPassword ? '👁️' : '👁️‍🗨️'}
              </button>
            </div>
            {errors.password && (
              <span className="error-text" id="passwordError">{errors.password}</span>
            )}
          </div>

          <div className="password-rules">
            <p className="rules-title">Password Rules:</p>
            <ul className="rules-list">
              <li className={passwordRules.length ? 'rule-met' : 'rule-unmet'}>
                {passwordRules.length ? '✓' : '○'} At least 8 characters
              </li>
              <li className={passwordRules.uppercase ? 'rule-met' : 'rule-unmet'}>
                {passwordRules.uppercase ? '✓' : '○'} Uppercase letter (A-Z)
              </li>
              <li className={passwordRules.lowercase ? 'rule-met' : 'rule-unmet'}>
                {passwordRules.lowercase ? '✓' : '○'} Lowercase letter (a-z)
              </li>
              <li className={passwordRules.number ? 'rule-met' : 'rule-unmet'}>
                {passwordRules.number ? '✓' : '○'} Number (0-9)
              </li>
              <li className={passwordRules.special ? 'rule-met' : 'rule-unmet'}>
                {passwordRules.special ? '✓' : '○'} Special character (@$!%*?&)
              </li>
            </ul>
          </div>

          <div className="form-group">
            <label htmlFor="confirmPassword" className="form-label">Confirm Password</label>
            <div className="password-input-wrapper">
              <input
                id="confirmPassword"
                type={showConfirmPassword ? 'text' : 'password'}
                className={`form-input ${errors.confirmPassword ? 'input-error' : ''}`}
                value={confirmPassword}
                onChange={handleConfirmPasswordChange}
                placeholder="Confirm your password"
                maxLength="128"
                disabled={loading}
              />
              <button
                type="button"
                className="password-toggle"
                onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                disabled={loading}
                title={showConfirmPassword ? 'Hide password' : 'Show password'}
              >
                {showConfirmPassword ? '👁️' : '👁️‍🗨️'}
              </button>
            </div>
            {errors.confirmPassword && (
              <span className="error-text" id="confirmPasswordError">{errors.confirmPassword}</span>
            )}
            {confirmPassword && password === confirmPassword && !errors.confirmPassword && (
              <span className="success-text">Passwords match ✓</span>
            )}
          </div>

          <button 
            type="submit" 
            className="create-account-button"
            disabled={loading || !allRulesMet}
            id="createAccountButton"
          >
            {loading ? 'Creating Account...' : 'Create Account'}
          </button>
        </form>

        <div className="back-to-login">
          <button 
            type="button"
            className="back-link"
            onClick={onBackClick}
            disabled={loading}
          >
            ← Back to Login
          </button>
        </div>
      </div>
    </div>
  );
};

export default CreateAccount;
