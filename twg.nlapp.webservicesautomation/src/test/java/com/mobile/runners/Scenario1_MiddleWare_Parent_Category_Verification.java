package com.mobile.runners;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.cucumber.parallel.baseSteps.steps.NewBaseClass;
import com.cucumber.parallel.extent.ExtentManager;
import com.cucumber.parallel.extent.ExtentTestManager;

import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.testng.TestNGCucumberRunner;


@CucumberOptions(
		features="src/test/resources/features/Scenario1_MiddleWare_Parent_Category_Verification.feature",
		glue="com.nlapp.api"	
				
		)
public class Scenario1_MiddleWare_Parent_Category_Verification extends NewBaseClass {
	
	//private Scenario scenario;
	//private String scenarioName;
	//int i=0;
	@Test
	public void runCuke()
	{
		new TestNGCucumberRunner(getClass()).runCukes();
		
	}	
	

	@AfterClass
	public void quitDriver()
	{
		getDriver().quit();
	}

}
