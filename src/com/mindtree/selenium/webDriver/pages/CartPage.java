package com.mindtree.selenium.webDriver.pages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage {

	private static WebElement element = null;
	private static List<WebElement> elements = null;
	
	public double getTotal(WebDriver driver) throws IOException, ParseException {
		Properties p = new Properties();
		FileInputStream file = new FileInputStream("..\\Assessment\\properties\\buying.properties");
		p.load(file);
		
		element=driver.findElement(By.xpath(p.getProperty("cart-total")));
		String totalString = element.getText();													//gets Total as String
		totalString = totalString.substring(1, totalString.length());							//Removes rupee symbol
		Number number = NumberFormat.getNumberInstance(java.util.Locale.US).parse(totalString);	//Converts to number and removes any commas
		double total = number.doubleValue();													//Converts number to double
		return total;
	}
	
	public void placeOrder(WebDriver driver) throws IOException{
		Properties p = new Properties();
		FileInputStream file = new FileInputStream("..\\Assessment\\properties\\buying.properties");
		p.load(file);
		
		element=driver.findElement(By.cssSelector(p.getProperty("cart-place-order")));
		element.click();
		
	}
}
