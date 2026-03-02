package test.java.stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.json.JSONObject;

public class EmployeeApiSteps {
    private Response response;
    private RequestSpecification request;

    @Given("I set the API base URL to {string}")
    public void setBaseUrl(String url) {
        RestAssured.baseURI = url;
        request = RestAssured.given().header("Content-Type", "application/json");
    }

    @When("I send a POST request to {string} with name {string} and email {string}")
    public void sendPostRequest(String endpoint, String name, String email) {
        // Build JSON according to Swagger Schema
        JSONObject body = new JSONObject();
        body.put("firstName", name);
        body.put("lastName", "Automation");
        body.put("email", email);
        body.put("dob", "1995-01-01");
        body.put("id", 0);

        response = request.body(body.toString()).post(endpoint);
    }

    @When("I send a GET request to {string}")
    public void sendGetRequest(String endpoint) {
        response = RestAssured.get(endpoint);
    }

    @Then("the response status code should be {int}")
    public void verifyStatusCode(int expectedCode) {
        Assert.assertEquals("Status code mismatch!", expectedCode, response.getStatusCode());
    }

    @Then("the response body should contain {string}")
    public void verifyBodyContent(String expectedContent) {
        String actualBody = response.getBody().asString();
        Assert.assertTrue("Body does not contain: " + expectedContent, actualBody.contains(expectedContent));
    }
}