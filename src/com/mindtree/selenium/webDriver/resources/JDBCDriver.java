package com.mindtree.selenium.webDriver.resources;
import java.sql.*;
import java.util.Set;

import org.apache.log4j.Logger;

import com.mindtree.selenium.webDriver.testNG.MenuTest;
public class JDBCDriver {

	final static Logger logger = Logger.getLogger(JDBCDriver.class);
	
	public Connection getConnection() {
		try {
			//Sets local timezone
			Connection conn = DriverManager.getConnection("jdbc:mysql://okmry52647dns.eastus.cloudapp.azure.com:3306/bmf-abhishek-jun19-dev?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "sdetuser1", "Welcome123$");
			logger.info("Succesfully connected to DB");
			return conn;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void selectAllFrom(String query, Connection conn, String col1, String col2) {
		try {
			Statement myStmt = conn.createStatement();
			ResultSet myRs = myStmt.executeQuery(query);
			
			while (myRs.next()) {
				System.out.println(myRs.getString(col1) + "," +
								   myRs.getString(col2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getUserID(Connection conn, String email, String number) {

		try {
			Statement myStmt = conn.createStatement();
			ResultSet myRs = myStmt.executeQuery("SELECT * FROM profile WHERE email_id='"+email+"' AND mobile_no=" + number);

			while (myRs.next()) {
				System.out.println("User ID = " + myRs.getInt("user_id"));
				logger.info("User ID = " + myRs.getInt("user_id"));
				return myRs.getInt("user_id");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public int getCartID(Connection conn, int userId) {

		try {
			Statement myStmt = conn.createStatement();
			ResultSet myRs = myStmt.executeQuery("SELECT * FROM cart WHERE profile_user_id="+userId);

			while (myRs.next()) {
				System.out.println("Cart ID = " + myRs.getInt("cart_id"));
				logger.info("Cart ID = " + myRs.getInt("cart_id"));
				return myRs.getInt("cart_id");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public int verifyUserIDFromInvoice(Connection conn, int invoiceID) {
		
		try {
			Statement myStmt = conn.createStatement();
			ResultSet myRs = myStmt.executeQuery("SELECT * FROM product_order WHERE order_reference="+invoiceID);

			while (myRs.next()) {
				System.out.println("User ID = " + myRs.getInt("user_user_id"));
				logger.info("Cart ID = " + myRs.getInt("user_user_id"));
				return myRs.getInt("user_user_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
		
	}
	
}
