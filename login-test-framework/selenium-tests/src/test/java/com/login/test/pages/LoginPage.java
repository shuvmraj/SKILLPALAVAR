package com.login.test.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private static final By EMAIL_INPUT = By.id("email");
    private static final By PASSWORD_INPUT = By.id("password");
    private static final By LOGIN_BUTTON = By.id("loginButton");
    private static final By EMAIL_ERROR = By.id("emailError");
    private static final By PASSWORD_ERROR = By.id("passwordError");
    private static final By SUBMIT_ERROR = By.id("submitError");
    private static final By SUCCESS_MESSAGE = By.id("successMessage");
    private static final By LOGIN_TITLE = By.xpath("//h1[@class='login-title']");
    private static final By WELCOME_TITLE = By.xpath("//h1[contains(@class,'welcome-title')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isPageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_TITLE));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isWelcomePageVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(WELCOME_TITLE));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void enterEmail(String email) {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_INPUT));
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_INPUT));
        passwordField.clear();
        passwordField.sendKeys(password);
        
    }

    public void clickLoginButton() {
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON));
        loginBtn.click();
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    public String getEmailErrorMessage() {
        try {
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_ERROR));
            return errorElement.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getPasswordErrorMessage() {
        try {
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_ERROR));
            return errorElement.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getSubmitErrorMessage() {
        try {
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(SUBMIT_ERROR));
            return errorElement.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getSuccessMessage() {
        try {
            WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_MESSAGE));
            return successElement.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isEmailInputEmpty() {
        return driver.findElement(EMAIL_INPUT).getAttribute("value").isEmpty();
    }

    public boolean isPasswordInputEmpty() {
        return driver.findElement(PASSWORD_INPUT).getAttribute("value").isEmpty();
    }

    public String getEmailInputValue() {
        return driver.findElement(EMAIL_INPUT).getAttribute("value");
    }

    public String getPasswordInputValue() {
        return driver.findElement(PASSWORD_INPUT).getAttribute("value");
    }

    public boolean isEmailInputDisabled() {
        return !driver.findElement(EMAIL_INPUT).isEnabled();
    }

    public boolean isPasswordInputDisabled() {
        return !driver.findElement(PASSWORD_INPUT).isEnabled();
    }

    public boolean isLoginButtonDisabled() {
        return !driver.findElement(LOGIN_BUTTON).isEnabled();
    }

    public String getLoginButtonText() {
        return driver.findElement(LOGIN_BUTTON).getText();
    }
}
