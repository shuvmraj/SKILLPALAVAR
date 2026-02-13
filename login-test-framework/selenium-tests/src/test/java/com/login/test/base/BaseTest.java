package com.login.test.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

public class BaseTest {
    protected WebDriver driver;
    protected static final String BASE_URL = "http://localhost:3000";
    protected static final long IMPLICIT_WAIT = 10;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        
        driver = new ChromeDriver(options);
        driver.manage().deleteAllCookies();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void navigateToLoginPage() {
        driver.navigate().to(BASE_URL);
    }
}
