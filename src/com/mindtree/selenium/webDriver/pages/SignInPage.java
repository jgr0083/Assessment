package com.mindtree.selenium.webDriver.pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mindtree.selenium.webDriver.resources.User;

public class SignInPage {


	private static WebElement element = null;
	private static List<WebElement> elements = null;
	Properties p = new Properties();
	FileInputStream file=null;
	

	public SignInPage() throws IOException{
		file = new FileInputStream("..\\Assessment\\properties\\signin.properties");
		p.load(file);
	}

	public void createAccount(WebDriver driver) {
		element = driver.findElement(By.cssSelector(p.getProperty("createAccount")));
		element.click();
	}

	public void setUsername(WebDriver driver, String name) {
		element = driver.findElement(By.id(p.getProperty("username")));
		element.sendKeys("Test Name" + name);
	}
	
	public void setPassword(WebDriver driver, String password) {
		elements = driver.findElements(By.id(p.getProperty("password")));
		elements.get(1).sendKeys(password);
	}
	
	public void setPhone(WebDriver driver, String phone) {
		element = driver.findElement(By.id(p.getProperty("phone")));
		element.sendKeys(phone);
	}
	
	public void setEmail(WebDriver driver, String name) {
		elements = driver.findElements(By.id(p.getProperty("email")));
		elements.get(1).sendKeys("Test_Name" + name + "@Test.com");
	}
	
	public void submit(WebDriver driver) {
		elements = driver.findElements(By.cssSelector(p.getProperty("submit")));
		elements.get(1).click();

	}
	
	public void close(WebDriver driver) {
		elements= driver.findElements(By.cssSelector(".mat-icon.notranslate.material-icons.mat-icon-no-color"));
		elements.get(11).click();
	}
	
	public void login(WebDriver driver, User user) {
		elements = driver.findElements(By.id(p.getProperty("email")));
		elements.get(0).sendKeys(user.getEmail());

		elements = driver.findElements(By.id(p.getProperty("password")));
		elements.get(0).sendKeys(user.getPassword());

		elements = driver.findElements(By.cssSelector(".btn.btn-primary.btn-lg.btn-block"));
		elements.get(0).click();
	}
}
