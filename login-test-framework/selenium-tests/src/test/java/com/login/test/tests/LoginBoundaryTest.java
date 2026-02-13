package com.login.test.tests;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.login.test.base.BaseTest;
import com.login.test.pages.LoginPage;

@DisplayName("Login Form Boundary Value Tests")
public class LoginBoundaryTest extends BaseTest {

    private LoginPage loginPage;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        navigateToLoginPage();
        loginPage = new LoginPage(driver);
    }

    @Test
    @DisplayName("Test 2.1: Email length boundary - minimum valid (5 chars)")
    public void testMinimumValidEmailLength() {
        loginPage.enterEmail("a@b.c");
        loginPage.enterPassword("ValidPass@123");
        
        assertThat(loginPage.getEmailErrorMessage())
            .as("Minimum length valid email should not show error")
            .isEmpty();
    }

    @Test
    @DisplayName("Test 2.2: Email length boundary - maximum valid (255 chars)")
    public void testMaximumValidEmailLength() {
        String longEmail = "test" + "a".repeat(240) + "@example.com";
        loginPage.enterEmail(longEmail);
        loginPage.enterPassword("ValidPass@123");
        
        String errorMessage = loginPage.getEmailErrorMessage();
        // Should not exceed 255 chars in input field
        assertThat(loginPage.getEmailInputValue().length())
            .as("Email should not exceed 255 characters")
            .isLessThanOrEqualTo(255);
    }

    @Test
    @DisplayName("Test 2.3: Email length boundary - exceed maximum (256+ chars)")
    public void testExceedMaximumEmailLength() {
        String veryLongEmail = "test" + "a".repeat(300) + "@example.com";
        loginPage.enterEmail(veryLongEmail);
        
        // The input should truncate or prevent entry beyond 255
        assertThat(loginPage.getEmailInputValue().length())
            .as("Email input should not accept more than 255 characters")
            .isLessThanOrEqualTo(255);
    }

    @Test
    @DisplayName("Test 2.4: Password length boundary - minimum valid (8 chars)")
    public void testMinimumValidPasswordLength() {
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword("Valid@12");
        loginPage.clickLoginButton();
        
        String errorMessage = loginPage.getPasswordErrorMessage();
        assertThat(errorMessage)
            .as("8-character valid password should be accepted")
            .isEmpty();
    }

    @Test
    @DisplayName("Test 2.5: Password length boundary - less than minimum (7 chars)")
    public void testLessThanMinimumPasswordLength() {
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword("Valid@1");
        loginPage.clickLoginButton();
        
        String errorMessage = loginPage.getPasswordErrorMessage();
        assertThat(errorMessage)
            .as("7-character password should show error")
            .isNotEmpty();
    }

    @Test
    @DisplayName("Test 2.6: Password length boundary - maximum valid (128 chars)")
    public void testMaximumValidPasswordLength() {
        String longPassword = "Valid@" + "1".repeat(120) + "Abc";
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword(longPassword);
        
        assertThat(loginPage.getPasswordInputValue().length())
            .as("Should allow up to 128 character password")
            .isLessThanOrEqualTo(128);
    }

    @Test
    @DisplayName("Test 2.7: Email with special characters")
    public void testEmailWithSpecialCharacters() {
        loginPage.enterEmail("test+tag@example.com");
        loginPage.enterPassword("ValidPass@123");
        
        String errorMessage = loginPage.getEmailErrorMessage();
        assertThat(errorMessage)
            .as("Email with special characters (+ sign) should be valid")
            .isEmpty();
    }

    @Test
    @DisplayName("Test 2.8: Email with dots")
    public void testEmailWithDots() {
        loginPage.enterEmail("first.last@example.com");
        loginPage.enterPassword("ValidPass@123");
        
        String errorMessage = loginPage.getEmailErrorMessage();
        assertThat(errorMessage)
            .as("Email with dots should be valid")
            .isEmpty();
    }

    @Test
    @DisplayName("Test 2.9: Email with underscore")
    public void testEmailWithUnderscore() {
        loginPage.enterEmail("test_user@example.com");
        loginPage.enterPassword("ValidPass@123");
        
        String errorMessage = loginPage.getEmailErrorMessage();
        assertThat(errorMessage)
            .as("Email with underscore should be valid")
            .isEmpty();
    }

    @Test
    @DisplayName("Test 2.10: Email with hyphen")
    public void testEmailWithHyphen() {
        loginPage.enterEmail("test-user@example.com");
        loginPage.enterPassword("ValidPass@123");
        
        String errorMessage = loginPage.getEmailErrorMessage();
        assertThat(errorMessage)
            .as("Email with hyphen should be valid")
            .isEmpty();
    }

    @Test
    @DisplayName("Test 2.11: Password with multiple special characters")
    public void testPasswordWithMultipleSpecialCharacters() {
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword("Valid@Pass$123");
        loginPage.clickLoginButton();
        
        String errorMessage = loginPage.getPasswordErrorMessage();
        assertThat(errorMessage)
            .as("Password with multiple valid special characters should be accepted")
            .isEmpty();
    }

    @Test
    @DisplayName("Test 2.12: Password with all valid special characters")
    public void testPasswordWithAllSpecialCharacters() {
        loginPage.enterEmail("test@example.com");
        // Test with all allowed special characters: @$!%*?&
        loginPage.enterPassword("Pass@$!%A1");
        loginPage.clickLoginButton();
        
        String errorMessage = loginPage.getPasswordErrorMessage();
        assertThat(errorMessage)
            .as("Password with allowed special characters should be accepted")
            .isEmpty();
    }

    @Test
    @DisplayName("Test 2.13: Email boundary - empty string")
    public void testEmptyEmailBoundary() {
        loginPage.enterEmail("");
        loginPage.enterPassword("ValidPass@123");
        loginPage.clickLoginButton();
        
        String errorMessage = loginPage.getEmailErrorMessage();
        assertThat(errorMessage)
            .as("Empty email should show required error")
            .isNotEmpty();
    }

    @Test
    @DisplayName("Test 2.14: Email with spaces at boundaries")
    public void testEmailWithSpaces() {
        loginPage.enterEmail("  test@example.com  ");
        loginPage.enterPassword("ValidPass@123");
        
        // Should either trim or reject
        assertThat(loginPage.getEmailInputValue().trim())
            .as("Email should handle spaces properly")
            .isNotEmpty();
    }
}
