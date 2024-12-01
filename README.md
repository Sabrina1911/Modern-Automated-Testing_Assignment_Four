# Project Title : Assignment Four

## Project Description:
The assignment focuses on creating a Maven Project using TestNG and RestAssured for API testing. The project is divided into 5 parts.

### Key Goals:

  **Implementing temporary solutions**
  **Reducing technical debt**
  **Isolating problematic code without refactoring**

## Part 1: Setting Up the Project
1.  **Open Eclipse - Create a new MAVEN Project
    **GroupID** - Assignment & **ArtifactID** - Assignment_Four.
2.  **Add Dependencies**  for TestNG and RestAssured into the pom.xml file:
     ```xml
       <dependencies>
            <dependency>
                <groupId>org.testng</groupId>
                <artifactId>testng</artifactId>
                <version>7.4.0</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>io.rest-assured</groupId>
                <artifactId>rest-assured</artifactId>
                <version>4.4.0</version>
            </dependency>
        </dependencies>
  3. **Configure Testng** - Create a testng.xml file in the root of your project with the following content:
            <!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
            <suite name="API Testing Suite">
                <test name="API Tests">
                    <classes>
                        <class name="com.example.api.ApiTest"/>
                    </classes>
                </test>
            </suite> 

# Part 2: Implementing Temporary Solutions
    * Create a Temporary API Test - To quickly verify the API functionality we develop a temporary solution.
## Test Case: GET Request to Verify Status Code
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
                    Response response = RestAssured.get("https://jsonplaceholder.typicode.com/posts/1");
                    Assert.assertEquals(response.getStatusCode(), 200); // Temporary solution: Just verifying the status code for now
                }
            }
### Adding comment and improving the code for GET Request
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
                    Assert.assertEquals(response.getStatusCode(), 200); // Temporary solution: Just verifying the status code for now
                }
            }
            
# Part 3: Reducing Technical Debt
## Identify and Plan
 *Plan to reduce technical debt by:
    1. Adding assertions to validate the response body.
    2. Handling different response scenarios (e.g., non-200 status codes).
    3. Implementing data-driven testing for multiple endpoints.
## Plan to reduce technical debt by adding more robust assertions and handling different response scenarios.
1. Enhance the testGetRequest method: Add assertions to validate the response body:

            @Test
            public void testGetRequest() 
            {
                Response response = RestAssured.get("https://jsonplaceholder.typicode.com/posts/1");
                Assert.assertEquals(response.getStatusCode(), 200);
                Assert.assertNotNull(response.getBody());
                Assert.assertTrue(response.getBody().asString().contains("userId"));
            }
2. Add More Test Scenarios:
### Test to Check the Status Code for a Non-Existent Resource (404)
                This test checks if the API returns the correct status code when requesting a resource that does not exist.
                
            @Test
            public void testGetNonExistentResource() 
           {
                Response response = RestAssured.get("https://jsonplaceholder.typicode.com/posts/9999");
                Assert.assertEquals(response.getStatusCode(), 404, "Expected status code to be 404 for non-existent resource");
            }
            
            # Test to Check the Response Time:
                This test checks if the response time is within an acceptable limit. For example, we can assert that the response time should be less than 2 seconds.

            @Test
            public void testResponseTime() 
            {
                Response response = RestAssured.get("https://jsonplaceholder.typicode.com/posts/1");
                Assert.assertTrue(response.getTime() < 2000, "Response time is greater than expected (2000ms)");
            }
            
            #  Test to Validate JSON Structure (Keys):
This test checks if the response contains all the expected keys (e.g., userId, id, title, body).

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

            # Test to Verify a Successful POST Request:
            This test case demonstrates how you can perform a POST request and verify the response.
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

            # Test to Verify PUT Request for Updating Data:
This test case checks how you would perform a PUT request to update an existing resource and verify the response.
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

            # Test to Verify DELETE Request:
This test case checks the DELETE request and ensures the status code is correct (typically 200 or 204 for a successful deletion).
            @Test
            public void testDeleteRequest() 
            {
                Response response = RestAssured.delete("https://jsonplaceholder.typicode.com/posts/1");
                Assert.assertEquals(response.getStatusCode(), 200, "Expected status code to be 200 for successful DELETE request");
            }

            # Test to Validate Content-Type Header:
This test verifies that the response contains the correct Content-Type header (e.g., application/json).
            @Test
	        public void testContentType() 
	        {
	            Response response = RestAssured.get("https://jsonplaceholder.typicode.com/posts/1");
	            Assert.assertEquals(response.getHeader("Content-Type"), "application/json; charset=utf-8", "Expected content type is application/json");
	        }
# Running the Tests
1. Run the code with TestNG
2. Run the code with Maven
3. Run the code in the terminal

# Troubleshooting
If you encounter issues such as "unable to connect to the API endpoint," ensure your network settings allow external HTTP requests or verify that the API endpoint is not down.

