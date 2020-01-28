package com.mindtree.selenium.webDriver.basic;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BrowserController {
	
	public WebDriver openBrowser(String browserType) {
		switch(browserType) {
		case "Chrome":
			System.out.println("Opening " + browserType);
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\Jarell\\Selenium-Workspace\\SeleniumIDE\\lib\\chromedriver_win32\\chromedriver.exe");
			return new ChromeDriver();
		case "FireFox":
			System.out.println("Opening " + browserType);
			System.setProperty("webdriver.gecko.driver", "C:\\Users\\Jarell\\Selenium-Workspace\\Selenium Test Cases\\lib\\geckodriver-v0.25.0-win64\\geckodriver.exe");
			return new FirefoxDriver();
		case "Opera":
	        String path = "C:\\Users\\Jarell\\Selenium-Workspace\\Selenium Test Cases\\lib\\operadriver_win64\\operadriver_win64\\operadriver.exe";
	        OperaOptions options = new OperaOptions();
	        options.setBinary("C:\\Users\\Jarell\\AppData\\Local\\Programs\\Opera\\63.0.3368.94_0\\opera.exe");
			System.out.println("Opening " + browserType);
			System.setProperty("webdriver.opera.driver", path);
			return new OperaDriver(options);
		case "theBadOne"://This one is IE
			File ieFile = new File("C:\\Users\\Jarell\\Selenium-Workspace\\Selenium Test Cases\\lib\\IEDriverServer_x64_3.14.0\\IEDriverServer.exe");
			System.out.println("Opening " + browserType);
			System.setProperty("webdriver.ie.driver", ieFile.getAbsolutePath());
			DesiredCapabilities ieCaps = DesiredCapabilities.internetExplorer();
			ieCaps.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "http://www.bing.com/");
			return new InternetExplorerDriver();

		}
		return new ChromeDriver();
	}
}
