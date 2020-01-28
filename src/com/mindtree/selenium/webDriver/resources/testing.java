package com.mindtree.selenium.webDriver.resources;

import java.text.NumberFormat;
import java.text.ParseException;

public class testing {

	public static void main(String[] args) throws ParseException {
		Number number=NumberFormat.getNumberInstance(java.util.Locale.US).parse("444,444");
		double numberDouble = number.doubleValue();
		  System.out.println(numberDouble);
	}

}
