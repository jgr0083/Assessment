package com.mindtree.selenium.webDriver.testNG;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.mindtree.selenium.webDriver.basic.BrowserController;
import com.mindtree.selenium.webDriver.basic.WindowController;
import com.mindtree.selenium.webDriver.resources.RandomGenerator;
import com.mindtree.selenium.webDriver.resources.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class NewUserLogin {
	WebDriver driver;
	User user=new User();
	
	NewUserLogin(WebDriver driver){
		this.driver=driver;
	}
	
  @Test
  public void openBrowser() {
		BrowserController browser=new BrowserController();
		driver = browser.openBrowser("Chrome");
		driver.manage().deleteAllCookies();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

  }
  
  @Test(dependsOnMethods = {"openBrowser"})
  public void createNewUser() throws FileNotFoundException, IOException {
	  Properties p=new Properties();
	  FileInputStream file = new FileInputStream("..\\Assessment\\src\\login.properties");
	  p.load(file);
	  RandomGenerator rando= new RandomGenerator();
	  String ranName = rando.randomString(10);
	  int phone = rando.randomInt(1000000000, 999999999); 
	  String phoneString = Integer.toString(phone);

	  user.setUsername("Test Name"+ranName);
	  user.setPassword("Password123!");
	  user.setEmail("Test_Name"+ranName+"@Test.com");
	  user.setPhone(phoneString);
	  
	  WindowController ctrl =new WindowController(driver);

	  driver.get("http://okmry52647dns.eastus.cloudapp.azure.com:9090");
	  ctrl.max(driver);

	  ctrl.click(driver, By.xpath("//*[@id=\"navbarSupportedContent\"]/form/button[4]"));	//Selects Sign in button
	  ctrl.click(driver, By.cssSelector(".btn.btn-primary.btn-md"));	//Clicks Create Account

	  ctrl.type(driver, By.id(p.getProperty("username")), "Test Name"+ranName);
	  ctrl.type(driver, By.id(p.getProperty("phone")), phoneString);
	  
	  List<WebElement> email = driver.findElements(By.id(p.getProperty("email")));
	  email.get(1).sendKeys("Test_Name"+ranName+"@Test.com");
	  
	  List<WebElement> password = driver.findElements(By.id(p.getProperty("password")));
	  password.get(1).sendKeys("Password123!");
	  
	  List<WebElement> submit = driver.findElements(By.cssSelector(".btn.btn-primary.btn-lg.btn-block"));
	  submit.get(1).click();

	  List<WebElement> close = driver.findElements(By.cssSelector(".mat-icon.notranslate.material-icons.mat-icon-no-color"));
	  close.get(11).click();
	  

	  //role="alertdialog"
  }
  
  @Test(dependsOnMethods = {"openBrowser", "createNewUser"})
  public void login() throws FileNotFoundException, IOException {
	  Properties p=new Properties();
	  FileInputStream file = new FileInputStream("..\\Assessment\\src\\login.properties");
	  p.load(file);
	  
	  List<WebElement> email = driver.findElements(By.id(p.getProperty("email")));
	  email.get(0).sendKeys(user.getEmail());
	  
	  List<WebElement> password = driver.findElements(By.id(p.getProperty("password")));
	  password.get(0).sendKeys(user.getPassword());
	  
	  List<WebElement> submit = driver.findElements(By.cssSelector(".btn.btn-primary.btn-lg.btn-block"));
	  submit.get(0).click();
  }
  
  @AfterTest
  public void close() {
	  //driver.close();
  }
}
