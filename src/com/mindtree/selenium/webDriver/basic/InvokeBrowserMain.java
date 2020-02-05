package com.mindtree.selenium.webDriver.basic;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.mindtree.selenium.webDriver.scriptAssignments.BasicCommands;

public class InvokeBrowserMain {
	static WebDriver driver;

	public void invokeBrowser(String browserType) {
		BrowserController browser=new BrowserController();
		driver = browser.openBrowser(browserType);
		driver.manage().deleteAllCookies();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}
	
	
	public static void main(String[] args) {
		InvokeBrowserMain invoke = new InvokeBrowserMain();
		BasicCommands links=new BasicCommands();
		String browserType[]= {"Chrome", "FireFox", "Opera", "theBadOne"};//In case of mutliple types of browsers

		invoke.invokeBrowser(browserType[0]);
		
	    //links.runCommand(driver);
	    
		driver.close();
		System.exit(0);
	}
	

}
