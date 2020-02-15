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

public class PaymentPage {

	private static WebElement element = null;
	private static List<WebElement> elements = null;

	public void selectPaymentType(WebDriver driver, int select) throws IOException, ParseException {
		Properties p = new Properties();
		FileInputStream file = new FileInputStream("..\\Assessment\\properties\\buying.properties");
		p.load(file);

		elements = driver.findElements(By.cssSelector(p.getProperty("payment-type")));// Radio buttons
		elements.get(select).click();
	}

	public void pay(WebDriver driver) throws IOException, ParseException {
		Properties p = new Properties();
		FileInputStream file = new FileInputStream("..\\Assessment\\properties\\buying.properties");
		p.load(file);
		
		element=driver.findElement(By.cssSelector(p.getProperty("pay")));
		element.click();
	}
	
	
}
