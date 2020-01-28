package com.mindtree.selenium.webDriver.basic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Validator {

//Checks if element is enabled or disabled
	public boolean isEnabled(WebDriver driver, By oBy) {
		return driver.findElement(oBy).isEnabled();
	}

//Checks if element exists
	public boolean doesExist(WebDriver driver,  By oBy) {
	    try {
	        driver.findElement(oBy);
	        return true;
	    } catch (org.openqa.selenium.NoSuchElementException e) {
	        return false;
	    }
	}

//Checks if element is selected
		public boolean isSelected(WebDriver driver, By oBy) {
			return driver.findElement(oBy).isSelected();
		}
		
//Validate text	
	public boolean validateText(WebDriver driver, By oBy, String text) {
		if(driver.findElement(oBy).getText().equals(text))
			return true;
		else
			return false;
	}
	
	
}
