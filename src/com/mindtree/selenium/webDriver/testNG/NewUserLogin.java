package com.mindtree.selenium.webDriver.testNG;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.mindtree.selenium.webDriver.basic.BrowserController;
import com.mindtree.selenium.webDriver.basic.WindowController;
import com.mindtree.selenium.webDriver.feedback.SendEmail;
import com.mindtree.selenium.webDriver.resources.JDBCDriver;
import com.mindtree.selenium.webDriver.resources.RandomGenerator;
import com.mindtree.selenium.webDriver.resources.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class NewUserLogin {
	WebDriver driver;
	User user = new User();
	final static Logger logger = Logger.getLogger(NewUserLogin.class);
	SendEmail email = new SendEmail();

	@Ignore
	public User getUser() {
		return user;
	}
	
	@Test
	public void openBrowser() {
		BrowserController browser = new BrowserController();
		driver = browser.openBrowser("Chrome");
		driver.manage().deleteAllCookies();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

	}

	@Test(dependsOnMethods = { "openBrowser" })
	public void createNewUser() throws FileNotFoundException, IOException {
		JDBCDriver jdbc = new JDBCDriver();
		Connection conn = jdbc.getConnection();
		Properties p = new Properties();
		FileInputStream file = new FileInputStream("..\\Assessment\\properties\\login.properties");
		p.load(file);
		RandomGenerator rando = new RandomGenerator();
		String ranName = rando.randomString(10);
		int phone = rando.randomInt(1000000000, 999999999);
		String phoneString = Integer.toString(phone);
		logger.info("Generating user information...");

		user.setUsername("Test Name" + ranName);
		user.setPassword("Password123!");
		user.setEmail("Test_Name" + ranName + "@Test.com");
		user.setPhone(phoneString);

		WindowController ctrl = new WindowController(driver);

		driver.get("http://okmry52647dns.eastus.cloudapp.azure.com:9090");
		ctrl.max(driver);

		logger.info("Open Sign in page");
		ctrl.click(driver, By.xpath("//*[@id=\"navbarSupportedContent\"]/form/button[4]")); // Selects Sign in button
		logger.info("Open Create Account Page");
		ctrl.click(driver, By.cssSelector(".btn.btn-primary.btn-md")); // Clicks Create Account

		logger.info("Setting user information");
		ctrl.type(driver, By.id(p.getProperty("username")), "Test Name" + ranName);
		ctrl.type(driver, By.id(p.getProperty("phone")), phoneString);

		List<WebElement> email = driver.findElements(By.id(p.getProperty("email")));
		email.get(1).sendKeys("Test_Name" + ranName + "@Test.com");

		List<WebElement> password = driver.findElements(By.id(p.getProperty("password")));
		password.get(1).sendKeys("Password123!");

		logger.info("Submitting new user");
		List<WebElement> submit = driver.findElements(By.cssSelector(".btn.btn-primary.btn-lg.btn-block"));
		submit.get(1).click();

		logger.info("Closing new user setup");
		List<WebElement> close = driver
				.findElements(By.cssSelector(".mat-icon.notranslate.material-icons.mat-icon-no-color"));
		close.get(11).click();
		
		ctrl.pause(2000);
		
		//verify User in DB
		int userId=jdbc.getUserID(conn, user.getEmail(), user.getPhone());
		if(userId==0) {
			System.out.println("ERROR: User not found in DB");
			logger.error("User "+user.getUsername() +" not found in DB");
			System.exit(0);
		}else
		{
			logger.info("userId ID found: " + userId);
			user.setUserID(userId);
		}

		// SELECT * FROM profile where user_id=216;

		// role="alertdialog"
	}

	@Test(dependsOnMethods = { "openBrowser", "createNewUser" })
	public void login() throws FileNotFoundException, IOException {
		Properties p = new Properties();
		FileInputStream file = new FileInputStream("..\\Assessment\\properties\\login.properties");
		p.load(file);

		logger.info("Logging in as new user");
		List<WebElement> email = driver.findElements(By.id(p.getProperty("email")));
		email.get(0).sendKeys(user.getEmail());

		List<WebElement> password = driver.findElements(By.id(p.getProperty("password")));
		password.get(0).sendKeys(user.getPassword());

		List<WebElement> submit = driver.findElements(By.cssSelector(".btn.btn-primary.btn-lg.btn-block"));
		submit.get(0).click();
	}

	@AfterTest
	public void close() {
		email.send("NewUserLogin");
		logger.info("Test Complete");
		// driver.close();
	}

	@Ignore
	public WebDriver openBrowserNonNG() {
		BrowserController browser = new BrowserController();
		driver = browser.openBrowser("Chrome");
		driver.manage().deleteAllCookies();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		return driver;
	}
}
