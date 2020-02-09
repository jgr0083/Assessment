package com.mindtree.selenium.webDriver.testNG;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import com.mindtree.selenium.webDriver.basic.BrowserController;
import com.mindtree.selenium.webDriver.basic.WindowController;
import com.mindtree.selenium.webDriver.feedback.SendEmail;
import com.mindtree.selenium.webDriver.resources.RandomGenerator;
import com.mindtree.selenium.webDriver.resources.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class MenuTest {
	WebDriver driver;
	User user=new User();
    final static Logger logger = Logger.getLogger(MenuTest.class);	
	SendEmail email = new SendEmail();

  @BeforeTest
  public void openBrowser() {
	  	logger.info("Opening Browser");
		BrowserController browser=new BrowserController();
		driver = browser.openBrowser("Chrome");
		System.out.println("HERE");
		driver.manage().deleteAllCookies();
	 	WindowController ctrl = new WindowController(driver);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

		driver.get("http://okmry52647dns.eastus.cloudapp.azure.com:9090/");
		ctrl.max(driver);
	  	logger.info("Maximize window");
  }
  
  @AfterMethod
  public void home() throws FileNotFoundException, IOException {
	  WindowController ctrl = new WindowController(driver);
	  ctrl.pause(1000);
	  Properties p=new Properties();
	  FileInputStream file = new FileInputStream("..\\Assessment\\properties\\categories.properties");
	  p.load(file);
	  
	  logger.info("Open Home page");
	  ctrl.click(driver, By.cssSelector(p.getProperty("home")));
	  ctrl.pause(1000);
  }

  @Test
  public void openChairs() throws FileNotFoundException, IOException {
	  WindowController ctrl = new WindowController(driver);
	  Properties p=new Properties();
	  FileInputStream file = new FileInputStream("..\\Assessment\\properties\\categories.properties");
	  p.load(file);

	  logger.info("Open Chair page");
	  ctrl.click(driver, By.cssSelector(p.getProperty("chairs")));

  }
  
  @Test
  public void openSofas() throws FileNotFoundException, IOException {
	  WindowController ctrl = new WindowController(driver);
	  Properties p=new Properties();
	  FileInputStream file = new FileInputStream("..\\Assessment\\properties\\categories.properties");
	  p.load(file);

	  logger.info("Open Sofa page");
	  ctrl.click(driver, By.cssSelector(p.getProperty("sofas")));

  }
  
  @Test
  public void openTables() throws FileNotFoundException, IOException {
	  WindowController ctrl = new WindowController(driver);
	  Properties p=new Properties();
	  FileInputStream file = new FileInputStream("..\\Assessment\\properties\\categories.properties");
	  p.load(file);

	  logger.info("Open Table page");
	  ctrl.click(driver, By.cssSelector(p.getProperty("tables")));
  }

  @Test
  public void openBeds() throws FileNotFoundException, IOException {
	  WindowController ctrl = new WindowController(driver);
	  Properties p=new Properties();
	  FileInputStream file = new FileInputStream("..\\Assessment\\properties\\categories.properties");
	  p.load(file);

	  logger.info("Open Bed page");
	  ctrl.click(driver, By.cssSelector(p.getProperty("beds")));
  }
  
  @Test
  public void openAll() throws FileNotFoundException, IOException {
	  WindowController ctrl = new WindowController(driver);
	  Properties p=new Properties();
	  FileInputStream file = new FileInputStream("..\\Assessment\\properties\\categories.properties");
	  p.load(file);

	  logger.info("Open all Furnature page");
	  ctrl.click(driver, By.cssSelector(p.getProperty("allFurnature")));
  }
  

  @AfterTest
  public void close() {
	  email.send("MenuTest");
	  logger.info("Test Complete");
	  //driver.close();
  }
}
