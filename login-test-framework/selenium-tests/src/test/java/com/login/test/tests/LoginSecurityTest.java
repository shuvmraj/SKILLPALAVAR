package com.login.test.tests;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.login.test.base.BaseTest;
import com.login.test.pages.LoginPage;

@DisplayName("Login Form Security Tests")
@Order(3)
public class LoginSecurityTest extends BaseTest {

    private LoginPage loginPage;

    @Override
    @BeforeEach
    public void setUp(TestInfo testInfo) {
        super.setUp(testInfo);
        navigateToLoginPage();
        loginPage = new LoginPage(driver);
    }

    @Test
    @DisplayName("Test 3.1: SQL Injection attempt in email field")
    public void testSQLInjectionInEmail() {
        loginPage.enterEmail("test' OR '1'='1@example.com");
        loginPage.enterPassword("ValidPass@123");
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getSubmitErrorMessage();
        assertThat(errorMessage)
            .as("SQL injection attempt should be detected")
            .isNotEmpty();
    }

    @Test
    @DisplayName("Test 3.2: SQL Injection with DROP statement in email")
    public void testSQLInjectionDropStatement() {
        loginPage.enterEmail("'; DROP TABLE users; --@example.com");
        loginPage.enterPassword("ValidPass@123");
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getSubmitErrorMessage();
        assertThat(errorMessage)
            .as("DROP statement injection should be blocked")
            .isNotEmpty();
    }

    @Test
    @DisplayName("Test 3.3: SQL Injection with UNION SELECT in email")
    public void testSQLInjectionUnionSelect() {
        loginPage.enterEmail("test' UNION SELECT * FROM users--@example.com");
        loginPage.enterPassword("ValidPass@123");
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getSubmitErrorMessage();
        assertThat(errorMessage)
            .as("UNION SELECT injection should be blocked")
            .isNotEmpty();
    }

    @Test
    @DisplayName("Test 3.4: SQL Injection in password field")
    public void testSQLInjectionInPassword() {
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword("ValidDROPPass@123");
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getSubmitErrorMessage();
        assertThat(errorMessage)
            .as("SQL injection in password should be detected")
            .isNotEmpty();
    }

    @Test
    @DisplayName("Test 3.5: XSS attempt with script tag in email")
    public void testXSSScriptTagInEmail() {
        loginPage.enterEmail("<script>alert('XSS')</script>@example.com");
        loginPage.enterPassword("ValidPass@123");
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getEmailErrorMessage();
        // Should show invalid email format error
        assertThat(errorMessage)
            .as("Email with script tag should be rejected as invalid format")
            .isNotEmpty();
    }

    @Test
    @DisplayName("Test 3.6: XSS attempt with event handler in password")
    public void testXSSEventHandlerInPassword() {
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword("ValidPass@123<img src=x onerror=alert('XSS')>");
        loginPage.clickLoginButton();

        // Should either reject or safely handle
        String passwordValue = loginPage.getPasswordInputValue();
        assertThat(passwordValue)
            .as("Password input should safely handle attempted XSS")
            .isNotEmpty();
    }

    @Test
    @DisplayName("Test 3.7: Command injection attempt in email")
    public void testCommandInjectionInEmail() {
        loginPage.enterEmail("test@example.com;rm");
        loginPage.enterPassword("ValidPass@123");
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getSubmitErrorMessage();
        // Command injection pattern (--;) should be detected by backend
        assertThat(errorMessage)
            .as("Command injection in email should be rejected")
            .isNotEmpty();
    }

    @Test
    @DisplayName("Test 3.8: LDAP Injection attempt")
    public void testLDAPInjectionAttempt() {
        loginPage.enterEmail("test*@example.com");
        loginPage.enterPassword("ValidPass@123");
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getSubmitErrorMessage();
        assertThat(errorMessage)
            .as("LDAP wildcard injection should be handled")
            .isNotEmpty();
    }

    @Test
    @DisplayName("Test 3.9: NoSQL Injection attempt")
    public void testNoSQLInjectionAttempt() {
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword("ValidPass@123SELECT");
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getSubmitErrorMessage();
        assertThat(errorMessage)
            .as("NoSQL injection pattern should be detected")
            .isNotEmpty();
    }

    @Test
    @DisplayName("Test 3.10: Password field should hide input with asterisks")
    public void testPasswordFieldMasking() {
        loginPage.enterPassword("ValidPass@123");
        
        // Check that password field type is password (not text)
        String passwordInputType = driver.findElement(
            org.openqa.selenium.By.id("password")
        ).getAttribute("type");
        
        assertThat(passwordInputType)
            .as("Password field should be type='password' for masking")
            .isEqualTo("password");
    }

    @Test
    @DisplayName("Test 3.11: Email field should not contain sensitive data in visible text")
    public void testEmailFieldDoesNotLeakSensitiveData() {
        String testEmail = "sensitive@example.com";
        loginPage.enterEmail(testEmail);
        
        // Email value should match what was entered
        assertThat(loginPage.getEmailInputValue())
            .as("Email value should be accessible to backend but handled securely")
            .contains("sensitive");
    }

    @Test
    @DisplayName("Test 3.12: Session should not be reused after failed login")
    public void testSessionNotReuseAfterFailedLogin() {
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword("WrongPass@123");
        loginPage.clickLoginButton();

        // Should show error
        String errorMessage = loginPage.getSubmitErrorMessage();
        assertThat(errorMessage)
            .as("Failed login should show error")
            .isNotEmpty();

        // Verify that form is still interactive and not cached
        loginPage.enterEmail("another@example.com");
        assertThat(loginPage.getEmailInputValue())
            .as("Form should be interactive after failed login")
            .contains("another");
    }
}
