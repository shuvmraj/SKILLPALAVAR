package com.login.test.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
    protected WebDriver driver;
    protected static final String BASE_URL = "http://localhost:3000";
    protected static final long IMPLICIT_WAIT = 10;
    protected static final long TEST_DELAY_MS = 1500;

    @RegisterExtension
    TestWatcher testWatcher = new TestWatcher() {
        @Override
        public void testSuccessful(ExtensionContext context) {
            System.out.println("[PASS] " + context.getDisplayName());
        }

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            System.out.println("[FAIL] " + context.getDisplayName() + " - " + cause.getMessage());
        }

        @Override
        public void testDisabled(ExtensionContext context, java.util.Optional<String> reason) {
            String suffix = reason.map(r -> " - " + r).orElse("");
            System.out.println("[SKIP] " + context.getDisplayName() + suffix);
        }
    };

    @BeforeEach
    public void setUp(TestInfo testInfo) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        
        driver = new ChromeDriver(options);
        driver.manage().deleteAllCookies();
        System.out.println("[START] " + testInfo.getDisplayName());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }

        try {
            Thread.sleep(TEST_DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected void navigateToLoginPage() {
        driver.navigate().to(BASE_URL);
    }
}
