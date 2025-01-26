package Capstone;

import utility.ExtentManager;
import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

public class WestFlorida {
	
	WebDriver driver;
	
	
	@BeforeClass
    public void browserLaunch() {
        driver = new ChromeDriver();
        driver.get("https://westfloridaahec.org/");
    }
	
	
	@Test(priority=1)
	public void verifyTitle() {
		ExtentTest test = ExtentManager.getReporter().createTest("Verify Page Title");
		
		String actualTitle = driver.getTitle();
		Assert.assertTrue(actualTitle.contains("West Florida"));
		
		test.pass("Title Verified successfully");
	}
	
	@Test(priority=2)
	public void navigationMenu() {
		ExtentTest test = ExtentManager.getReporter().createTest("Verify navigation menu");
		
		WebElement menu = driver.findElement(By.id("menu-main-menu"));
		Assert.assertTrue(menu.isDisplayed());
		
		test.pass("Navigation menu is displayed");
	}
	
	@Test(priority=3)
	public void healthPrograms() {
		ExtentTest test = ExtentManager.getReporter().createTest("Verify health programs");
		
		WebElement programsLink = driver.findElement(By.cssSelector("span.menu-text"));
		Actions hoverAction = new Actions(driver);
		hoverAction.moveToElement(programsLink).perform();
		
		List<WebElement> programs = driver.findElements(By.cssSelector("ul.sub-menu"));
		Assert.assertTrue(programs.size() > 0);
		
		test.pass("Health programs are displayed");
	}
	
	@Test(priority=4)
	public void resourceLinks() {
		ExtentTest test = ExtentManager.getReporter().createTest("Verify resource links");
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		List<WebElement> resourceLinks = driver.findElements(By.tagName("a"));
		for(WebElement resourceLink : resourceLinks) {
			if(resourceLink.getText().contains("AHEC")) {
				
				wait.until(driver -> resourceLink.isDisplayed() && resourceLink.isEnabled());
				resourceLink.click();
				
				String actualTitle = driver.getTitle();
				Assert.assertTrue(actualTitle.contains("AHEC"));
				break;
			}		
		}
		driver.navigate().back();
		test.pass("Resource link is loaded");
	}
	
	@Test(priority=5)
	public void searchFunctionality() throws InterruptedException {
		ExtentTest test = ExtentManager.getReporter().createTest("Verify search functionality");
		
		WebElement search = driver.findElement(By.cssSelector("input[type='search']"));
		search.sendKeys("Tobacco");
		WebElement searchIcon = driver.findElement(By.cssSelector("input[type='submit']"));
		searchIcon.click();
		
		String url = driver.getCurrentUrl();
		Assert.assertTrue(url.contains("Tobacco"));
		
		driver.navigate().back();
		Thread.sleep(6000);
		
		test.pass("Verified search functionality");
		
	}
	
	
	@Test(priority=6)
	public void userRegistration() throws InterruptedException {
		ExtentTest test = ExtentManager.getReporter().createTest("Verify user registration");
		
		WebElement myAccountElement = driver.findElement(By.xpath("//*[@id=\"menu-main-menu\"]/li[8]/a/span[1]"));
		myAccountElement.click();
		
		driver.findElement(By.xpath("//*[@id=\"reg_username\"]")).sendKeys("snehal");
		driver.findElement(By.xpath("//*[@id=\"reg_email\"]")).sendKeys("snehal@gmail.com");
		driver.findElement(By.xpath("//*[@id=\"reg_password\"]")).sendKeys("snehal@12345");
		driver.findElement(By.xpath("//*[@id=\"customer_login\"]/div[2]/form/p[4]/button")).click();
		
		Thread.sleep(5000);
		WebElement helloMessage = driver.findElement(By.xpath("//*[@id=\"post-381\"]/div/div/div[1]/div[1]/span[1]"));
		String message = helloMessage.getText();
		Assert.assertTrue("Already user is registered",message.contains("snehal"));
		
		driver.findElement(By.linkText("Log out")).click();
		
		test.pass("User is registered");
	}
	
	@Test(priority=7)
	public void userLogin() throws InterruptedException {
		
		ExtentTest test = ExtentManager.getReporter().createTest("Verify user login");
		
		driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("snehal");
		driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("snehal@12345");
		driver.findElement(By.xpath("//*[@id=\"customer_login\"]/div[1]/form/p[3]/button")).click();
		
		Thread.sleep(5000);
		WebElement helloMessage = driver.findElement(By.xpath("//*[@id=\"post-381\"]/div/div/div[1]/div[1]/span[1]"));
		String message = helloMessage.getText();
		Assert.assertTrue(message.contains("snehal"));
		
		driver.findElement(By.linkText("Log out")).click();
		
		test.pass("Login successful");
	}
	
	@Test(priority=8)
	public void passwordRecovery() throws InterruptedException {
		
		ExtentTest test = ExtentManager.getReporter().createTest("Verify password recovery");
		
		driver.findElement(By.linkText("Lost your password?")).click();
		driver.findElement(By.xpath("//*[@id=\"user_login\"]")).sendKeys("snehal@gmail.com");
		driver.findElement(By.xpath("//*[@id=\"post-381\"]/div/div/form/p[3]/button")).click();
		
		Thread.sleep(5000);
		WebElement recoveryMessageElement = driver.findElement(By.xpath("//*[@id=\"post-381\"]/div/div/div/div"));
		String recoveryMessage = recoveryMessageElement.getText();
		
		Assert.assertTrue(recoveryMessage.contains("reset email"));
		
		test.pass("Password recovery successful");
		
	}
	
	
	@AfterClass
    public void tearDown() {
		if(driver !=null) {
			driver.quit();
		}
		 ExtentManager.getReporter().flush();
        
    }


}
