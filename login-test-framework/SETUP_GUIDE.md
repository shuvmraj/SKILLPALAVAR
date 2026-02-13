# Login Form Testing Framework - Complete Setup and Execution Guide

## Project Overview

This is a comprehensive testing framework for a Login Form that includes:
- **Frontend:** React-based login form with client-side validation
- **Backend:** Node.js with Express and MySQL database
- **Testing:** Selenium with Java for automated testing
- **Security:** SQL injection prevention, password strength validation, XSS protection

---

## Directory Structure

```
login-test-framework/
├── frontend/                      # React login form
│   ├── public/
│   │   └── index.html
│   ├── src/
│   │   ├── index.js
│   │   ├── App.js
│   │   ├── App.css
│   │   └── components/
│   │       ├── LoginForm.js
│   │       └── LoginForm.css
│   └── package.json
│
├── backend/                       # Node.js backend API
│   ├── config/
│   │   ├── database.js
│   │   └── initDatabase.js
│   ├── routes/
│   │   └── auth.js
│   ├── server.js
│   ├── package.json
│   └── .env
│
├── selenium-tests/                # Selenium Java tests
│   ├── pom.xml
│   ├── src/test/java/com/login/test/
│   │   ├── base/
│   │   │   └── BaseTest.java
│   │   ├── pages/
│   │   │   └── LoginPage.java
│   │   └── tests/
│   │       ├── LoginFunctionalTest.java
│   │       ├── LoginBoundaryTest.java
│   │       ├── LoginSecurityTest.java
│   │       └── LoginEdgeCaseTest.java
│   └── target/
│
├── test-reports/                  # Test reports and results
│   ├── test-results.html
│   ├── pass-fail-report.md
│   └── junit-reports/
│
├── TEST_CASES.md                  # Detailed test case documentation
├── SETUP_GUIDE.md                 # This file
└── README.md                       # Project overview
```

---

## Prerequisites

### System Requirements
- **OS:** macOS, Linux, or Windows
- **RAM:** Minimum 4GB
- **Disk Space:** 500MB minimum

### Software Requirements

#### For Frontend & Backend:
- **Node.js:** v14.0.0 or higher
- **npm:** v6.0.0 or higher

#### For Backend:
- **MySQL:** v5.7 or higher
- **MySQL Client:** For manual database configuration (optional)

#### For Testing:
- **Java JDK:** v11 or higher
- **Maven:** v3.6.0 or higher
- **Chrome/Chromium:** Latest version (for Selenium)

---

## Installation and Setup

### Step 1: Install System Dependencies

#### macOS (using Homebrew):
```bash
# Install Node.js
brew install node

# Install MySQL
brew install mysql

# Install Java
brew install openjdk@11

# Install Maven
brew install maven
```

#### Ubuntu/Debian:
```bash
sudo apt-get update
sudo apt-get install nodejs npm mysql-server default-jdk maven
```

---

### Step 2: Setup Backend (Node.js + MySQL)

#### 2.1 Start MySQL Server
```bash
# macOS
brew services start mysql

# Ubuntu/Debian
sudo systemctl start mysql

# Or use MySQL directly
mysql.server start
```

#### 2.2 Configure Database User
```bash
# Login to MySQL
mysql -u root -p

# Run these commands:
CREATE USER 'root'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

#### 2.3 Install Backend Dependencies
```bash
cd /Users/shubhamraj/Desktop/SKILLPALAVAR/login-test-framework/backend
npm install
```

#### 2.4 Configure Environment
The `.env` file is already configured. Verify/update if needed:
```
PORT=5000
DB_HOST=localhost
DB_USER=root
DB_PASSWORD=password
DB_NAME=login_test_db
JWT_SECRET=your-jwt-secret-key-change-in-production
NODE_ENV=development
```

#### 2.5 Start Backend Server
```bash
cd /Users/shubhamraj/Desktop/SKILLPALAVAR/login-test-framework/backend
npm start
```

**Expected Output:**
```
Server is running on port 5000
Database created or already exists
Users table created or already exists
Login attempts table created or already exists
```

---

### Step 3: Setup Frontend (React)

#### 3.1 Install Frontend Dependencies
```bash
cd /Users/shubhamraj/Desktop/SKILLPALAVAR/login-test-framework/frontend
npm install
```

#### 3.2 Start Frontend Server
```bash
npm start
```

**This will:**
- Start the development server on `http://localhost:3000`
- Automatically open your browser
- Show any build errors

---

### Step 4: Setup Testing (Selenium + Java)

#### 4.1 Install Java and Maven (if not already installed)

```bash
# Verify Java installation
java -version

# Verify Maven installation
mvn -version
```

#### 4.2 Download WebDriver (Automatic)
The tests automatically download ChromeDriver via WebDriverManager.

#### 4.3 Run Selenium Tests

```bash
cd /Users/shubhamraj/Desktop/SKILLPALAVAR/login-test-framework/selenium-tests

# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=LoginFunctionalTest

# Run specific test method
mvn test -Dtest=LoginFunctionalTest#testLoginPageLoads

# Run with detailed output
mvn test -X
```

---

## Test Case Categories

### 1. Functional Tests (14 tests)
Tests basic functionality of the login form:
- Page loading
- Field visibility
- Input field behavior
- Form submission
- Error messages
- Validation rules

**Run Command:**
```bash
mvn test -Dtest=LoginFunctionalTest
```

---

### 2. Boundary Value Tests (14 tests)
Tests limits and edge values:
- Minimum/maximum email length
- Minimum/maximum password length
- Special characters in email
- Field length constraints

**Run Command:**
```bash
mvn test -Dtest=LoginBoundaryTest
```

---

### 3. Security Tests (12 tests)
Tests security vulnerabilities:
- SQL injection attempts
- XSS (Cross-Site Scripting) attempts
- Command injection
- LDAP injection
- Password masking
- Session management

**Run Command:**
```bash
mvn test -Dtest=LoginSecurityTest
```

---

### 4. Edge Case Tests (15 tests)
Tests unusual but valid scenarios:
- Unicode characters
- Emoji input
- Control characters
- Case sensitivity
- Multiple special characters
- Very long inputs

**Run Command:**
```bash
mvn test -Dtest=LoginEdgeCaseTest
```

---

## Running All Tests

### Complete Test Suite
```bash
cd /Users/shubhamraj/Desktop/SKILLPALAVAR/login-test-framework/selenium-tests
mvn test
```

This will:
1. Compile all test code
2. Download WebDriver if needed
3. Launch Chrome browser
4. Execute all tests
5. Generate test reports

### Expected Output:
```
[INFO] Tests run: 55, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: XX.XXX s
[INFO] -------------------------------------------------------
[INFO] BUILD SUCCESS
```

---

## Test Reports

### Maven Surefire Reports

After running tests, reports are generated:

```bash
# Default location
target/surefire-reports/

# HTML Report (if Surefire plugins configured)
target/surefire-reports/index.html
```

### Generate HTML Test Report
```bash
mvn surefire-report:report
mvn site
```

Then open: `target/site/surefire-report.html`

---

## Testing Best Practices

### 1. Test Isolation
- Each test is independent
- No test depends on another test's result
- Tests can run in any order

### 2. Clear Naming
- Test names describe what they test
- Format: `test[ComponentName][Scenario]`
- Example: `testEmptyEmailValidation`

### 3. Assert Statements
- Uses AssertJ for readable assertions
- Each assertion has a description
- Multiple assertions show full context on failure

### 4. Page Object Model
- `LoginPage` class encapsulates page interactions
- Tests use the page object, not raw Selenium
- Easy to maintain when UI changes

### 5. Waiting Strategies
- Uses WebDriverWait with explicit waits
- No hardcoded `Thread.sleep()`
- Handles dynamic elements properly

---

## Login Form Validation Rules

### Email Field
- **Required:** Yes
- **Format:** Valid email with @ and domain
- **Max Length:** 255 characters
- **Special Chars:** Allowed within standard email format

### Password Field
- **Required:** Yes
- **Min Length:** 8 characters
- **Max Length:** 128 characters
- **Complexity Requirements:**
  - At least 1 uppercase letter (A-Z)
  - At least 1 lowercase letter (a-z)
  - At least 1 number (0-9)
  - At least 1 special character (@$!%*?&)

### Valid Test Credentials
```
Email: test@example.com
Password: ValidPass@123

Email: user@domain.com
Password: SecurePass@456
```

---

## Security Features Implemented

### Backend Security
✅ SQL Injection Detection (regex-based pattern matching)
✅ Password Hashing (bcryptjs with salt)
✅ JWT Tokens for session management
✅ Login Attempt Logging
✅ Email uniqueness validation
✅ Rate limiting-ready architecture

### Frontend Security
✅ Input validation before submission
✅ Password field masking (type="password")
✅ XSS protection (React handles escaping)
✅ CSRF token ready (can be implemented)
✅ HTTPS ready (configure in production)

---

## Troubleshooting

### Issue: Port 3000 Already in Use
```bash
# Kill process on port 3000
lsof -ti:3000 | xargs kill -9
```

### Issue: Port 5000 Already in Use
```bash
# Kill process on port 5000
lsof -ti:5000 | xargs kill -9
```

### Issue: MySQL Connection Refused
```bash
# Start MySQL server
brew services start mysql

# Or manually
mysql.server start

# Check MySQL status
mysql.server status
```

### Issue: WebDriver Issues
```bash
# Clear WebDriver cache
rm -rf ~/.wdm

# Then rerun tests (will re-download)
mvn clean test
```

### Issue: Tests Timeout
```bash
# Increase timeout in BaseTest.java or run:
mvn test -DargLine="-Xmx256m"
```

---

## Continuous Integration Setup

### GitHub Actions Example
```yaml
name: Login Form Tests

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    
    services:
      mysql:
        image: mysql:5.7
        env:
          MYSQL_ROOT_PASSWORD: password
    
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
        with:
          java-version: '11'
      - uses: actions/setup-node@v2
        with:
          node-version: '14'
      
      - name: Backend Tests
        run: cd backend && npm install && npm start &
      
      - name: Frontend
        run: cd frontend && npm install && npm start &
      
      - name: Selenium Tests
        run: cd selenium-tests && mvn clean test
```

---

## Performance Metrics

### Expected Test Execution Times
- Functional Tests: ~2-3 minutes
- Boundary Tests: ~2-3 minutes
- Security Tests: ~2-3 minutes
- Edge Case Tests: ~2-3 minutes
- **Total Suite:** ~8-12 minutes

---

## Test Coverage

### Components Tested
- ✅ Login Form UI
- ✅ Email validation
- ✅ Password validation
- ✅ Form submission
- ✅ Error messages
- ✅ Security checks
- ✅ Edge cases
- ✅ Boundary conditions

### Coverage Metrics
- **Test Cases:** 55
- **Scenarios Covered:** 100+
- **Security Tests:** SQL Injection (4), XSS (2), Other (6)
- **Boundary Tests:** 14 variations

---

## Next Steps

1. **Extend Testing:**
   - Add integration tests for backend API
   - Add unit tests for validation functions
   - Performance testing with load tools (JMeter)

2. **Improve Security:**
   - Implement CORS whitelist
   - Add rate limiting
   - Implement 2FA
   - Add CSRF tokens

3. **Enhance UI/UX:**
   - Add "Remember Me" functionality
   - Implement password reset flow
   - Add password strength indicator
   - Add social login options

4. **Production Readiness:**
   - Implement HTTPS
   - Add session timeout
   - Database encryption
   - Audit logging

---

## Support and References

### Documentation
- Selenium: https://www.selenium.dev/documentation/
- JUnit 5: https://junit.org/junit5/
- React: https://react.dev/
- Express: https://expressjs.com/

### Tools Used
- WebDriverManager: https://github.com/bonigarcia/webdrivermanager
- AssertJ: https://assertj.org/
- Maven: https://maven.apache.org/

---

## Test Execution Checklist

- [ ] MySQL server running
- [ ] Backend server started (port 5000)
- [ ] Frontend server started (port 3000)
- [ ] Java JDK 11+ installed
- [ ] Maven installed
- [ ] Chrome/Chromium browser installed
- [ ] All dependencies installed
- [ ] Tests compiled without errors
- [ ] Test reports generated

---

**Last Updated:** February 13, 2026  
**Version:** 1.0.0  
**Status:** Ready for Testing
