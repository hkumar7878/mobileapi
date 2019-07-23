package Archieve;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.containsString;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.equalTo;

public class TC_01_VerifyCategory {
	String response;
	
	@Test
	public void getCategory_API()
	{
	response=given().header("Ocp-Apim-Subscription-Key","c68fa40a7cb443ecbba9866f63e9edf2").when()
			.get("https://twg.azure-api.net/twlYourWarehouseTest/Category.json?CategoryId=12704-mobilephones-smartphone")
		.asString();
	int statusCode=given().header("Ocp-Apim-Subscription-Key","c68fa40a7cb443ecbba9866f63e9edf2").when()
			.get("https://twg.azure-api.net/twlYourWarehouseTest/Category.json?CategoryId=12704-mobilephones-smartphone")
		.statusCode();
	System.out.println("Status code is" + statusCode);
	System.out.println("Response is ---->"+response);
	
	if (statusCode == 200) {
		JsonPath jp = new JsonPath(response);
		//String attrTxt = jp.getString("attributionText");
		//System.out.println("Attribution texyt is" + attrTxt);
		List<String> names = from(response).getList("categoryTree.name");
		//List<String> titles = from(response).getList("data.results.title");
		
		for (String name : names) {
			System.out.println("Title is" + name);
		}
	}
	}
	
	@Test
	public void verifyCategory_Device()
	{
		
	}
	

}
