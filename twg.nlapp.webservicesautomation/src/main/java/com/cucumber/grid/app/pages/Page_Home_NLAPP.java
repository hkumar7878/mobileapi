package com.cucumber.grid.app.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.cucumber.parallel.baseSteps.steps.NewBaseClass;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class Page_Home_NLAPP extends NewBaseClass{
	
	public static AppiumDriver<WebElement> driver;
	public Page_Home_NLAPP(AppiumDriver<WebElement> driver)
	{
		this.driver=(AppiumDriver<WebElement>) driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@AndroidFindBy(xpath="//android.widget.FrameLayout[@content-desc='Products']/android.widget.ImageView")
	public AndroidElement tabProduct;
	
	public Page_Products_NLAPP clickTab_Product()
	{
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tabProduct.click();
		return new Page_Products_NLAPP(driver);
	
	}
	
}


