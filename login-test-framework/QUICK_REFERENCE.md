# Quick Reference Guide

## File Structure

```
login-test-framework/
│
├── README.md                          # Project overview
├── SETUP_GUIDE.md                     # Installation instructions
├── TEST_CASES.md                      # Test case specifications
│
├── frontend/                          # React application
│   ├── package.json
│   ├── public/
│   │   └── index.html
│   └── src/
│       ├── index.js
│       ├── App.js
│       ├── App.css
│       └── components/
│           ├── LoginForm.js
│           └── LoginForm.css
│
├── backend/                           # Node.js backend
│   ├── package.json
│   ├── .env
│   ├── server.js
│   ├── config/
│   │   ├── database.js
│   │   └── initDatabase.js
│   └── routes/
│       └── auth.js
│
├── selenium-tests/                    # Java Selenium tests
│   ├── pom.xml
│   └── src/test/java/com/login/test/
│       ├── base/
│       │   └── BaseTest.java
│       ├── pages/
│       │   └── LoginPage.java
│       └── tests/
│           ├── LoginFunctionalTest.java
│           ├── LoginBoundaryTest.java
│           ├── LoginSecurityTest.java
│           └── LoginEdgeCaseTest.java
│
└── test-reports/                      # Test reports
    └── PASS_FAIL_REPORT.md
```

## Command Reference

### Backend Setup
```bash
cd backend
npm install
npm start
```

### Frontend Setup
```bash
cd frontend
npm install
npm start
```

### Run Tests
```bash
cd selenium-tests
mvn clean test
```

### Test Subsets
```bash
mvn test -Dtest=LoginFunctionalTest      # Functional tests
mvn test -Dtest=LoginBoundaryTest        # Boundary tests
mvn test -Dtest=LoginSecurityTest        # Security tests
mvn test -Dtest=LoginEdgeCaseTest        # Edge case tests
```

## URLs

| Service | URL |
|---------|-----|
| Frontend | http://localhost:3000 |
| Backend API | http://localhost:5000 |
| Health Check | http://localhost:5000/health |

## API Endpoints

```
POST /api/auth/register
  Body: { email, password }
  Response: { message, token? }

POST /api/auth/login
  Body: { email, password }
  Response: { message, token, user }

GET /api/auth/login-attempts
  Response: [ { id, email, status, ip_address, attempt_time, error_message } ]
```

## Test Categories

| Category | Tests | Focus Area |
|----------|-------|-----------|
| Functional | 14 | Core form functionality |
| Boundary | 14 | Input limits & constraints |
| Security | 12 | Injection & vulnerability prevention |
| Edge Case | 15 | Unusual but valid scenarios |
| **Total** | **55** | **Complete coverage** |

## Validation Rules

**Email:**
- Required, must be valid format
- Max 255 chars
- Pattern: `[^\\s@]+@[^\\s@]+\\.[^\\s@]+`

**Password:**
- Required, 8-128 chars
- Must contain: UPPERCASE, lowercase, number, special char (@$!%*?&)
- Pattern: `^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$`

## Security Checks

✅ SQL Injection prevention  
✅ XSS protection  
✅ Password hashing (bcryptjs)  
✅ JWT tokens  
✅ Login attempt logging  
✅ Input validation  

## Troubleshooting

| Issue | Solution |
|-------|----------|
| Port 3000 in use | `lsof -ti:3000 \| xargs kill -9` |
| Port 5000 in use | `lsof -ti:5000 \| xargs kill -9` |
| MySQL not running | `brew services start mysql` |
| WebDriver errors | `rm -rf ~/.wdm && mvn clean test` |
| Tests timeout | Check backend is running on port 5000 |

## Test Results Location

```
selenium-tests/target/
├── surefire-reports/        # Test reports
├── site/                     # HTML report
└── test-results.html        # Detailed results
```

## Key Files to Review

1. **TEST_CASES.md** - All 55 test cases with specifications
2. **SETUP_GUIDE.md** - Installation & execution guide
3. **frontend/src/components/LoginForm.js** - Validation logic
4. **backend/routes/auth.js** - Backend validation & security
5. **selenium-tests/src/test/java/.../LoginPageobject** - Page object model

## Performance Notes

- Total runtime: ~8-12 minutes for all 55 tests
- Page load time: < 3 seconds
- Timeout: 10 seconds per interaction
- Browser: Chrome/Chromium

## Maintenance

- Update TEST_CASES.md when adding tests
- Keep dependencies current: `npm outdated`, `mvn dependency:outdated-report`
- Review PASS_FAIL_REPORT.md after each test run
- Document bugs in test-reports/

---

For detailed instructions, see **SETUP_GUIDE.md**  
For test specifications, see **TEST_CASES.md**  
For test results, see **test-reports/PASS_FAIL_REPORT.md**
