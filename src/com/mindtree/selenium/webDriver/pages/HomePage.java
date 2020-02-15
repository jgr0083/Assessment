package com.mindtree.selenium.webDriver.pages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {

	private static WebElement element = null;

	public void Browse(WebDriver driver, String category) throws IOException {
		Properties p = new Properties();
		FileInputStream file = new FileInputStream("..\\Assessment\\properties\\categories.properties");
		p.load(file);

		switch (category) {
		case "chairs":
			element = driver.findElement(By.cssSelector(p.getProperty("chairs")));
			element.click();
			break;
		case "sofas":
			element = driver.findElement(By.cssSelector(p.getProperty("sofas")));
			element.click();
			break;
		case "tables":
			element = driver.findElement(By.cssSelector(p.getProperty("tables")));
			element.click();
			break;
		case "beds":
			element = driver.findElement(By.cssSelector(p.getProperty("beds")));
			element.click();
			break;
		case "all":
			element = driver.findElement(By.cssSelector(p.getProperty("allFurnature")));
			element.click();
			break;
		}
	}
	

	public void randomCategory(WebDriver driver, int category) throws IOException {
		Properties p = new Properties();
		FileInputStream file = new FileInputStream("..\\Assessment\\properties\\categories.properties");
		p.load(file);

		switch (category) {
		case 0:
			element = driver.findElement(By.cssSelector(p.getProperty("chairs")));
			element.click();
			break;
		case 1:
			element = driver.findElement(By.cssSelector(p.getProperty("sofas")));
			element.click();
			break;
		case 2:
			element = driver.findElement(By.cssSelector(p.getProperty("tables")));
			element.click();
			break;
		case 3:
			element = driver.findElement(By.cssSelector(p.getProperty("beds")));
			element.click();
			break;
		case 4:
			element = driver.findElement(By.cssSelector(p.getProperty("allFurnature")));
			element.click();
			break;
		}
	}

}
