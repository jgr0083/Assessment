package com.mindtree.selenium.webDriver.miscTests;
import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

//This class is for debugging the logger
public class logging {
    final static Logger logger = Logger.getLogger(logging.class);
  
    public static void main(String args[]) {

        logger.error("Critical message, almost fatal");
        logger.warn("Warnings, which may lead to system impact");
        logger.info("Information");
        logger.debug("Debugging information ");
           
    }    
  
}


