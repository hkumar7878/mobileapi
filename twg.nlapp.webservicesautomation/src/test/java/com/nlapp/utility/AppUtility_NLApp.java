package com.nlapp.utility;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import io.restassured.path.json.JsonPath;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cucumber.parallel.extent.ExtentTestManager;

public class AppUtility_NLApp {

	public static String query;
	static Connection connection;
	static Statement stmt;
	static ResultSet rs;
	static List<String> dbParentCtryNames = new ArrayList<String>();
	public static String response;
	public static List<String> names;
	public static List<String> apiParentCtryNames;

	public static String[][] getSQLQueryData(String sqlQuery) {
		String [][] ctryArray1=null;

		try {
			query = sqlQuery;
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager
					.getConnection("jdbc:sqlserver://twgasql01d.database.windows.net;database=nlmobiletest;user=Support;password=H0llywood$;Integrated Security=true");
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				System.out.println("Branch Code in Database is ----> "+ rs.getString("Name"));
				dbParentCtryNames.add(rs.getString("Name"));
			}
			//String ctryArray[][];
			 String ctryArray[][] = new String[dbParentCtryNames.size() + 1][1];

			for (int i = 0; i < dbParentCtryNames.size(); i++) {
				ctryArray[i + 1][0] = dbParentCtryNames.get(i);
				System.out.println("Value of two dimensional array is--------->" + ctryArray[i + 1][0]);
			}
			ctryArray1=ctryArray;
			
			
	} catch (Exception e) {
			System.out.println("Exception occured " + e.getMessage());
			return null;
		}
		return ctryArray1;
	
		
		
	}
	
	public static String[][] getSQLQueryData1(String sqlQuery) {
		String [][] ctryArray1=null;

		try {
			query = sqlQuery;
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager
					.getConnection("jdbc:sqlserver://twgasql01d.database.windows.net;database=nlmobiletest;user=Support;password=H0llywood$;Integrated Security=true");
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				System.out.println("Branch Code in Database is ----> "+ rs.getString("Name"));
				dbParentCtryNames.add(rs.getString("Name"));
			}
			ctryArray1=GenericUtility_NLApp.convertListIntoTwoDimArray(dbParentCtryNames);
			
			
	} catch (Exception e) {
			System.out.println("Exception occured " + e.getMessage());
			return null;
		}
		return ctryArray1;
	
		
		
	}
	public static String[][] getAPIListValues(String subscriptionKeyName,String subscriptionKeyValue,String apiURL)
	{
		String [][] ctryArray1=null;
		
		try {
			response=given().header(subscriptionKeyName,subscriptionKeyValue).when().get(apiURL).asString();
			System.out.println("Response is ---->"+response);			
			JsonPath jp = new JsonPath(response);			
			names = from(response).getList("categoryTree.name");			
				for (String prntCtryName : names) {
					System.out.println("Title is" + prntCtryName);
					apiParentCtryNames.add(prntCtryName);
				}
			ctryArray1=GenericUtility_NLApp.convertListIntoTwoDimArray(apiParentCtryNames);
			System.out.println("Parent Category list from API respose is ------------->" + apiParentCtryNames);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return ctryArray1;
	}

}
