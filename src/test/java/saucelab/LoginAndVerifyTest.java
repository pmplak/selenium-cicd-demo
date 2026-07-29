package saucelab;

import java.time.Duration;
import java.util.logging.Logger;

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

    static Logger logger = Logger.getLogger(LoginAndVerifyTest.class.getName());

    @Test
    public void loginTest() throws InterruptedException {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        WebDriver driver = new ChromeDriver(options);

        try {

            driver.navigate().to("https://www.saucedemo.com");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            WebElement userName = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
            userName.sendKeys("standard_user");

            WebElement passWord = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("password")));
            passWord.sendKeys("secret_sauce");

            Thread.sleep(5000);

            driver.findElement(By.id("login-button")).click();

            String actualText = driver.findElement(By.xpath("//span[.='Products']")).getText();

            Thread.sleep(5000);

            Assert.assertEquals(actualText, "Products");

            logger.info("PASS : Successfully landed on Products page");

        } finally {

            driver.quit();

        }
    }
}