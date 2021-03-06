package com.mindtree.selenium.webDriver.testNG;

import java.util.concurrent.TimeUnit;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import com.mindtree.selenium.webDriver.basic.BrowserController;
import com.mindtree.selenium.webDriver.basic.WindowController;
import com.mindtree.selenium.webDriver.feedback.SendEmail;
import com.mindtree.selenium.webDriver.pages.HomePage;
import com.mindtree.selenium.webDriver.pages.MenuBar;
import com.mindtree.selenium.webDriver.resources.RestResources;
import com.mindtree.selenium.webDriver.resources.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class MenuTest {
	WebDriver driver;
	User user = new User();
	final static Logger logger = Logger.getLogger(MenuTest.class);
	SendEmail email = new SendEmail();
	HomePage homePage = new HomePage();
	MenuBar menu = null;
	Properties p = new Properties();
	FileInputStream file;
	
	@BeforeTest
	public void openBrowser() throws IOException {		
		file = new FileInputStream("..\\Assessment\\properties\\page.properties");
		p.load(file);
		logger.info("Opening Browser");
		BrowserController browser = new BrowserController();
		driver = browser.openBrowser("Chrome");
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		WindowController ctrl = new WindowController(driver);

		driver.get("http://okmry52647dns.eastus.cloudapp.azure.com:9090/");
		ctrl.max(driver);
		logger.info("Maximize window");
	}

	@AfterMethod
	public void home() throws FileNotFoundException, IOException {
		WindowController ctrl= new WindowController(driver);
		menu = new MenuBar();
		menu.home(driver);
		logger.info("Open Home page");
		Assert.assertEquals(ctrl.getURL(driver), p.getProperty("home"));
	}

	@Test
	public void openChairs() throws FileNotFoundException, IOException {
		WindowController ctrl = new WindowController(driver);
		homePage.Browse(driver, "chairs");
		logger.info("Open Chair page");
		ctrl.pause(1000);
		Assert.assertEquals(ctrl.getURL(driver), p.getProperty("chairs"));
	}

	@Test
	public void openSofas() throws FileNotFoundException, IOException {
		WindowController ctrl = new WindowController(driver);
		homePage.Browse(driver, "sofas");
		logger.info("Open Sofa page");
		ctrl.pause(1000);
		Assert.assertEquals(ctrl.getURL(driver), p.getProperty("sofas"));
	}

	@Test
	public void openTables() throws FileNotFoundException, IOException {
		WindowController ctrl = new WindowController(driver);
		homePage.Browse(driver, "tables");
		logger.info("Open Table page");
		ctrl.pause(1000);
		Assert.assertEquals(ctrl.getURL(driver), p.getProperty("tables"));
	}

	@Test
	public void openBeds() throws FileNotFoundException, IOException {
		WindowController ctrl = new WindowController(driver);
		homePage.Browse(driver, "beds");
		logger.info("Open Bed page");
		ctrl.pause(1000);
		Assert.assertEquals(ctrl.getURL(driver), p.getProperty("beds"));
	}

	@Test
	public void openAll() throws FileNotFoundException, IOException {
		WindowController ctrl = new WindowController(driver);
		homePage.Browse(driver, "all");
		logger.info("Open all Furnature page");
		ctrl.pause(1000);
		Assert.assertEquals(ctrl.getURL(driver), p.getProperty("all"));
	}

	@Test
	public void search() throws FileNotFoundException, IOException {
		WindowController ctrl = new WindowController(driver);
		menu.search(driver, "Chair");
		logger.info("Searched for Chair");
		ctrl.pause(1000);
		Assert.assertEquals(ctrl.getURL(driver), p.getProperty("search")+"Chair");
	}

	@Test
	public void wishlist() throws FileNotFoundException, IOException {
		WindowController ctrl = new WindowController(driver);
		menu.wishlist(driver);
		logger.info("Opened wishlist");
		ctrl.pause(1000);
		Assert.assertEquals(ctrl.getURL(driver), p.getProperty("wish"));
	}

	@Test(priority=1)
	public void cart() throws FileNotFoundException, IOException {
		WindowController ctrl = new WindowController(driver);
		menu.cart(driver);
		logger.info("Opened cart");
		ctrl.pause(1000);
		Assert.assertEquals(ctrl.getURL(driver), p.getProperty("cart"));
	}

	@Test//This test will fail if not logged in
	public void support() throws FileNotFoundException, IOException {
		WindowController ctrl = new WindowController(driver);
		menu.support(driver);
		logger.info("Opened support");
		ctrl.pause(1000);
		Assert.assertEquals(ctrl.getURL(driver), p.getProperty("support"));//Expected to fail
	}

	@Test
	public void signin() throws FileNotFoundException, IOException {
		WindowController ctrl = new WindowController(driver);
		menu.signin(driver);
		logger.info("Opened signin");
		ctrl.pause(1000);
		Assert.assertEquals(ctrl.getURL(driver), p.getProperty("signin"));
	}
	
	@Test
	public void post() throws ClientProtocolException, IOException {
		RestResources rest=new RestResources();
		rest.postResponse();
	}
	
	
	@AfterTest
	public void close() {
		email.send("MenuTest");
		logger.info("Test Complete");
		// driver.close();	//commented out for testing purposes
	}
}
