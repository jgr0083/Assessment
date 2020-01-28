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

public class LoggingTest {
	WebDriver driver;
	User user=new User();
    final static Logger logger = Logger.getLogger(LoggingTest.class);

  @Test
  public void openBrowser() {
  	logger.setLevel(Level.DEBUG);
    
    logger.error("Critical message, almost fatal");
    logger.warn("Warnings, which may lead to system impact");
    logger.info("Information");
    logger.debug("Debugging information ");

  }
  
  @Test(dependsOnMethods = {"openBrowser"})
  public void createNewUser() throws FileNotFoundException, IOException {
	  	logger.warn("MIddle");

  }
  
  @AfterTest
  public void close() {
	  	logger.fatal("End");
  }
}
