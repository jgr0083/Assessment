package com.mindtree.selenium.webDriver.testNG;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.mindtree.selenium.webDriver.basic.BrowserController;
import com.mindtree.selenium.webDriver.basic.WindowController;
import com.mindtree.selenium.webDriver.feedback.SendEmail;
import com.mindtree.selenium.webDriver.resources.Invoice;
import com.mindtree.selenium.webDriver.resources.RandomGenerator;
import com.mindtree.selenium.webDriver.resources.User;
import com.mindtree.selenium.webDriver.utils.JDBCDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class UserBuy {
	WebDriver driver;
	Invoice invoice = new Invoice();
	final static Logger logger = Logger.getLogger(UserBuy.class);
	SendEmail email = new SendEmail();
	User user;
	JDBCDriver jdbc = new JDBCDriver();
	Connection conn = jdbc.getConnection();

	@Test
	public void loginAsNewUser() throws FileNotFoundException, IOException {
		logger.info("Running New User Login Test");
		NewUserLogin stuff = new NewUserLogin();
		driver = stuff.openBrowserNonNG();
		stuff.createNewUser();
		stuff.login();
		this.user = stuff.getUser(); // Gets the user info from NewUseLogin
	}

	@Test(dependsOnMethods = { "loginAsNewUser" })
	public void addRandomItemToCart() throws FileNotFoundException, IOException, ParseException {
		WindowController ctrl = new WindowController(driver);
		RandomGenerator rand = new RandomGenerator();
		int randomSelect = rand.randomInt(0, 3);
		int cartID;
		ctrl.pause(1000);
		Properties p = new Properties();
		FileInputStream file = new FileInputStream("..\\Assessment\\properties\\buying.properties");
		p.load(file);

		List<WebElement> furnatureCategory = driver.findElements(By.cssSelector(".category-text"));
		furnatureCategory.get(randomSelect).click();
		logger.info("Selecting furnature type");

		randomSelect = rand.randomInt(0, 8);
		List<WebElement> furnature = driver.findElements(By.cssSelector(".btn1.mat-raised-button.mat-button-base"));
		furnature.get(randomSelect).click();
		logger.info("Selecting furnature");

		// WebElement details =
		// driver.findElement(By.cssSelector(".rounded.ng-star-inserted"));
		logger.info("Recording Furnatue Details:");

		invoice.setName(driver.findElement(By.xpath(p.getProperty("selected-product") + "div[1]/div[1]/h1")).getText());
		logger.info("Name: " + invoice.getName(0));

		String priceString = driver.findElement(By.xpath(p.getProperty("selected-product") + "div[3]/span[1]"))
				.getText();
		priceString = priceString.substring(1, priceString.length());
		Number number = NumberFormat.getNumberInstance(java.util.Locale.US).parse(priceString);
		double price = number.doubleValue();
		invoice.setPrice(price);
		logger.info("Price: " + invoice.getPrice(0));

		invoice.setDescription(
				driver.findElement(By.xpath(p.getProperty("selected-product") + "div[4]/div[2]")).getText());
		logger.info("Description: " + invoice.getDescription(0));

		invoice.setMaterial(
				driver.findElement(By.xpath(p.getProperty("selected-product") + "div[5]/div[2]")).getText());
		logger.info("Material: " + invoice.getMaterial(0));

		invoice.setColor(driver.findElement(By.xpath(p.getProperty("selected-product") + "div[6]/div[2]")).getText());
		logger.info("Color: " + invoice.getColor(0));

		invoice.setWarranty(
				driver.findElement(By.xpath(p.getProperty("selected-product") + "div[7]/div[2]")).getText());
		logger.info("Warranty: " + invoice.getWarranty(0));

		ctrl.click(driver, By.cssSelector(".btn.btn-outline-success.space.ng-star-inserted"));
		logger.info("Add to Cart");

		ctrl.pause(2000);
		
		cartID=jdbc.getCartID(conn, user.getUserID());
		
		if(cartID==0) {
			System.out.println("ERROR: Could not find cart id in the DB");
			logger.error("Could not find Cart ID in the DB");
			System.exit(0);
		}else {
			logger.info("cart ID found: " + cartID);
			user.setCartID(cartID);		
			}
		
	}


	@Test(dependsOnMethods = { "addRandomItemToCart" })
	public void placeOrder() throws FileNotFoundException, IOException, ParseException {
		WindowController ctrl = new WindowController(driver);
		RandomGenerator rand = new RandomGenerator();
		int randomSelect = rand.randomInt(0, 2);
		int dbUserID;
		Properties p = new Properties();
		FileInputStream file = new FileInputStream("..\\Assessment\\properties\\buying.properties");
		p.load(file);
		
		List<WebElement> menuButtons = driver.findElements(By.cssSelector(".mat-button.mat-button-base"));
		menuButtons.get(1).click();
		logger.info("Opening Cart");

		String totalString = driver
				.findElement(By.xpath(p.getProperty("cart-layout") + "div[2]/div/div[2]/div/div/h5[2]")).getText();
		totalString = totalString.substring(1, totalString.length());
		Number number = NumberFormat.getNumberInstance(java.util.Locale.US).parse(totalString);
		double total = number.doubleValue();
		invoice.setTotal(total);
		System.out.println("Total: " + invoice.getTotal());

		ctrl.click(driver, By.cssSelector(".btn.btn-dark"));// Place order
		logger.info("Placing Order");

		List<WebElement> paymentOptions = driver
				.findElements(By.cssSelector(".ui-radiobutton-box.ui-widget.ui-state-default"));// Radio buttons
		paymentOptions.get(randomSelect).click();

		ctrl.click(driver, By.cssSelector(".btn.btn-success.ng-star-inserted"));// Place order
		logger.info("Submit Order");

		String invoiceNumberString = driver
				.findElement(
						By.xpath(p.getProperty("cart-layout") + "app-payment-layout/div/p-card/div/div/div/div/h3"))
				.getText();
		invoiceNumberString = invoiceNumberString.substring(28, invoiceNumberString.length());
		int invoiceNumber = Integer.valueOf(invoiceNumberString);
		invoice.setInvoiceNumber(invoiceNumber);
		System.out.println("Invoice #: " + invoice.getInvoiceNumber());

		ctrl.pause(2000);
		
		dbUserID=jdbc.verifyUserIDFromInvoice(conn, invoice.getInvoiceNumber());
		
		if(dbUserID == 0) {
			System.out.println("ERROR: No Invoice was found in DB with the invoice number of " + invoice.getInvoiceNumber());
			logger.error("No Invoice was found in DB with the invoice number of " + invoice.getInvoiceNumber());
			System.exit(0);
		}else if(user.getUserID() != dbUserID){
			System.out.println("ERROR: User ID does not match the User ID in the DB");
			logger.error("User ID does not match the User ID in the DB");
			System.exit(0);
		}else {
			logger.info("User ID Matched. Invoice is linked to correct user ");
			}
		
		
	}

	@AfterTest
	public void close() {
		email.send("UserBuy");
		logger.info("Test Complete");
		// driver.close();
	}
}
