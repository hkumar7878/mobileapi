package com.cucumber.parallel.baseSteps.steps;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.cucumber.custoemlisteners.CustomerListener;
import com.cucumber.grid.utilities.DriverFactory;
import com.cucumber.parallel.extent.ExtentTestManager;
import com.google.common.util.concurrent.Service;
//import com.cucumber.parallel.extent.ExtentTestManager;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class NewBaseClass extends CustomerListener{
	
	public String browserName;
	DesiredCapabilities cap=null;
	//public Properties Config = new Properties();
	public static AppiumDriver<WebElement> driver;
	public static ThreadLocal<AppiumDriver> dr = new ThreadLocal<AppiumDriver>();
	public static ThreadLocal<ExtentTest> exTest= new ThreadLocal<ExtentTest>(); 
	public static ExtentTest test;
	public static String screenshotPath;
	public static String screenshotName;
	public static Logger logger = Logger.getLogger("devpinoyLogger");
	public FileInputStream fis;
	public String executionType;
	private static HashMap<Integer,String> scenarios;
	static String NodePath="C:/Program Files/nodejs/node.exe";
	static String appiumPath="C:/Users/308758/AppData/Local/Programs/Appium/resources/app/node_modules/appium/build/lib/main.js";
	//static AppiumDriverLocalService service;
	String node_path="C:/Program Files/nodejs/node.exe";
	String appiumjs_path="C:\\Users\\308758\\AppData\\Local\\Programs\\Appium\\resources\\app\\node_modules\\appium\\build\\lib\\main.js";
	
	private String getScenario(){
	    Thread currentThread = Thread.currentThread();
	    int threadID = currentThread.hashCode();
	    return scenarios.get(threadID);
	}
	
	public void setUpFramework()
	{
		DriverFactory.setConfigPropertyFile(System.getProperty("user.dir") + "//src//main//java//com//cucumber//grid//configuration//configuration.properties");
		try {
			fis= new FileInputStream(DriverFactory.getConfigPropertyFile());			
			service=AppiumDriverLocalService.buildService(new AppiumServiceBuilder().
					usingDriverExecutable(new File(NodePath)).withAppiumJS(new File(appiumPath))
					.withIPAddress("127.0.0.1").usingAnyFreePort());
			System.out.println("Starting the appium server ");
			service.start();
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}	
		try {
			Config.load(fis);
			logger.info("Property file loaded!!!!");			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// To run or debug the following beforeClass method, run it or debug it from testng.xml file not directly from this class.
	@Parameters({"deviceName_","UUID_","platformVersion_"})
	@BeforeClass
	public void deviceSetUp(String deviceName_,String UUID_,String platformVersion){
		try {
			setUpFramework();
			
			if(System.getenv("ExecutionType")!=null && !System.getenv("ExecutionType").isEmpty())
				executionType=System.getenv("ExecutionType");		
			else			
				executionType=Config.getProperty("executionType");

			if (executionType.equalsIgnoreCase("Android Only")) {
				{				
					DesiredCapabilities cap= new DesiredCapabilities();
					cap.setCapability("deviceName", deviceName_);
					cap.setCapability("udid", UUID_);
					cap.setCapability("platformName", "Android");
					cap.setCapability("platformVersion", platformVersion);					
					//cap.setCapability("appPackage","nz.co.noelleeming.mynlapp.qat");
					cap.setCapability("appPackage","nz.co.noelleeming.mynlapp.staging");
					cap.setCapability("appActivity","nz.co.noelleeming.mynlapp.MainActivity");				
					driver = new AppiumDriver<WebElement>(service.getUrl(), cap);
					System.out.println("Starting the appium server");
					logger.info("Starting the Appium session");
				}
				
			setWebDriver(driver);
			System.out.println(dr.get());

			logger.info("inside the base class method browser initilization");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception is " + e.getMessage());
			e.printStackTrace();
		}
	}			
	
	
	public void configureLogging()
	{
		String log4jConfigFile=System.getProperty("user.dir") +"//src//test//resources//properties//log4j1.properties";
		PropertyConfigurator.configure(log4jConfigFile);
	}
	
	
	public static AppiumDriver<MobileElement> getDriver() {
        return dr.get();
    }
	public static void setWebDriver(AppiumDriver<WebElement> driver){
		dr.set(driver);
	}
	
	public ExtentTest getExtTest() {
        return exTest.get();
    }
	
	public void setExtentTest(ExtentTest et){
		exTest.set(et);
	}	
	public void reportPass(String msg){
		getExtTest().log(LogStatus.PASS, msg);
	}	
	public void reportFailure(String msg){
		getExtTest().log(LogStatus.FAIL, msg);
		captureScreenshot();
		
	}
	
	public void captureScreenshot()  {

		File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		Date d = new Date();
		screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
		try {
			FileUtils.copyFile(scrFile,new File(System.getProperty("user.dir") + "\\reports\\" + screenshotName));
		} catch (IOException e) {
	
		}
		getExtTest().log(LogStatus.INFO, " Screen shot--> " + test.addScreenCapture(System.getProperty("user.dir") + "\\reports\\"+screenshotName));

	}
	
	public void addLog(String msg,String browser1)
	{
		logger.debug("Running thread value is : " + getThreadValue(dr.get())+   "   for Browser : " + "  "+ browser1 + "" +msg);	
		logger.info("Browser : " + browser1 + "" +msg);
	}
	/*public void passInfo(String messaage)
	{
		ExtentTestManager.testReport.get().pass(messaage);
	}
	
	public void failInfo(String messaage)
	{
		ExtentTestManager.testReport.get().fail(messaage);
	}*/
	
	public String getThreadValue(Object value)
	{
		String text=value.toString();
		String [] nextText=text.split(" ");
		String text2=nextText[nextText.length-1].replace("(", "").replace(")","");
		String [] newText2=text2.split("-");	
		String reqText=newText2[newText2.length-1];
		System.out.println("Thread value is " + reqText);
		
		return reqText;
	}
	/*public void logInfo(String messaage)
	{
		try{
			ExtentTestManager.testReport.get().info(messaage);
		}
		catch(Exception e){
			System.out.println("Error is" + e.getMessage());
		}
	}
*/	
	public void quitAppiumDriver()
	{
		getDriver().quit();
		//service.stop();
	}

}
