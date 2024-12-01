package com.example.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApiTest 
{
	 @Test
	    public void testGetRequest() 
	 	{
			/*
			 * This test is a temporary solution to verify the status code of a GET request.
			 * In the future, we can enhance this test to validate the response body and
			 * other aspects.
			 */
		 Response response = RestAssured.get("https://jsonplaceholder.typicode.com/posts/1"); 
		 Assert.assertEquals(response.getStatusCode(), 200); 
		 	 // Temporary solution: Just verifying the status code for now 
	    }
}
