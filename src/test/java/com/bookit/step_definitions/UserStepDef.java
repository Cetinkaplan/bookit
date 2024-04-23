package com.bookit.step_definitions;

import com.bookit.utilities.BookitUtils;
import com.bookit.utilities.ConfigurationReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserStepDef {

    String token;
    Response response;
    String emailGlobal;

    @Given("I logged Bookit api as a {string}")
    public void i_logged_bookit_api_as_a(String role) {
        token = BookitUtils.generateTokenByRole(role);
        System.out.println("token = " + token);

        Map<String, String> credentialsMap = BookitUtils.returnCredentials(role);

        emailGlobal = credentialsMap.get("email");


    }

    @When("I sent get request to {string} endpoint")
    public void i_sent_get_request_to_endpoint(String endpoint) {
        response = given().accept(ContentType.JSON)
                .header("Authorization", token)
                .when().get(ConfigurationReader.getProperty("base_url") + endpoint);
    }
    @Then("status code should be {int}")
    public void status_code_should_be(int expectedStatusCode) {
        System.out.println("response.statusCode() = " + response.statusCode());
        //verify status code
        Assert.assertEquals(expectedStatusCode,response.statusCode());

    }

    @Then("content type is {string}")
    public void content_type_is(String expectedContentType) {
        System.out.println("response.contentType() = " + response.contentType());
        //verify content type
        Assert.assertEquals(expectedContentType,response.contentType());
    }
    @Then("role is {string}")
    public void role_is(String expectedRole) {
        response.prettyPrint();
        String actualRole = response.path("role");

        Assert.assertEquals(expectedRole,actualRole);
    }

}
