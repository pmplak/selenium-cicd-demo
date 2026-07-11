package saucelab;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Add_Product_and_Complete_Checkout {
	static Logger logger = Logger.getLogger(LoginAndVerify.class.getName());
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		Map<String, Object> prefs = new HashMap<>();

		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--guest");


		 WebDriver driver = new ChromeDriver(options);
		 driver.navigate().to("https://www.saucedemo.com");
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		 WebElement userName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
		 userName.sendKeys("standard_user");
		 WebElement passWord = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
		 passWord.sendKeys("secret_sauce");
		 Thread.sleep(5000);
		 driver.findElement(By.id("login-button")).click();
		 
		 driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
		 driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).click();
		 driver.findElement(By.id("checkout")).click();
		 
		 List<String> data = Arrays.asList(
				    "John",
				    "Doe",
				    "625001"
				);
		 List<WebElement> formElements = 
				 driver.findElements(By.xpath("//div[@class='checkout_info']//input"));

		 for (int i = 0; i < formElements.size(); i++) {

			    System.out.println("Entering : "
			            + formElements.get(i).getAttribute("placeholder"));

			    formElements.get(i).sendKeys(data.get(i));
			}
		 
		 
				driver.quit();
		 
		 

	}

}
