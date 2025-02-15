package api;

import org.testng.Assert;
import org.testng.annotations.Test;
import base.ApiTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CreateUser extends ApiTestBase {

    @Test
    public void testCreateUser() {
        String requestBody = "{ \"name\": \"Marina\", \"job\": \"QA Engineer\" }";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .statusCode(201)  // Validate HTTP status 201 (Created)
                .extract()
                .response();

        // Extract user details from response
        String name = response.jsonPath().getString("name");
        String job = response.jsonPath().getString("job");

        // Assertions to validate response data
        Assert.assertEquals(name, "Marina", "Name does not match expected value.");
        Assert.assertEquals(job, "QA Engineer", "Job title does not match expected value.");

        // Print response body
        System.out.println("Response: " + response.getBody().asString());
    }

    @Test
    public void testCreateUserInvalidData() {
        String invalidRequestBody = "{ \"invalidField\": \"value\" }";  // Incorrect JSON structure

        Response response = given()
                .contentType(ContentType.JSON)
                .body(invalidRequestBody)
                .when()
                .post("/users");

        // Log the response for debugging
        response.then().log().all();

        int actualStatusCode = response.getStatusCode();
        System.out.println("Response Status Code: " + actualStatusCode);
        System.out.println("Response Body: " + response.getBody().asString());

        // Handle unexpected status codes gracefully
        if (actualStatusCode == 201) {
            System.out.println("Warning: API is accepting invalid data.");
            Assert.fail("API should return 400 Bad Request but returned 201 Created.");
        } else if (actualStatusCode == 400) {
            Assert.assertTrue(response.getBody().asString().contains("error"), 
                    "Error message not found in response!");
        } else {
            Assert.fail("Unexpected response status code: " + actualStatusCode);
        }
    }

}
