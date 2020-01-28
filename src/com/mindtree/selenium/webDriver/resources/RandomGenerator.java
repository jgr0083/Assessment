package com.mindtree.selenium.webDriver.resources;

import java.util.Random;

public class RandomGenerator {

	private Random rand= new Random();
	
	public int randomInt(int add, int num) {
		  return rand.nextInt(num) +add;
	}
	
	public String randomString(int length) {
		  char c = (char) (rand.nextInt(26) + 'a');
		  String ranName = "" + c;
	      
		  for(int i = 0; i<length; i++) {
			  c = (char) (rand.nextInt(26) + 'a');
			  ranName=ranName+c;
		  }
		  
		return ranName;
	}
	
}
