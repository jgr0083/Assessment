package com.mindtree.selenium.webDriver.pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ItemSearchPage {

	private static WebElement element = null;
	private static List<WebElement> elements = null;

	public void select(WebDriver driver, int select) throws IOException {
		Properties p = new Properties();
		FileInputStream file = new FileInputStream("..\\Assessment\\properties\\buying.properties");
		p.load(file);

		elements = driver.findElements(By.cssSelector(p.getProperty("furniture-select")));
		elements.get(select).click();
		
	}

}
