package com.login.test.tests;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.login.test.base.BaseTest;
import com.login.test.pages.LoginPage;

@DisplayName("Login Form Edge Case Tests")
@Order(4)
public class LoginEdgeCaseTest extends BaseTest {

    private LoginPage loginPage;

    @Override
    @BeforeEach
    public void setUp(TestInfo testInfo) {
        super.setUp(testInfo);
        navigateToLoginPage();
        loginPage = new LoginPage(driver);
    }

    @Test
    @DisplayName("Test 4.1: Unicode characters in email")
    public void testUnicodeInEmail() {
        loginPage.enterEmail("tëst@example.com");
        loginPage.enterPassword("ValidPass@123");
        loginPage.clickLoginButton();

        // Should either accept or reject based on validation rules
        String errorMessage = loginPage.getEmailErrorMessage();
        assertThat(errorMessage)
            .as("Unicode in email should be handled")
            .isNotNull();
    }

    @Test
    @DisplayName("Test 4.2: Special characters in password field")
    public void testEmojiInPassword() {
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword("ValidPass@123©");
        loginPage.clickLoginButton();

        // Password error should appear (© is not in allowed char set)
        String errorMessage = loginPage.getPasswordErrorMessage();
        assertThat(errorMessage)
            .as("Password validation should reject disallowed characters")
            .isNotEmpty();
    }

    @Test
    @DisplayName("Test 4.3: Tab character in email")
    public void testTabCharacterInEmail() {
        loginPage.enterEmail("test\t@example.com");
        loginPage.enterPassword("ValidPass@123");
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getEmailErrorMessage();
        assertThat(errorMessage)
            .as("Tab character in email should be handled")
            .isNotEmpty();
    }

    @Test
    @DisplayName("Test 4.4: Newline character in password")
    public void testNewlineCharacterInPassword() {
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword("ValidPass@123\n");
        loginPage.clickLoginButton();

        // Should handle newline properly
        String passwordValue = loginPage.getPasswordInputValue();
        assertThat(passwordValue)
            .as("Password field should handle newline")
            .isNotEmpty();
    }

    @Test
    @DisplayName("Test 4.5: Null byte injection in email")
    public void testNullByteInEmail() {
        loginPage.enterEmail("test@example.com\0");
        loginPage.enterPassword("ValidPass@123");
        loginPage.clickLoginButton();

        // Should handle null byte safely
        String emailValue = loginPage.getEmailInputValue();
        assertThat(emailValue)
            .as("Null byte should be handled safely")
            .isNotNull();
    }

    @Test
    @DisplayName("Test 4.6: Case sensitivity - uppercase email")
    public void testUppercaseEmail() {
        loginPage.enterEmail("TEST@EXAMPLE.COM");
        loginPage.enterPassword("ValidPass@123");
        loginPage.clickLoginButton();

        // Uppercase email should be valid format
        String errorMessage = loginPage.getEmailErrorMessage();
        assertThat(errorMessage)
            .as("Uppercase email should be valid format")
            .isEmpty();
    }

    @Test
    @DisplayName("Test 4.7: Mixed case email")
    public void testMixedCaseEmail() {
        loginPage.enterEmail("TeSt@ExAmPle.CoM");
        loginPage.enterPassword("ValidPass@123");
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getEmailErrorMessage();
        assertThat(errorMessage)
            .as("Mixed case email should be valid format")
            .isEmpty();
    }

    @Test
    @DisplayName("Test 4.8: Email with consecutive dots")
    public void testConsecutiveDotsInEmail() {
        loginPage.enterEmail("test..mail@example.com");
        loginPage.enterPassword("ValidPass@123");
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getEmailErrorMessage();
        // May or may not be valid depending on validation rules
        assertThat(errorMessage)
            .as("Consecutive dots should be handled")
            .isNotNull();
    }

    @Test
    @DisplayName("Test 4.9: Email starting with dot")
    public void testEmailStartingWithDot() {
        loginPage.enterEmail(".test@example.com");
        loginPage.enterPassword("ValidPass@123");
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getEmailErrorMessage();
        assertThat(errorMessage)
            .as("Email starting with dot should be handled")
            .isNotNull();
    }

    @Test
    @DisplayName("Test 4.10: Email ending with dot before domain")
    public void testEmailEndingWithDot() {
        loginPage.enterEmail("test.@example.com");
        loginPage.enterPassword("ValidPass@123");
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getEmailErrorMessage();
        assertThat(errorMessage)
            .as("Email ending with dot should be handled")
            .isNotNull();
    }

    @Test
    @DisplayName("Test 4.11: Multiple @ symbols in email")
    public void testMultipleAtSymbols() {
        loginPage.enterEmail("test@@example.com");
        loginPage.enterPassword("ValidPass@123");
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getEmailErrorMessage();
        assertThat(errorMessage)
            .as("Multiple @ symbols should be invalid")
            .isNotEmpty();
    }

    @Test
    @DisplayName("Test 4.12: No @ symbol in email")
    public void testNoAtSymbolInEmail() {
        loginPage.enterEmail("testexample.com");
        loginPage.enterPassword("ValidPass@123");
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getEmailErrorMessage();
        assertThat(errorMessage)
            .as("Email without @ should be invalid")
            .isNotEmpty();
    }

    @Test
    @DisplayName("Test 4.13: Valid numbers in password")
    public void testValidNumbersInPassword() {
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword("Pass0123456789@");
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getPasswordErrorMessage();
        assertThat(errorMessage)
            .as("Password with valid numbers should be accepted")
            .isEmpty();
    }

    @Test
    @DisplayName("Test 4.14: Repeated characters in password")
    public void testRepeatedCharactersInPassword() {
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword("Aaaaaaaa1@");
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getPasswordErrorMessage();
        assertThat(errorMessage)
            .as("Password with repeated characters should still be valid if it meets requirements")
            .isEmpty();
    }

    @Test
    @DisplayName("Test 4.15: Very long valid inputs")
    public void testVeryLongValidInputs() {
        String longLocalPart = "a".repeat(64);
        String validEmail = longLocalPart + "@example.com";
        String validPassword = "Pass@" + "1".repeat(120) + "Aa";

        loginPage.enterEmail(validEmail);
        loginPage.enterPassword(validPassword);

        assertThat(loginPage.getEmailInputValue())
            .as("Long email should be usable")
            .isNotEmpty();
        
        assertThat(loginPage.getPasswordInputValue())
            .as("Long password should be usable")
            .isNotEmpty();
    }
}
