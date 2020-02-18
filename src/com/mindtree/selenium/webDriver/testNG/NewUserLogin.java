package com.mindtree.selenium.webDriver.testNG;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
//import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.mindtree.selenium.webDriver.basic.BrowserController;
import com.mindtree.selenium.webDriver.basic.WindowController;
import com.mindtree.selenium.webDriver.feedback.SendEmail;
import com.mindtree.selenium.webDriver.pages.MenuBar;
import com.mindtree.selenium.webDriver.pages.SignInPage;
import com.mindtree.selenium.webDriver.resources.RandomGenerator;
import com.mindtree.selenium.webDriver.resources.User;
import com.mindtree.selenium.webDriver.utils.JDBCDriver;

import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class NewUserLogin {
	WebDriver driver;
	User user = new User();
	final static Logger logger = Logger.getLogger(NewUserLogin.class);
	SendEmail email = new SendEmail();
	//@Ignore
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
		MenuBar menu = new MenuBar();
		SignInPage sign = new SignInPage();
		JDBCDriver jdbc = new JDBCDriver();
		Connection conn = jdbc.getConnection();
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
		menu.signin(driver);
		logger.info("Open Create Account Page");
		sign.createAccount(driver);// Clicks Create Account

		logger.info("Setting user information");
		
		sign.setUsername(driver, ranName);		
		sign.setPhone(driver, phoneString);
		sign.setEmail(driver, ranName);
		sign.setPassword(driver, "Password123!");

		
		logger.info("Submitting new user");
		sign.submit(driver);

		logger.info("Closing new user setup");
		sign.close(driver);
		
		ctrl.pause(2000);
		
		//verify User in DB
		int userId=jdbc.getUserID(conn, user.getEmail(), user.getPhone());
		if(userId==0) {
			System.out.println("ERROR: User not found in DB");
			logger.error("User "+user.getUsername() +" not found in DB");
		//	System.exit(0);
		}else
		{
			logger.info("userId ID found: " + userId);
			user.setUserID(userId);
		}
	}
	
	
	@Test(dependsOnMethods = { "openBrowser", "createNewUser" })
	public void login() throws FileNotFoundException, IOException {
		SignInPage sign = new SignInPage();
		Properties p = new Properties();
		FileInputStream file = new FileInputStream("..\\Assessment\\properties\\login.properties");
		p.load(file);

		logger.info("Logging in as new user");
		sign.login(driver, user);
	}

	@AfterTest
	public void close() {
		email.send("NewUserLogin");
		logger.info("Test Complete");
		//driver.close();
	}

	//@Ignore
	public WebDriver openBrowserNonNG() {
		BrowserController browser = new BrowserController();
		driver = browser.openBrowser("Chrome");
		driver.manage().deleteAllCookies();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		return driver;
	}

	@Test(dependsOnMethods = { "createNewUser" })
	public void getResponse() throws ClientProtocolException, IOException {

		Properties p = new Properties();
		FileInputStream file = new FileInputStream("..\\Assessment\\properties\\rest.properties");
		p.load(file);
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request =new HttpGet(p.getProperty("endpoint")+p.getProperty("phone"));
		HttpResponse response = client.execute(request);
		BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		String line="";
		StringBuffer sb= new StringBuffer();
		while((line=br.readLine())!=null) {
			sb.append(line);
		}
		System.out.println(response.getStatusLine());
		System.out.println(sb);
		String actualString=sb.toString();
		String status=response.getStatusLine().toString();
		assertTrue(actualString.contains(user.getPhone()));
		assertTrue(status.contains("200"));
	}
}
