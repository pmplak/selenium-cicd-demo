package saucelab;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class AddProductAndCompleteCheckoutTest {

    @Test
    public void checkoutTest()
            throws InterruptedException {

        Map<String, Object> prefs =
                new HashMap<>();

        prefs.put(
                "credentials_enable_service",
                false);

        prefs.put(
                "profile.password_manager_enabled",
                false);

        ChromeOptions options =
                new ChromeOptions();

        options.setExperimentalOption(
                "prefs",
                prefs);

        options.addArguments(
                "--start-maximized");

        options.addArguments(
                "--disable-notifications");

        options.addArguments(
                "--guest");

        WebDriver driver = null;

        try {

            driver =
                    new ChromeDriver(options);

            WebDriverWait wait =
                    new WebDriverWait(
                            driver,
                            Duration.ofSeconds(30));

            driver.navigate().to(
                    "https://www.saucedemo.com");

            CustomReportManager.logStep(
                    "Open SauceDemo application",
                    "SauceDemo login page should be displayed",
                    "SauceDemo login page opened",
                    "PASS");

            WebElement userName =
                    wait.until(
                        ExpectedConditions
                            .visibilityOfElementLocated(
                                By.id("user-name")));

            userName.sendKeys(
                    "standard_user");

            CustomReportManager.logStep(
                    "Enter username",
                    "Username should be accepted",
                    "standard_user entered",
                    "PASS");

            WebElement passWord =
                    wait.until(
                        ExpectedConditions
                            .visibilityOfElementLocated(
                                By.id("password")));

            passWord.sendKeys(
                    "secret_sauce");

            CustomReportManager.logStep(
                    "Enter password",
                    "Password should be accepted",
                    "Password entered successfully",
                    "PASS");

            driver.findElement(
                    By.id("login-button"))
                    .click();

            CustomReportManager.logStep(
                    "Click Login button",
                    "User should be logged in",
                    "Login button clicked",
                    "PASS");

            driver.findElement(
                    By.id(
                        "add-to-cart-sauce-labs-backpack"))
                    .click();

            CustomReportManager.logStep(
                    "Add Sauce Labs Backpack",
                    "Backpack should be added to cart",
                    "Backpack added to cart",
                    "PASS");

            driver.findElement(
                    By.className(
                        "shopping_cart_link"))
                    .click();

            CustomReportManager.logStep(
                    "Open shopping cart",
                    "Shopping cart should be displayed",
                    "Shopping cart opened",
                    "PASS");

            driver.findElement(
                    By.id("checkout"))
                    .click();

            CustomReportManager.logStep(
                    "Click Checkout",
                    "Checkout information page should open",
                    "Checkout page opened",
                    "PASS");

            List<String> data =
                    Arrays.asList(
                            "John",
                            "Doe",
                            "625001");

            List<WebElement> formElements =
                    driver.findElements(
                        By.xpath(
                            "//div[@class='checkout_info']//input"));

            for (int i = 0;
                 i < formElements.size();
                 i++) {

                String fieldName =
                        formElements.get(i)
                            .getAttribute(
                                "placeholder");

                formElements.get(i)
                        .sendKeys(
                            data.get(i));

                CustomReportManager.logStep(
                        "Enter " + fieldName,
                        fieldName
                            + " should be populated",
                        data.get(i),
                        "PASS");
            }

            CustomReportManager.logStep(
                    "Enter checkout information",
                    "First Name, Last Name and ZIP "
                    + "Code should be populated",
                    "All checkout fields populated",
                    "PASS");

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