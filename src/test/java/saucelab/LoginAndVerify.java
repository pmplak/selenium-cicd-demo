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

public class LoginAndVerify {
	static Logger logger = Logger.getLogger(LoginAndVerify.class.getName());

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		 ChromeOptions options = new ChromeOptions();
         options.addArguments("--start-maximized");
         options.addArguments("--disable-notifications");

		 WebDriver driver = new ChromeDriver(options);
		 driver.navigate().to("https://www.saucedemo.com");
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		 WebElement userName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
		 userName.sendKeys("standard_user");
		 WebElement passWord = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
		 passWord.sendKeys("secret_sauce");
		 Thread.sleep(5000);
		 driver.findElement(By.id("login-button")).click();
		 String expectedText = driver.findElement(By.xpath("//span[.='Products']")).getText();
		 Thread.sleep(5000);
		 if(expectedText.equals("Products")) {
			 logger.info("PASS : Successfully landed on Products page");
			 
		 }else {
			 logger.severe("FAIL : Products page cannot be found");
		 }
		 
		 driver.quit();
		
		 
		

	}

}
