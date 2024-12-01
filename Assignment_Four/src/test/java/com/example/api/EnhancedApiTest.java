package com.example.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EnhancedApiTest 
{
	@Test
	public void testGetRequest() 
	{
		Response response = RestAssured.get("https://jsonplaceholder.typicode.com/posts/1");
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().asString().contains("userId"));
	}
	
	@Test
	public void testGetNonExistentResource() 
	{
	    Response response = RestAssured.get("https://jsonplaceholder.typicode.com/posts/9999");
	    Assert.assertEquals(response.getStatusCode(), 404, "Expected status code to be 404 for non-existent resource");
	}
	
	@Test
    public void testResponseTime() 
    {
        Response response = RestAssured.get("https://jsonplaceholder.typicode.com/posts/1");
        Assert.assertTrue(response.getTime() < 2000, "Response time is greater than expected (2000ms)");
    }
	
	@Test
	public void testJsonStructure() 
	{
	    Response response = RestAssured.get("https://jsonplaceholder.typicode.com/posts/1");
	    String responseBody = response.getBody().asString();
	    Assert.assertTrue(responseBody.contains("userId"), "Response does not contain userId");
	    Assert.assertTrue(responseBody.contains("id"), "Response does not contain id");
	    Assert.assertTrue(responseBody.contains("title"), "Response does not contain title");
	    Assert.assertTrue(responseBody.contains("body"), "Response does not contain body");
	}
	
	@Test
	public void testPostRequest() 
	{
	    String jsonBody = "{ \"title\": \"foo\", \"body\": \"bar\", \"userId\": 1 }";
	    Response response = RestAssured.given()
	                                   .header("Content-Type", "application/json")
	                                   .body(jsonBody)
	                                   .post("https://jsonplaceholder.typicode.com/posts");
	    
	    Assert.assertEquals(response.getStatusCode(), 201, "Expected status code to be 201 for successful POST request");
	    Assert.assertTrue(response.getBody().asString().contains("id"), "Response body does not contain 'id'");
	}
	
	@Test
	public void testPutRequest() 
	{
	    String jsonBody = "{ \"id\": 1, \"title\": \"updated title\", \"body\": \"updated body\", \"userId\": 1 }";
	    
	    Response response = RestAssured.given()
	                                   .header("Content-Type", "application/json")
	                                   .body(jsonBody)
	                                   .put("https://jsonplaceholder.typicode.com/posts/1");
	    
	    Assert.assertEquals(response.getStatusCode(), 200, "Expected status code to be 200 for successful PUT request");
	    Assert.assertTrue(response.getBody().asString().contains("\"title\": \"updated title\""), "The title was not updated correctly");
	}
	
	@Test
	public void testDeleteRequest() 
	{
	    Response response = RestAssured.delete("https://jsonplaceholder.typicode.com/posts/1");
	    Assert.assertEquals(response.getStatusCode(), 200, "Expected status code to be 200 for successful DELETE request");
	}
	
	@Test
	public void testContentType() 
	{
	    Response response = RestAssured.get("https://jsonplaceholder.typicode.com/posts/1");
	    Assert.assertEquals(response.getHeader("Content-Type"), "application/json; charset=utf-8", "Expected content type is application/json");
	}
}
