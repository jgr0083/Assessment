package com.mindtree.selenium.webDriver.logger;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class listenerTesting implements ITestListener{

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("Test Started: " + result.getName());
	}
	
	
	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("Test Success: " + result.getName());
		
	}
	
	
	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("Test Failed: " + result.getName());
		
	}
	
	
	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("Test Skipped: " + result.getName());
		
	}

	
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("Test Failed within Success Percentage: " + result.getName());
		
	}
	
	
	@Override
	public void onStart(ITestContext context) {
		System.out.println("Started: " + context.getName());
		
	}
	
	
	@Override
	public void onFinish(ITestContext context) {
		System.out.println("Finished: " + context.getName());
		
	}

}
