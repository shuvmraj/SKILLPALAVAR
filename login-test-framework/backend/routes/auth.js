const express = require('express');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const pool = require('../config/database');

const router = express.Router();

// Validation functions
const validateEmail = (email) => {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
};

const validatePassword = (password) => {
  // Minimum 8 chars, 1 upper, 1 lower, 1 number, 1 special char
  const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
  return passwordRegex.test(password);
};

// Check for SQL injection patterns
const containsSQLInjection = (input) => {
  const sqlKeywords = /(\b(SELECT|INSERT|UPDATE|DELETE|DROP|CREATE|ALTER|EXEC|EXECUTE|UNION|WHERE|OR|AND|--|;|\/\*|\*\/|xp_|sp_)\b)/gi;
  return sqlKeywords.test(input);
};

// Register endpoint
router.post('/register', async (req, res) => {
  const { email, password } = req.body;

  try {
    // Validate inputs
    if (!email || !password) {
      return res.status(400).json({
        message: 'Email and password are required',
        errors: {
          email: !email ? 'Email is required' : undefined,
          password: !password ? 'Password is required' : undefined
        }
      });
    }

    // Check for SQL injection
    if (containsSQLInjection(email) || containsSQLInjection(password)) {
      return res.status(400).json({
        message: 'Invalid input detected',
        errors: { submit: 'Suspicious input pattern detected' }
      });
    }

    // Validate email format
    if (!validateEmail(email)) {
      return res.status(400).json({
        message: 'Invalid email format',
        errors: { email: 'Invalid email format' }
      });
    }

    // Validate password strength
    if (!validatePassword(password)) {
      return res.status(400).json({
        message: 'Password does not meet requirements',
        errors: {
          password: 'Password must contain uppercase, lowercase, number, and special character (@$!%*?&)'
        }
      });
    }

    const connection = await pool.getConnection();

    // Check if user already exists
    const [existingUsers] = await connection.execute(
      'SELECT id FROM users WHERE email = ?',
      [email]
    );

    if (existingUsers.length > 0) {
      connection.release();
      return res.status(409).json({
        message: 'User already exists',
        errors: { submit: 'This email is already registered' }
      });
    }

    // Hash password
    const hashedPassword = await bcrypt.hash(password, 10);

    // Create user
    await connection.execute(
      'INSERT INTO users (email, password) VALUES (?, ?)',
      [email, hashedPassword]
    );

    connection.release();

    res.status(201).json({
      message: 'User registered successfully'
    });
  } catch (error) {
    console.error('Register error:', error);
    res.status(500).json({
      message: 'Registration failed',
      errors: { submit: 'An error occurred during registration' }
    });
  }
});

// Login endpoint
router.post('/login', async (req, res) => {
  const { email, password } = req.body;
  const clientIP = req.ip;

  try {
    // Validate inputs
    if (!email || !password) {
      return res.status(400).json({
        message: 'Email and password are required',
        errors: {
          email: !email ? 'Email is required' : undefined,
          password: !password ? 'Password is required' : undefined
        }
      });
    }

    // Check for SQL injection
    if (containsSQLInjection(email) || containsSQLInjection(password)) {
      const connection = await pool.getConnection();
      await connection.execute(
        'INSERT INTO login_attempts (email, status, ip_address, error_message) VALUES (?, ?, ?, ?)',
        [email, 'failed', clientIP, 'SQL Injection Attempt Detected']
      );
      connection.release();

      return res.status(400).json({
        message: 'Invalid input detected',
        errors: { submit: 'Suspicious input pattern detected' }
      });
    }

    // Trim email for comparison
    const trimmedEmail = email.trim();

    const connection = await pool.getConnection();

    // Find user
    const [users] = await connection.execute(
      'SELECT * FROM users WHERE email = ?',
      [trimmedEmail]
    );

    if (users.length === 0) {
      await connection.execute(
        'INSERT INTO login_attempts (email, status, ip_address, error_message) VALUES (?, ?, ?, ?)',
        [trimmedEmail, 'failed', clientIP, 'User not found']
      );
      connection.release();

      return res.status(401).json({
        message: 'Invalid credentials',
        errors: { submit: 'Invalid email or password' }
      });
    }

    const user = users[0];

    // Verify password
    const passwordMatch = await bcrypt.compare(password, user.password);

    if (!passwordMatch) {
      await connection.execute(
        'INSERT INTO login_attempts (email, status, ip_address, error_message) VALUES (?, ?, ?, ?)',
        [trimmedEmail, 'failed', clientIP, 'Invalid password']
      );
      connection.release();

      return res.status(401).json({
        message: 'Invalid credentials',
        errors: { submit: 'Invalid email or password' }
      });
    }

    // Log successful attempt
    await connection.execute(
      'INSERT INTO login_attempts (email, status, ip_address) VALUES (?, ?, ?)',
      [trimmedEmail, 'success', clientIP]
    );

    connection.release();

    // Generate JWT token
    const token = jwt.sign(
      { id: user.id, email: user.email },
      process.env.JWT_SECRET,
      { expiresIn: '1h' }
    );

    res.json({
      message: 'Login successful',
      token,
      user: {
        id: user.id,
        email: user.email
      }
    });
  } catch (error) {
    console.error('Login error:', error);
    res.status(500).json({
      message: 'Login failed',
      errors: { submit: 'An error occurred during login' }
    });
  }
});

// Get login attempts (for testing/monitoring)
router.get('/login-attempts', async (req, res) => {
  try {
    const connection = await pool.getConnection();
    const [attempts] = await connection.execute(
      'SELECT * FROM login_attempts ORDER BY attempt_time DESC LIMIT 100'
    );
    connection.release();

    res.json(attempts);
  } catch (error) {
    console.error('Error fetching login attempts:', error);
    res.status(500).json({ message: 'Failed to fetch login attempts' });
  }
});

module.exports = router;
