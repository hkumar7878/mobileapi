package com.nlapp.api;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import io.restassured.path.json.JsonPath;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.cucumber.grid.app.pages.Page_Home_NLAPP;
import com.cucumber.grid.app.pages.Page_Products_NLAPP;
import com.cucumber.parallel.baseSteps.steps.NewBaseClass;
import com.cucumber.parallel.extent.ExtentTestManager;
import com.nlapp.utility.AppUtility_NLApp;
import com.nlapp.utility.GenericUtility_NLApp;

import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class TC_01_MiddleWare_Parent_Category_Verification extends NewBaseClass{
	
	protected Scenario scenario;
	static String scenarioName;
	static int i=0;
	String response;
	List<String> names;
	List<String> prntCateNames= new ArrayList<String>();
	Page_Products_NLAPP obj_Products;
	private String query;
	Connection connection;
	Statement stmt;
	ResultSet rs;
	//List<String> dbParentCtryNames= new ArrayList<String>();
	String[][] apiParentCtryNames;
	String [][] dbParentCtryNames;
	
		
	/*@Given("^user access DB and fetch category data$")
	public void user_access_DB_and_fetch_category_data() throws Throwable {
		//String ctryArray [][]=null;
		try{
		query = "SELECT * FROM [import].[EP_Category] where ParentCategoryPath is null and Name not in ('Clearance Centre','Gift Card')"; 
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		connection = DriverManager.getConnection("jdbc:sqlserver://twgasql01d.database.windows.net;database=nlmobiletest;user=Support;password=H0llywood$;Integrated Security=true");
        stmt = connection.createStatement();
        rs = stmt.executeQuery(query);
        while(rs.next()){
        	System.out.println("Branch Code in Database is ----> "+rs.getString("Name"));
        	dbParentCtryNames.add(rs.getString("Name"));
          }
        String ctryArray [][] = new String[dbParentCtryNames.size()+1][1];
		
		for(int i=0;i<dbParentCtryNames.size();i++)
		{
			ctryArray[i+1][0]=dbParentCtryNames.get(i);
			System.out.println("Value of two dimensional array is--------->"+ctryArray[i+1][0]);
		}
		ExtentTestManager.logInfo(MarkupHelper.createLabel("Parent Category fetched from API are as follow", ExtentColor.BLUE));
		ExtentTestManager.logInfo(MarkupHelper.createTable(ctryArray));
		}
        catch (Exception e)
        {
        	System.out.println("Exception occured " + e.getMessage());
        }
        	
        }*/
	
	
	/*@Given("^user access Category API and fetch category data$")
	public void user_access_Category_API_and_fetch_category_data() throws Throwable {
		response=given().header("Ocp-Apim-Subscription-Key","c68fa40a7cb443ecbba9866f63e9edf2").when()
				.get("https://twg.azure-api.net/twlYourWarehouseTest/Category.json?CategoryId=root&IncludeNonBrowse=N")
			.asString();
		int statusCode=given().header("Ocp-Apim-Subscription-Key","c68fa40a7cb443ecbba9866f63e9edf2").when()
				.get("https://twg.azure-api.net/twlYourWarehouseTest/Category.json?CategoryId=12704-mobilephones-smartphone")
			.statusCode();
		System.out.println("Status code is" + statusCode);
		System.out.println("Response is ---->"+response);
		
		if (statusCode == 200) {
			JsonPath jp = new JsonPath(response);			
			names = from(response).getList("categoryTree.name");			
			for (String prntCtryName : names) {
				System.out.println("Title is" + prntCtryName);
				apiParentCtryNames.add(prntCtryName);
				
			}
			System.out.println("Parent Category list from API respose is ------------->" + apiParentCtryNames);
			
			ExtentTestManager.logInfo("Parent Categories names fetched from GetCategory API response are " + apiParentCtryNames);
		}
		else
			ExtentTestManager.logFail("GetCategory API not reachable");
	}
*/	

	@Given("^user access DB and fetch category data$")
	public void user_access_DB_and_fetch_category_data(DataTable sqlQuery) throws Throwable {
		
		List<List<String>> data = sqlQuery.raw();
		dbParentCtryNames=AppUtility_NLApp.getSQLQueryData1(data.get(0).get(0));
		if(dbParentCtryNames!=null)
		{
			ExtentTestManager.logInfo(MarkupHelper.createLabel("Parent Category fetched from API are as follow", ExtentColor.BLUE));
			ExtentTestManager.logInfo(MarkupHelper.createTable(dbParentCtryNames));
		}
		else
		{}
        }
	@Given("^user access Category API and fetch category data$")
	public void user_access_Category_API_and_fetch_category_data(DataTable apiReqData) throws Throwable {
		try {
			List<List<String>> data = apiReqData.raw();
			int statusCode=GenericUtility_NLApp.getStatusCode(data.get(0).get(0), data.get(0).get(1), data.get(1).get(0));
			Assert.assertEquals(200, statusCode);
			apiParentCtryNames= AppUtility_NLApp.getAPIListValues(data.get(0).get(0), data.get(0).get(1), data.get(1).get(0));
			if(apiParentCtryNames!=null)
			{
				ExtentTestManager.logInfo(MarkupHelper.createLabel("Parent Category fetched from API are as follow", ExtentColor.BLUE));
				ExtentTestManager.logInfo(MarkupHelper.createTable(dbParentCtryNames));
			}
			
			else
			{
				
			}
		} catch (AssertionError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}

	@Given("^user launches the app$")
	public void user_launches_the_app() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Launching the app");
	   
	}

	@Then("^user clicks on the Products tab$")
	public void user_clicks_on_the_Products_tab() throws Throwable {
		try{
		System.out.println("Test");
		Page_Home_NLAPP obj_HomePage= new Page_Home_NLAPP(driver);
		
		obj_Products=obj_HomePage.clickTab_Product();
		System.out.println("");
		ExtentTestManager.logInfo("Clicking Products tab");
		}
		catch(Exception e)
		{
			System.out.println("Exception occured" + e.getLocalizedMessage());
			ExtentTestManager.logFail("Products tab is not clicked due to exception" + e.getMessage());;
		}
	   
	}
	
	@Then("^verify Products tab is displayed$")
	public void verify_Products_tab_is_displayed() throws Throwable {
		obj_Products= new Page_Products_NLAPP(driver);
		boolean flag=obj_Products.verifyProductsTab();
		if(flag)
		{
			System.out.println("Products tab is clicked successfully");
			ExtentTestManager.logPass("Product is clicked successfully");
		}
		else{
			
		System.out.println("Products tab could not be clicked");
		ExtentTestManager.logFail("Products tab could not be clicked");
		}
	   
	}


	@Then("^all categories on Product tab is fetched$")
	public void all_categories_on_Product_tab_is_fetched() throws Throwable {
		prntCateNames=obj_Products.getParentCategoryNames();
		System.out.println("Parent Category names are----->" + prntCateNames );
		ExtentTestManager.logInfo("Parent categories fetched from the NLAPP are" + prntCateNames);
		
		
	    
	}
	
	@Then("^verify the parent categories on the app with the response$")
	public void verify_the_parent_categories_on_the_app_with_the_response() throws Throwable {
		//assertThat(names).containsAll(prntCateNames);
		try{
		Assert.assertEquals(apiParentCtryNames, prntCateNames);
		ExtentTestManager.logPass("Parent Categories from both api response and mobile app are matching" + "API parent"
				+ " are " + apiParentCtryNames + "and App parent category is -->" + prntCateNames);
		}
		catch(AssertionError e)
		{
			System.out.println("Exception occured" + e.getMessage());
			ExtentTestManager.logFail("Parent Categories from both api response and mobile app NOT are matching" + "API parent"
					+ " are " + apiParentCtryNames + "and App parent category is -->" + prntCateNames);
		}
		}
}
