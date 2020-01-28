package com.mindtree.selenium.webDriver.testNG;
import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class logging {
    final static Logger logger = Logger.getLogger(logging.class);
  
    public static void main(String args[]) {

    	logger.setLevel(Level.DEBUG);
      
        logger.error("Critical message, almost fatal");
        logger.warn("Warnings, which may lead to system impact");
        logger.info("Information");
        logger.debug("Debugging information ");
           
    }    
  
}


