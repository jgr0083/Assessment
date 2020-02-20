package com.mindtree.selenium.webDriver.miscTests;
import java.sql.*;

import java.text.ParseException;

import com.mindtree.selenium.webDriver.utils.JDBCDriver;

public class JDBCTest {
	public static void main(String[] args) {
		JDBCDriver jdbc=new JDBCDriver();
		Connection conn = jdbc.getConnection();
		
		jdbc.selectAllFrom("SELECT * FROM cart", conn, "cart_id_", "quantity");
		
	}

		
}
