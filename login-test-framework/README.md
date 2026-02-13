# Login Form Testing Framework

## Overview

A comprehensive testing framework for validating login form functionality, security, and reliability. This framework includes a React frontend, Node.js backend, MySQL database, and automated Selenium tests with Java.

## Key Features

✅ **Functional Testing** - 14 test cases for core functionality  
✅ **Boundary Value Analysis** - 14 test cases for limits and edge values  
✅ **Security Testing** - 12 test cases for injection attacks and vulnerabilities  
✅ **Edge Case Testing** - 15 test cases for unusual scenarios  

**Total: 55 Comprehensive Test Cases**

## Quick Start

### 1. Start Backend
```bash
cd backend
npm install
npm start
```

### 2. Start Frontend
```bash
cd frontend
npm install
npm start
```

### 3. Run Tests
```bash
cd selenium-tests
mvn test
```

## Technology Stack

| Component | Technology |
|-----------|-----------|
| Frontend | React 18.2 |
| Backend | Node.js + Express 4.18 |
| Database | MySQL 5.7+ |
| Testing | Selenium 4.10 + JUnit 5 |
| Build | Maven 3.6+ |

## Project Structure

```
login-test-framework/
├── frontend/              # React login form
├── backend/               # Node.js API server
├── selenium-tests/        # Automated test suite
├── test-reports/          # Test results
├── TEST_CASES.md         # Detailed test specifications
├── SETUP_GUIDE.md        # Installation & setup
└── README.md             # This file
```

## Test Coverage

### ✅ Functional Tests
- Page loading
- Form field validation
- Empty field validation
- Email format validation
- Password strength validation
- Error message display

### ✅ Boundary Value Tests
- Minimum/maximum field lengths
- Special characters in fields
- Field length restrictions
- Character encoding handling

### ✅ Security Tests
- SQL Injection prevention (4 test cases)
- XSS (Cross-Site Scripting) prevention
- Command injection prevention
- LDAP injection prevention
- NoSQL injection prevention
- Password masking/privacy
- Session management

### ✅ Edge Case Tests
- Unicode character handling
- Emoji input handling
- Control character handling
- Case sensitivity
- Email format variations
- Very long inputs

## Validation Rules

### Email
- Required field
- Valid email format with @domain
- Maximum 255 characters
- Trimmed before processing

### Password
- Required field
- Minimum 8 characters
- Maximum 128 characters
- Must contain: uppercase, lowercase, number, special character
- Allowed special characters: `@$!%*?&`

## Test Execution

### Run All Tests
```bash
mvn test
```

### Run Specific Test Suite
```bash
# Functional tests only
mvn test -Dtest=LoginFunctionalTest

# Boundary tests only
mvn test -Dtest=LoginBoundaryTest

# Security tests only
mvn test -Dtest=LoginSecurityTest

# Edge case tests only
mvn test -Dtest=LoginEdgeCaseTest
```

### Generate Reports
```bash
mvn surefire-report:report
mvn site
# Open: target/site/surefire-report.html
```

## Security Features

- SQL Injection detection (regex-based pattern matching)
- Password hashing with bcryptjs
- JWT token-based authentication
- Login attempt logging
- Email uniqueness validation
- XSS protection (React built-in)

## Performance Metrics

- **Total Test Cases:** 55
- **Expected Runtime:** 8-12 minutes
- **Browser:** Chrome/Chromium
- **Timeout:** 10 seconds per action

## Documentation

- **[TEST_CASES.md](TEST_CASES.md)** - Detailed test specifications with pass/fail criteria
- **[SETUP_GUIDE.md](SETUP_GUIDE.md)** - Complete installation and setup instructions
- **[PASS_FAIL_REPORT.md](test-reports/PASS_FAIL_REPORT.md)** - Test results template

## Prerequisites

- **Node.js** v14.0.0+
- **Java JDK** v11.0.0+
- **Maven** v3.6.0+
- **MySQL** v5.7.0+
- **Chrome/Chromium** browser

## Common Issues & Solutions

### MySQL Connection Error
```bash
# Start MySQL service
brew services start mysql  # macOS
sudo systemctl start mysql  # Linux

# Verify running
mysql -u root -p
```

### Port Already in Use
```bash
# Kill process on port 3000
lsof -ti:3000 | xargs kill -9

# Kill process on port 5000
lsof -ti:5000 | xargs kill -9
```

### WebDriver Issues
```bash
# Clear WebDriver cache and re-download
rm -rf ~/.wdm
mvn clean test
```

## Test Data

### Valid Credentials
```
Email: test@example.com
Password: ValidPass@123
```

### Invalid Inputs for Testing
```
SQL Injection: test' OR '1'='1
XSS Attack: <script>alert('XSS')</script>
Invalid Email: notanemail
Weak Password: weak123
```

## API Endpoints

### Authentication
- **POST** `/api/auth/register` - Register new user
- **POST** `/api/auth/login` - Authenticate user
- **GET** `/api/auth/login-attempts` - View login history

### Health Check
- **GET** `/health` - Server status

## Contributing

1. Add new test methods to appropriate test class
2. Follow naming convention: `test[Scenario][Expected]`
3. Add clear assertion descriptions
4. Update TEST_CASES.md with new test specifications
5. Run full test suite before submitting

## Future Enhancements

- [ ] Password reset functionality
- [ ] Two-factor authentication (2FA)
- [ ] Rate limiting on login attempts
- [ ] Account lockout after failed attempts
- [ ] Social authentication (Google, GitHub)
- [ ] Remember me functionality
- [ ] Multi-device session management
- [ ] Audit logging with encrypted storage

## Performance Testing

For load testing, use:
```bash
# JMeter
jmeter -n -t test_plan.jmx -l results.jtl

# Artillery
artillery run load-test.yml
```

## Deployment

### Production Checklist
- [ ] Change JWT_SECRET in .env
- [ ] Enable HTTPS
- [ ] Configure CORS whitelist
- [ ] Enable rate limiting
- [ ] Setup database backups
- [ ] Configure monitoring/logging
- [ ] Run full test suite
- [ ] Review security headers
- [ ] Test on staging environment

## Support

For issues or questions:
1. Check SETUP_GUIDE.md for troubleshooting
2. Review TEST_CASES.md for test specifications
3. Check browser console for errors
4. Review MySQL logs
5. Check backend server logs

## License

MIT License - Feel free to use for educational and commercial purposes

## Version

- **Current Version:** 1.0.0
- **Release Date:** February 13, 2026
- **Status:** Production Ready

---

**Last Updated:** February 13, 2026
