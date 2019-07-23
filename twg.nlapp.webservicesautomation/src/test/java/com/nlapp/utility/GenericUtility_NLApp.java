package com.nlapp.utility;

import static io.restassured.RestAssured.given;

import java.util.List;

public class GenericUtility_NLApp {

	
	public static String[][] convertListIntoTwoDimArray(List<String> list)
	{
		String ctryArray1[][]=null;
		try {
			String ctryArray[][] = new String[list.size() + 1][1];

			for (int i = 0; i < list.size(); i++) {
				ctryArray[i + 1][0] = list.get(i);
				System.out.println("Value of two dimensional array is--------->" + ctryArray[i + 1][0]);
			}
			ctryArray1=ctryArray;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ctryArray1;
	}
	
	public static int getStatusCode(String subscriptionKeyName,String subscriptionKeyValue,String apiURL)
	{
		int statusCode=given().header(subscriptionKeyName,subscriptionKeyValue).when()
				.get(apiURL)
			.statusCode();
		return statusCode;
	}
}
