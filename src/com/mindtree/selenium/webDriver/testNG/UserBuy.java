package com.mindtree.selenium.webDriver.testNG;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

import com.mindtree.selenium.webDriver.basic.WindowController;
import com.mindtree.selenium.webDriver.feedback.SendEmail;
import com.mindtree.selenium.webDriver.pages.CartPage;
import com.mindtree.selenium.webDriver.pages.HomePage;
import com.mindtree.selenium.webDriver.pages.InvoicePage;
import com.mindtree.selenium.webDriver.pages.ItemPage;
import com.mindtree.selenium.webDriver.pages.ItemSearchPage;
import com.mindtree.selenium.webDriver.pages.MenuBar;
import com.mindtree.selenium.webDriver.pages.PaymentPage;
import com.mindtree.selenium.webDriver.resources.Invoice;
import com.mindtree.selenium.webDriver.resources.RandomGenerator;
import com.mindtree.selenium.webDriver.resources.RestResources;
import com.mindtree.selenium.webDriver.resources.User;
import com.mindtree.selenium.webDriver.utils.JDBCDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.util.Properties;

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
		HomePage home = new HomePage();
		ItemSearchPage search = new ItemSearchPage();
		ItemPage item = new ItemPage();
		ctrl.pause(1000);
		
		home.randomCategory(driver, randomSelect);
		logger.info("Selecting furnature type");

		randomSelect = rand.randomInt(0, 8);
		search.select(driver, randomSelect);
		logger.info("Selecting furnature");

		invoice.setName(item.getName(driver));
		logger.info("Name: " + invoice.getName(0));


		invoice.setPrice(item.getPrice(driver));
		logger.info("Price: " + invoice.getPrice(0));

		invoice.setDescription(item.getDescription(driver));
		logger.info("Description: " + invoice.getDescription(0));

		invoice.setMaterial(item.getMaterial(driver));
		logger.info("Material: " + invoice.getMaterial(0));

		invoice.setColor(item.getColor(driver));
		logger.info("Color: " + invoice.getColor(0));

		invoice.setWarranty(item.getWarranty(driver));
		logger.info("Warranty: " + invoice.getWarranty(0));

		item.addToCart(driver);
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
		MenuBar menu= new MenuBar();
		CartPage cart= new CartPage();
		PaymentPage pay=new PaymentPage();
		InvoicePage invoicePage=new InvoicePage();
		int randomSelect = rand.randomInt(0, 2);
		int dbUserID;
		
		menu.cart(driver);
		logger.info("Opening Cart");
		
		invoice.setTotal(cart.getTotal(driver));
		logger.info("Setting Total: " + invoice.getTotal());

		cart.placeOrder(driver);
		logger.info("Placing Order");

		pay.selectPaymentType(driver, randomSelect);
		logger.info("Selecting payment type: " + randomSelect);

		pay.pay(driver);// Place order
		logger.info("Submit Order");

		invoice.setInvoiceNumber(invoicePage.getInvoiceNumber(driver));
		logger.info("Invoice #: " + invoice.getInvoiceNumber());

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
	
	@Test(dependsOnMethods = { "placeOrder" })
	public void getResponse() throws ClientProtocolException, IOException {

		Properties p = new Properties();		
		FileInputStream file = new FileInputStream("..\\Assessment\\properties\\rest.properties");
		p.load(file);
		RestResources rest=new RestResources();

		rest.getResponse(p.getProperty("endpoint"), p.getProperty("order")+user.getUserID(), Integer.toString(invoice.getInvoiceNumber()));
	}
	
	@AfterTest
	public void close() {
		email.send("UserBuy");
		logger.info("Test Complete");
		// driver.close();	//Commented out for testing purposes
	}
}
