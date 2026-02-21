package com.login.test.tests;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestMethodOrder;

import com.login.test.base.BaseTest;
import com.login.test.pages.LoginPage;

@DisplayName("Login Form Functional Tests")
@Order(1)
@TestMethodOrder(OrderAnnotation.class)
public class LoginFunctionalTest extends BaseTest {

    private LoginPage loginPage;

    @Override
    @BeforeEach
    public void setUp(TestInfo testInfo) {
        super.setUp(testInfo);
        navigateToLoginPage();
        loginPage = new LoginPage(driver);
    }

    @Test
    @Order(1)
    @DisplayName("Test 1.0: Successful login with valid credentials")
    public void testSuccessfulLogin() {
        loginPage.login("Raunit@gmail.com", "Raunit@123");

        assertThat(loginPage.isWelcomePageVisible())
            .as("Successful login should redirect to welcome screen")
            .isTrue();
    }

    @Test
    @DisplayName("Test 1.1: Verify login page loads successfully")
    public void testLoginPageLoads() {
        assertThat(loginPage.isPageLoaded())
            .as("Login page should load successfully")
            .isTrue();
    }

    @Test
    @DisplayName("Test 1.2: Both email and password fields should be visible and empty")
    public void testEmailAndPasswordFieldsAreVisible() {
        assertThat(loginPage.isEmailInputEmpty())
            .as("Email field should be empty on page load")
            .isTrue();
        
        assertThat(loginPage.isPasswordInputEmpty())
            .as("Password field should be empty on page load")
            .isTrue();
    }

    @Test
    @DisplayName("Test 1.3: Login button should be visible and clickable")
    public void testLoginButtonIsVisible() {
        assertThat(loginPage.getLoginButtonText())
            .as("Login button should be visible with correct text")
            .containsIgnoringCase("login");
    }

    @Test
    @DisplayName("Test 1.4: Test empty email validation")
    public void testEmptyEmailValidation() {
        loginPage.enterPassword("ValidPass@123");
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getEmailErrorMessage();
        assertThat(errorMessage)
            .as("Empty email should show error message")
            .containsIgnoringCase("email is required");
    }

    @Test
    @DisplayName("Test 1.5: Test empty password validation")
    public void testEmptyPasswordValidation() {
        loginPage.enterEmail("test@example.com");
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getPasswordErrorMessage();
        assertThat(errorMessage)
            .as("Empty password should show error message")
            .containsIgnoringCase("password is required");
    }

    @Test
    @DisplayName("Test 1.6: Test both fields empty validation")
    public void testBothFieldsEmptyValidation() {
        loginPage.clickLoginButton();

        String emailError = loginPage.getEmailErrorMessage();
        String passwordError = loginPage.getPasswordErrorMessage();

        assertThat(emailError)
            .as("Email field should show error when empty")
            .isNotEmpty();
        
        assertThat(passwordError)
            .as("Password field should show error when empty")
            .isNotEmpty();
    }

    @Test
    @DisplayName("Test 1.7: Test invalid email format")
    public void testInvalidEmailFormat() {
        loginPage.enterEmail("invalidemail");
        loginPage.enterPassword("ValidPass@123");
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getEmailErrorMessage();
        assertThat(errorMessage)
            .as("Invalid email format should show error")
            .containsIgnoringCase("invalid email");
    }

    @Test
    @DisplayName("Test 1.8: Test valid email format acceptance")
    public void testValidEmailFormatAcceptance() {
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword("ValidPass@123");
        
        // Email should not show error for valid format
        String errorMessage = loginPage.getEmailErrorMessage();
        assertThat(errorMessage)
            .as("Valid email should not show error")
            .isEmpty();
    }

    @Test
    @DisplayName("Test 1.9: Test short password validation")
    public void testShortPasswordValidation() {
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword("Short1!");
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getPasswordErrorMessage();
        assertThat(errorMessage)
            .as("Short password should show error")
            .containsIgnoringCase("at least 8 characters");
    }

    @Test
    @DisplayName("Test 1.10: Test password without uppercase letter")
    public void testPasswordWithoutUppercase() {
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword("valid@pass123");
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getPasswordErrorMessage();
        assertThat(errorMessage)
            .as("Password without uppercase should show error")
            .containsIgnoringCase("uppercase");
    }

    @Test
    @DisplayName("Test 1.11: Test password without lowercase letter")
    public void testPasswordWithoutLowercase() {
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword("VALID@PASS123");
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getPasswordErrorMessage();
        assertThat(errorMessage)
            .as("Password without lowercase should show error")
            .containsIgnoringCase("lowercase");
    }

    @Test
    @DisplayName("Test 1.12: Test password without number")
    public void testPasswordWithoutNumber() {
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword("ValidPass@");
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getPasswordErrorMessage();
        assertThat(errorMessage)
            .as("Password without number should show error")
            .containsIgnoringCase("number");
    }

    @Test
    @DisplayName("Test 1.13: Test password without special character")
    public void testPasswordWithoutSpecialChar() {
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword("ValidPass123");
        loginPage.clickLoginButton();

        String errorMessage = loginPage.getPasswordErrorMessage();
        assertThat(errorMessage)
            .as("Password without special character should show error")
            .containsIgnoringCase("special character");
    }

    @Test
    @DisplayName("Test 1.14: Test valid password acceptance")
    public void testValidPasswordAcceptance() {
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword("ValidPass@123");
        
        // Password should not show error for valid format
        String errorMessage = loginPage.getPasswordErrorMessage();
        assertThat(errorMessage)
            .as("Valid password should not show error")
            .isEmpty();
    }
}
