package com.mindtree.selenium.webDriver.basic;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * This class is for handling common web functionality
 * */
public class WindowController {

	String originalHandle;
	
	public WindowController(WebDriver driver){
		originalHandle = driver.getWindowHandle();
	}
	
	public void back(WebDriver driver) {
		driver.navigate().back();
		System.out.println("Back... New page: " +driver.getTitle());
	}
	public void forward(WebDriver driver) {
		driver.navigate().forward();
		System.out.println("Forward... New page: " +driver.getTitle());

	}
	public void refresh(WebDriver driver) {
		driver.navigate().refresh();
		System.out.println("Refreshing page: " +driver.getTitle());

	}
	public void max(WebDriver driver) {
		driver.manage().window().maximize();
		System.out.println("Maximized page: " +driver.getTitle());
	}
	
	public void pause(int time){
		try {
		Thread.sleep(time);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}

	//clicks
	public void click(WebDriver driver, By oby) {
		driver.findElement(oby).click();
	}
	
	//Switches to new tab or Frame
	public void switchToNewTab(WebDriver driver) {
		 ArrayList tabs = new ArrayList (driver.getWindowHandles());
		 driver.switchTo().window((String) tabs.get(tabs.size()-1)); 
	}
	
	public void switchToNewFrame(WebDriver driver, String id) {
		driver.switchTo().frame(driver.findElement(By.id(id)));
	}
	
	public void originalWindow(WebDriver driver) {
	    for(String handle : driver.getWindowHandles()) {
	        if (!handle.equals(originalHandle)) {
	            driver.switchTo().window(handle);
	            driver.close();
	        }
	    }
	    driver.switchTo().window(originalHandle);
	}
	
	//read
	public String read(WebDriver driver, By oby) {
		WebDriverWait wait= new WebDriverWait(driver, 10);
		WebElement usernameNext = wait.until(ExpectedConditions.elementToBeClickable(oby));	
		return driver.findElement(oby).getText();
	}
	
	//write
	public void type(WebDriver driver, By oby, String keys) {
		WebDriverWait wait= new WebDriverWait(driver, 10);
		WebElement usernameNext = wait.until(ExpectedConditions.elementToBeClickable(oby));		
		driver.findElement(oby).sendKeys(keys);
	}
	
	
	//Scroll
	public void toTop(WebDriver driver) {
		Actions action = new Actions(driver);
		action.sendKeys(Keys.CONTROL).sendKeys(Keys.HOME).perform();
		System.out.println("Scrolled to the Top");
	}
	public void tobottom(WebDriver driver) {
		Actions action = new Actions(driver);
		action.sendKeys(Keys.CONTROL).sendKeys(Keys.END).perform();
		System.out.println("Scrolled to the Bottom");
	}
	
	
}
