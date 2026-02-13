const mysql = require('mysql2/promise');
require('dotenv').config();

async function initializeDatabase() {
  try {
    // Connect without database first
    const connection = await mysql.createConnection({
      host: process.env.DB_HOST,
      user: process.env.DB_USER,
      password: process.env.DB_PASSWORD
    });

    // Create database
    await connection.execute(
      `CREATE DATABASE IF NOT EXISTS ${process.env.DB_NAME}`
    );
    console.log('Database created or already exists');

    // Close first connection and create new one with database selected
    await connection.end();

    // Create new connection with database
    const dbConnection = await mysql.createConnection({
      host: process.env.DB_HOST,
      user: process.env.DB_USER,
      password: process.env.DB_PASSWORD,
      database: process.env.DB_NAME
    });

    // Create users table
    await dbConnection.execute(`
      CREATE TABLE IF NOT EXISTS users (
        id INT AUTO_INCREMENT PRIMARY KEY,
        email VARCHAR(255) UNIQUE NOT NULL,
        password VARCHAR(255) NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
      )
    `);
    console.log('Users table created or already exists');

    // Create login_attempts table for security
    await dbConnection.execute(`
      CREATE TABLE IF NOT EXISTS login_attempts (
        id INT AUTO_INCREMENT PRIMARY KEY,
        email VARCHAR(255) NOT NULL,
        status ENUM('success', 'failed') NOT NULL,
        ip_address VARCHAR(45),
        attempt_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        error_message VARCHAR(255)
      )
    `);
    console.log('Login attempts table created or already exists');

    await dbConnection.end();
    console.log('Database initialization completed successfully');
  } catch (error) {
    console.error('Error initializing database:', error);
    process.exit(1);
  }
}

module.exports = initializeDatabase;
