package com.cucumber.custoemlisteners;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import com.cucumber.grid.utilities.DriverFactory;

public class CustomerListener implements ISuiteListener {
	public AppiumDriverLocalService service;
	public FileInputStream fis;
	public static Properties Config = new Properties();
	public static Logger logger = Logger.getLogger("devpinoyLogger");
	static String NodePath="C:/Program Files/nodejs/node.exe";
	static String appiumPath="C:/Users/308758/AppData/Local/Programs/Appium/resources/app/node_modules/appium/build/lib/main.js";


	@Override
	public void onStart(ISuite suite) {
	
		DriverFactory.setConfigPropertyFile(System.getProperty("user.dir") + "//src//main//java//com//cucumber//grid//configuration//configuration.properties");
		try {
			System.out.println("Inside custom suite listener class");
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

	@Override
	public void onFinish(ISuite suite) {
	
		service.stop();
	}

}
