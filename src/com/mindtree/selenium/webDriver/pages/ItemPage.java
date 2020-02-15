package com.mindtree.selenium.webDriver.pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ItemPage {


	private static WebElement element = null;
	private static List<WebElement> elements = null;

	public String getName(WebDriver driver) throws IOException {
		Properties p = new Properties();
		FileInputStream file = new FileInputStream("..\\Assessment\\properties\\buying.properties");
		p.load(file);
		return driver.findElement(By.xpath(p.getProperty("product-name") )).getText();
	}

	public double getPrice(WebDriver driver) throws IOException, ParseException {
		Properties p = new Properties();
		FileInputStream file = new FileInputStream("..\\Assessment\\properties\\buying.properties");
		p.load(file);
		
		element=driver.findElement(By.xpath(p.getProperty("product-price")));
		String priceString = element.getText();															//gets text
		priceString = priceString.substring(1, priceString.length());									//removes rupee symbol 
		Number number = NumberFormat.getNumberInstance(java.util.Locale.US).parse(priceString);			//converts String to number and removes any commas
		double price = number.doubleValue();															//converts number to double
		return price;
	}
	
	public String getDescription(WebDriver driver) throws IOException, ParseException {
		Properties p = new Properties();
		FileInputStream file = new FileInputStream("..\\Assessment\\properties\\buying.properties");
		p.load(file);
		
		element=driver.findElement(By.xpath(p.getProperty("product-description")));
		return element.getText();
	}

	public String getMaterial(WebDriver driver) throws IOException, ParseException {
		Properties p = new Properties();
		FileInputStream file = new FileInputStream("..\\Assessment\\properties\\buying.properties");
		p.load(file);
		
		element=driver.findElement(By.xpath(p.getProperty("product-material")));
		return element.getText();
	}
	
	public String getColor(WebDriver driver) throws IOException, ParseException {
		Properties p = new Properties();
		FileInputStream file = new FileInputStream("..\\Assessment\\properties\\buying.properties");
		p.load(file);
		
		element=driver.findElement(By.xpath(p.getProperty("product-color")));
		return element.getText();
	}
	
	public String getWarranty(WebDriver driver) throws IOException, ParseException {
		Properties p = new Properties();
		FileInputStream file = new FileInputStream("..\\Assessment\\properties\\buying.properties");
		p.load(file);
		
		element=driver.findElement(By.xpath(p.getProperty("product-warranty")));
		return element.getText();
	}
	
	public void addToCart(WebDriver driver) throws IOException{
		Properties p = new Properties();
		FileInputStream file = new FileInputStream("..\\Assessment\\properties\\buying.properties");
		p.load(file);
		
		element=driver.findElement(By.cssSelector(p.getProperty("cart")));
		element.click();
	}
}
