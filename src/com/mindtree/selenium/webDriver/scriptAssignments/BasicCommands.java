package com.mindtree.selenium.webDriver.scriptAssignments;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.mindtree.selenium.webDriver.basic.InvokeBrowserMain;
import com.mindtree.selenium.webDriver.basic.Validator;
import com.mindtree.selenium.webDriver.basic.WindowController;

public class BasicCommands {	
	
	public void runCommand(WebDriver driver) {
		WindowController ctrl = new WindowController(driver);
		Validator check = new Validator();
		
		
		driver.get("https://www.facebook.com");//Sometimes the page layout is different and messes with the script. Try running the script again to get the correct page
		
		driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);
		
		//Finds the footer tag of the page
		WebElement footer=driver.findElement(By.cssSelector(".pageFooterLinkList"));
		
		//grabs all <a> tagged links in the footer
		List <WebElement> links=footer.findElements(By.tagName("a")); 

		//the "-2" skips the settings and Activity Log links which are both hidden
		for(int i=0; i <links.size()-2;i++) {
			String title = links.get(i).getText();	//To avoid stale element
			System.out.println(title);
			links.get(i).click();
			System.out.println("  " + driver.getTitle());
			if(title.equals("Instagram"))	//The only link that opens a new tab. 
				ctrl.originalWindow(driver);//Handles new tab by closing and returning to original window
			else if(driver.getTitle() != null)	//If there is a title
				ctrl.back(driver);
			else {//Checks if nothing opens
				System.out.println("Error when opening the link: " + title);
				System.exit(0);
			}
				
			//refreshes elements when we go back
			footer=driver.findElement(By.cssSelector(".pageFooterLinkList"));
			links=footer.findElements(By.tagName("a"));
		}
		
		System.out.println("Passed with no errors");
		
		
	}
}
