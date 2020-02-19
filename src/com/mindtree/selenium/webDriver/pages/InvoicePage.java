package com.mindtree.selenium.webDriver.pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InvoicePage {

	private static WebElement element = null;
	private static List<WebElement> elements = null;

	public int getInvoiceNumber(WebDriver driver) throws IOException, ParseException {
		Properties p = new Properties();
		FileInputStream file = new FileInputStream("..\\Assessment\\properties\\buying.properties");
		p.load(file);

		element=driver.findElement(By.xpath(p.getProperty("invoice-number")));
		String invoiceNumberString = element.getText();
		invoiceNumberString = invoiceNumberString.substring(28, invoiceNumberString.length());
		return Integer.valueOf(invoiceNumberString);
	}
}
