package saucelab;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginAndVerifyTest {

    @Test
    public void loginTest()
            throws InterruptedException {

        String environment =
                System.getProperty("environment");

        String browser =
                System.getProperty("browser");

        String suite =
                System.getProperty("suite");

        String headless =
                System.getProperty("headless");

        WebDriver driver = null;

        try {

            ChromeOptions options =
                    new ChromeOptions();

            if ("true".equalsIgnoreCase(headless)) {

                options.addArguments(
                        "--headless=new");
            }

            options.addArguments(
                    "--start-maximized");

            options.addArguments(
                    "--disable-notifications");

            driver =
                    new ChromeDriver(options);

            WebDriverWait wait =
                    new WebDriverWait(
                            driver,
                            Duration.ofSeconds(30));

            CustomReportManager.logStep(
                    "Open SauceDemo application",
                    "SauceDemo login page should be displayed",
                    "SauceDemo login page opened",
                    "PASS");

            driver.navigate().to(
                    "https://www.saucedemo.com");

            CustomReportManager.logStep(
                    "Enter username",
                    "Username should be accepted",
                    "Username entered: standard_user",
                    "PASS");

            WebElement userName =
                    wait.until(
                        ExpectedConditions
                            .visibilityOfElementLocated(
                                By.id("user-name")));

            userName.sendKeys(
                    "standard_user");

            CustomReportManager.logStep(
                    "Enter password",
                    "Password should be accepted",
                    "Password entered successfully",
                    "PASS");

            WebElement passWord =
                    wait.until(
                        ExpectedConditions
                            .visibilityOfElementLocated(
                                By.id("password")));

            passWord.sendKeys(
                    "secret_sauce");

            CustomReportManager.logStep(
                    "Click Login button",
                    "User should be logged in",
                    "Login button clicked",
                    "PASS");

            driver.findElement(
                    By.id("login-button"))
                    .click();

            CustomReportManager.logStep(
                    "Verify Products page",
                    "Products",
                    driver.findElement(
                            By.xpath(
                                "//span[.='Products']"))
                        .getText(),
                    "PASS");

            String actualText =
                    wait.until(
                        ExpectedConditions
                            .visibilityOfElementLocated(
                                By.xpath(
                                    "//span[.='Products']")))
                    .getText();

            try {

                Assert.assertEquals(
                        actualText,
                        "Products");

                        
            }
            catch (AssertionError e) {

                CustomReportManager.failTest(
                        "Verify Products page",
                        "Products",
                        actualText,
                        driver);

                throw e;
            }

            System.out.println(
                    "Environment : "
                    + environment);

            System.out.println(
                    "Browser     : "
                    + browser);

            System.out.println(
                    "Suite       : "
                    + suite);

            System.out.println(
                    "Headless    : "
                    + headless);

        }
        catch (AssertionError e) {

            throw e;

        }
        catch (Exception e) {

            if (driver != null) {

                CustomReportManager.failTest(
                        "Unexpected test failure",
                        "Test should execute successfully",
                        e.getMessage(),
                        driver);
            }

            throw e;

        }
        finally {

            if (driver != null) {

                driver.quit();
            }
        }
    }
}