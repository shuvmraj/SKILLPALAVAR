# Login Form Testing Framework - Test Case Documentation

## Overview
This document provides comprehensive test case specifications for the Login Form Testing Framework. It covers functional testing, boundary value analysis, security testing, and edge case handling.

---

## 1. FUNCTIONAL TEST CASES

### Test 1.1: Verify Login Page Loads Successfully
**Objective:** Ensure the login page renders without errors  
**Steps:**
1. Navigate to `http://localhost:3000`
2. Verify page loads within reasonable time
3. Check all elements (email field, password field, login button) are visible

**Expected Result:** Page loads successfully with all UI elements visible  
**Pass Criteria:** Page renders in < 3 seconds
**Status:** [_] Pass [_] Fail

---

### Test 1.2: Email and Password Fields Are Visible and Empty
**Objective:** Verify form fields are empty on page load  
**Steps:**
1. Navigate to login page
2. Check email input field
3. Check password input field

**Expected Result:** Both fields are empty and ready for input  
**Pass Criteria:** No pre-filled values  
**Status:** [_] Pass [_] Fail

---

### Test 1.3: Login Button is Visible and Clickable
**Objective:** Ensure login button is available and interactive  
**Steps:**
1. Navigate to login page
2. Verify login button is displayed
3. Verify button text is readable

**Expected Result:** Login button is visible with text "Login"  
**Pass Criteria:** Button is clickable  
**Status:** [_] Pass [_] Fail

---

### Test 1.4: Empty Email Validation
**Objective:** Test validation when email is empty  
**Steps:**
1. Leave email field empty
2. Enter valid password: `Test@1234`
3. Click Login button

**Expected Result:** Error message displayed under email field  
**Error Message:** "Email is required"  
**Pass Criteria:** Error appears immediately  
**Status:** [_] Pass [_] Fail

---

### Test 1.5: Empty Password Validation
**Objective:** Test validation when password is empty  
**Steps:**
1. Enter valid email: `test@example.com`
2. Leave password field empty
3. Click Login button

**Expected Result:** Error message displayed under password field  
**Error Message:** "Password is required"  
**Pass Criteria:** Error appears immediately  
**Status:** [_] Pass [_] Fail

---

### Test 1.6: Both Fields Empty Validation
**Objective:** Test validation when both fields are empty  
**Steps:**
1. Leave both fields empty
2. Click Login button

**Expected Result:** Error messages displayed under both fields  
**Pass Criteria:** Both errors appear  
**Status:** [_] Pass [_] Fail

---

### Test 1.7: Invalid Email Format
**Objective:** Test validation for malformed email  
**Steps:**
1. Enter email: `invalidemail` (no @ symbol)
2. Enter password: `Test@1234`
3. Click Login button

**Expected Result:** Error message under email field  
**Error Message:** "Invalid email format"  
**Pass Criteria:** Error validation works  
**Status:** [_] Pass [_] Fail

---

### Test 1.8: Valid Email Format Acceptance
**Objective:** Test that valid email formats are accepted  
**Steps:**
1. Enter email: `test@example.com`
2. Enter password: `Test@1234`

**Expected Result:** No error under email field  
**Pass Criteria:** No validation error shown  
**Status:** [_] Pass [_] Fail

---

### Test 1.9: Short Password Validation
**Objective:** Test password length validation  
**Steps:**
1. Enter email: `test@example.com`
2. Enter password: `Short@1` (7 characters)
3. Click Login button

**Expected Result:** Error message displayed  
**Error Message:** "Password must be at least 8 characters"  
**Pass Criteria:** Length validation works  
**Status:** [_] Pass [_] Fail

---

### Test 1.10: Password Without Uppercase Letter
**Objective:** Test password complexity validation  
**Steps:**
1. Enter email: `test@example.com`
2. Enter password: `valid@pass1` (no uppercase)
3. Click Login button

**Expected Result:** Error message displayed  
**Error Message:** "must contain uppercase"  
**Pass Criteria:** Complexity check works  
**Status:** [_] Pass [_] Fail

---

### Test 1.11: Password Without Lowercase Letter
**Objective:** Test password complexity validation  
**Steps:**
1. Enter email: `test@example.com`
2. Enter password: `VALID@PASS1` (no lowercase)
3. Click Login button

**Expected Result:** Error message displayed  
**Error Message:** "must contain lowercase"  
**Pass Criteria:** Complexity check works  
**Status:** [_] Pass [_] Fail

---

### Test 1.12: Password Without Number
**Objective:** Test password complexity validation  
**Steps:**
1. Enter email: `test@example.com`
2. Enter password: `ValidPass@` (no number)
3. Click Login button

**Expected Result:** Error message displayed  
**Error Message:** "must contain number"  
**Pass Criteria:** Complexity check works  
**Status:** [_] Pass [_] Fail

---

### Test 1.13: Password Without Special Character
**Objective:** Test password complexity validation  
**Steps:**
1. Enter email: `test@example.com`
2. Enter password: `ValidPass123` (no special char)
3. Click Login button

**Expected Result:** Error message displayed  
**Error Message:** "must contain special character"  
**Pass Criteria:** Complexity check works  
**Status:** [_] Pass [_] Fail

---

### Test 1.14: Valid Password Acceptance
**Objective:** Test that valid password is accepted  
**Steps:**
1. Enter email: `test@example.com`
2. Enter password: `ValidPass@123`

**Expected Result:** No error under password field  
**Pass Criteria:** No validation error shown  
**Status:** [_] Pass [_] Fail

---

## 2. BOUNDARY VALUE ANALYSIS TEST CASES

### Test 2.1: Minimum Valid Email Length
**Objective:** Test minimum email length acceptance  
**Test Data:** Email = `a@b.c` (5 characters)  
**Steps:**
1. Enter email: `a@b.c`
2. Enter password: `ValidPass@123`
3. Verify no error

**Expected Result:** No validation error  
**Pass Criteria:** Email accepted  
**Status:** [_] Pass [_] Fail

---

### Test 2.2: Maximum Valid Email Length
**Objective:** Test maximum email length acceptance  
**Test Data:** Email = 255 characters  
**Steps:**
1. Enter 255-character valid email
2. Enter password: `ValidPass@123`
3. Verify no error for format

**Expected Result:** Input accepted up to 255 characters  
**Pass Criteria:** Input field limit works  
**Status:** [_] Pass [_] Fail

---

### Test 2.3: Exceed Maximum Email Length
**Objective:** Test email field rejects > 255 characters  
**Test Data:** Email = 256+ characters  
**Steps:**
1. Attempt to enter > 255 character email
2. Check input field value

**Expected Result:** Input limited to 255 characters  
**Pass Criteria:** Field prevents exceeding limit  
**Status:** [_] Pass [_] Fail

---

### Test 2.4: Minimum Valid Password Length
**Objective:** Test 8-character password acceptance  
**Test Data:** Password = `Valid@12` (8 characters)  
**Steps:**
1. Enter email: `test@example.com`
2. Enter password: `Valid@12`
3. Click Login

**Expected Result:** No length error  
**Pass Criteria:** 8-char password accepted  
**Status:** [_] Pass [_] Fail

---

### Test 2.5: Less Than Minimum Password Length
**Objective:** Test 7-character password rejection  
**Test Data:** Password = `Valid@1` (7 characters)  
**Steps:**
1. Enter email: `test@example.com`
2. Enter password: `Valid@1`
3. Click Login

**Expected Result:** Error message shown  
**Pass Criteria:** Password rejected  
**Status:** [_] Pass [_] Fail

---

### Test 2.6: Maximum Valid Password Length
**Objective:** Test 128-character password  
**Test Data:** Password = 128 characters total  
**Steps:**
1. Enter long valid password (128 chars)
2. Verify accepted

**Expected Result:** Password accepted  
**Pass Criteria:** 128-char limit works  
**Status:** [_] Pass [_] Fail

---

### Test 2.7-2.14: Email Special Characters
**Objective:** Test email format with valid special characters  
**Test Cases:**
- 2.7: Email with `+` → `test+tag@example.com`
- 2.8: Email with `.` → `first.last@example.com`
- 2.9: Email with `_` → `test_user@example.com`
- 2.10: Email with `-` → `test-user@example.com`

**Expected Result:** All valid emails accepted  
**Pass Criteria:** No format errors  
**Status:** [_] Pass [_] Fail

---

## 3. SECURITY TEST CASES

### Test 3.1: SQL Injection - Basic OR Statement
**Objective:** Prevent SQL injection attacks  
**Test Data:** Email = `test' OR '1'='1`  
**Steps:**
1. Enter email: `test' OR '1'='1`
2. Enter password: `ValidPass@123`
3. Click Login

**Expected Result:** Suspicious input detected error  
**Pass Criteria:** Injection attempt blocked  
**Status:** [_] Pass [_] Fail

---

### Test 3.2: SQL Injection - DROP TABLE
**Objective:** Prevent destructive SQL injection  
**Test Data:** Email = `'; DROP TABLE users; --`  
**Steps:**
1. Enter dangerous SQL statement in email
2. Click Login

**Expected Result:** Error message and action blocked  
**Pass Criteria:** Injection blocked  
**Status:** [_] Pass [_] Fail

---

### Test 3.3: SQL Injection - UNION SELECT
**Objective:** Prevent UNION-based SQL injection  
**Test Data:** Email = `test' UNION SELECT * FROM users--`  
**Steps:**
1. Enter union-based injection
2. Click Login

**Expected Result:** Injection detected and blocked  
**Pass Criteria:** Attack prevented  
**Status:** [_] Pass [_] Fail

---

### Test 3.4: SQL Injection in Password
**Objective:** Prevent SQL injection in password field  
**Test Data:** Password = `Pass' OR '1'='1`  
**Steps:**
1. Enter email: `test@example.com`
2. Enter password with SQL injection
3. Click Login

**Expected Result:** Injection blocked  
**Pass Criteria:** Defense works  
**Status:** [_] Pass [_] Fail

---

### Test 3.5: XSS - Script Tag in Email
**Objective:** Prevent script injection  
**Test Data:** Email = `<script>alert('XSS')</script>@example.com`  
**Steps:**
1. Enter email with script tag
2. Click Login

**Expected Result:** Invalid format or injection blocked  
**Pass Criteria:** XSS prevented  
**Status:** [_] Pass [_] Fail

---

### Test 3.6-3.9: Various Injection Attempts
**Test Cases:**
- 3.6: XSS event handler in password
- 3.7: Command injection in email
- 3.8: LDAP injection with wildcards
- 3.9: NoSQL injection pattern

**Expected Result:** All blocked/rejected  
**Pass Criteria:** Injection attempts handled  
**Status:** [_] Pass [_] Fail

---

### Test 3.10: Password Field Masking
**Objective:** Ensure password privacy  
**Steps:**
1. Enter password in password field
2. Inspect input field type attribute

**Expected Result:** Type attribute = "password"  
**Pass Criteria:** Characters masked on screen  
**Status:** [_] Pass [_] Fail

---

### Test 3.11: No Data Leakage in Email Field
**Objective:** Verify email handling is secure  
**Steps:**
1. Enter email: `sensitive@example.com`
2. Check input value property

**Expected Result:** Email accessible to backend only  
**Pass Criteria:** No visible leakage  
**Status:** [_] Pass [_] Fail

---

### Test 3.12: Session Not Reused After Failed Login
**Objective:** Prevent session fixation  
**Steps:**
1. Enter wrong credentials
2. See error message
3. Enter new email
4. Verify form is interactive

**Expected Result:** Form resets properly  
**Pass Criteria:** No stale session  
**Status:** [_] Pass [_] Fail

---

## 4. EDGE CASE TEST CASES

### Test 4.1: Unicode Characters in Email
**Objective:** Handle international characters  
**Test Data:** Email = `tëst@example.com`  
**Steps:**
1. Enter email with unicode char
2. Click Login

**Expected Result:** Either accepted or rejected with error  
**Pass Criteria:** Handled gracefully  
**Status:** [_] Pass [_] Fail

---

### Test 4.2-4.5: Special Characters/Control Characters
**Test Cases:**
- 4.2: Emoji in password
- 4.3: Tab character in email
- 4.4: Newline in password
- 4.5: Null byte injection

**Expected Result:** All handled safely  
**Pass Criteria:** No crashes/malfunction  
**Status:** [_] Pass [_] Fail

---

### Test 4.6-4.9: Case Sensitivity
**Test Cases:**
- 4.6: UPPERCASE email
- 4.7: MiXeD CaSe email
- 4.8: Consecutive dots: `test..mail@example.com`
- 4.9: Email starting with dot: `.test@example.com`

**Expected Result:** Handled consistently  
**Pass Criteria:** Validation logic works  
**Status:** [_] Pass [_] Fail

---

### Test 4.10-4.12: Email Format Edge Cases
**Test Cases:**
- 4.10: Email ending with dot: `test.@example.com`
- 4.11: Multiple @ symbols: `test@@example.com`
- 4.12: No @ symbol: `testexample.com`

**Expected Result:** All invalid emails rejected  
**Pass Criteria:** Validation works  
**Status:** [_] Pass [_] Fail

---

### Test 4.13-4.15: Password Content Variations
**Test Cases:**
- 4.13: Valid numbers: `Pass0123456789@`
- 4.14: Repeated characters: `Aaaaaaaa1@`
- 4.15: Very long valid inputs: 128-char password

**Expected Result:** Valid formats accepted  
**Pass Criteria:** Content validation works  
**Status:** [_] Pass [_] Fail

---

## Test Summary Statistics

| Category | Number of Tests |
|----------|-----------------|
| Functional Tests | 14 |
| Boundary Value Tests | 14 |
| Security Tests | 12 |
| Edge Case Tests | 15 |
| **TOTAL** | **55** |

---

## Pass/Fail Report Template

```
Test Run Date: [Date]
Tester: [Name]
Environment: [Details]
Browser: Chrome/Firefox/Safari

SUMMARY:
- Total Tests: 55
- Passed: [___]
- Failed: [___]
- Pass Rate: [___]%

FAILED TESTS:
[List any failed tests with error details]

NOTES:
[Any additional observations]
```

---

## Test Execution Guidelines

1. **Setup Requirements:**
   - React frontend running on `http://localhost:3000`
   - Node.js backend running on `http://localhost:5000`
   - MySQL database configured and initialized

2. **Test Execution Order:**
   - Run functional tests first (Suite 1)
   - Then boundary tests (Suite 2)
   - Then security tests (Suite 3)
   - Finally edge case tests (Suite 4)

3. **Pass Criteria:**
   - All assertions must pass
   - Expected error messages must appear
   - No console errors or warnings
   - Page response time < 3 seconds

4. **Failure Handling:**
   - Document error message text
   - Take screenshot of the error
   - Record browser console logs
   - Note any environmental issues

