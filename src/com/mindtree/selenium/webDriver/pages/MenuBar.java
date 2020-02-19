package com.mindtree.selenium.webDriver.pages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MenuBar {

	private static WebElement element = null;
	private static List<WebElement> elements = null;
	Properties p = new Properties();
	FileInputStream file=null;

	public MenuBar() throws IOException{
		file = new FileInputStream("..\\Assessment\\properties\\menu.properties");
		p.load(file);
	}

	public void home(WebDriver driver){
		element = driver.findElement(By.cssSelector(p.getProperty("home")));
		element.click();
	}

	public void allFurnature(WebDriver driver){
		element = driver.findElement(By.cssSelector(p.getProperty("allFurnature")));
		element.click();
	}

	public void search(WebDriver driver, String search){
		element = driver.findElement(By.xpath(p.getProperty("searchButton")));
		element.click();
		element = driver.findElement(By.xpath(p.getProperty("searchString")));
		element.sendKeys(search);
		element = driver.findElement(By.xpath(p.getProperty("searchSubmit")));
		element.click();
	}

	public void wishlist(WebDriver driver){
		element = driver.findElement(By.xpath(p.getProperty("wishlist")));
		element.click();
	}
	
	public void cart(WebDriver driver){
		element = driver.findElement(By.xpath(p.getProperty("cart")));
		element.click();
	}
	
	public void support(WebDriver driver){
		element = driver.findElement(By.xpath(p.getProperty("support")));
		element.click();
	}
	
	public void signin(WebDriver driver){
		element = driver.findElement(By.xpath(p.getProperty("signin")));
		element.click();
	}
}
