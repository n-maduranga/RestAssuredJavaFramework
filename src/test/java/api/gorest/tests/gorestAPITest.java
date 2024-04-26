package api.gorest.tests;

import static io.restassured.RestAssured.given;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import api.gorest.endpoints.UserEndPoints;
import api.gorest.payloads.UserPOJO;
import io.restassured.response.Response;

public class gorestAPITest {

	Faker faker;
	UserPOJO userpayload;
	String BearerToken = "3537b9548057935bbca957eaede4f5317e83c47af7cd1d2079aa79a3ca2d70c0";
	// public Logger logger;

	@BeforeClass
	public void setup() {
		// Creating payload - data will be create using faker class
		faker = new Faker();
		userpayload = new UserPOJO();
		userpayload.setEmail(faker.internet().emailAddress());
		userpayload.setName(faker.name().fullName() );
		userpayload.setGender("Male");
		userpayload.setStatus("Inactive");

		// logs
		// logger = LogManager.getLogger(this.getClass());
	}

	@Test
	public void testCreateUser(ITestContext context) {
		Response response = UserEndPoints.createUserRequest(userpayload, BearerToken);

		// response.then().log().all();

		int id = response.then().extract().path("id");
		String name = response.then().extract().path("name");

		System.out.println("Generated ID is:" + id);
		System.out.println("Generated Name is:" + name);
		// Set the id using setAttribute

		// Refer test level
		context.setAttribute("user_id", id);
		context.setAttribute("user_name", name);
		// set userid
		// context.setAttribute("user_id", id); Refer test level
		// context.getSuite().setAttribute("user_id", id);//suite level

	}

	@Test(priority = 2)
	public void testGetUserById(ITestContext context) {

		Integer id = (Integer) context.getAttribute("user_id");
		Response response = UserEndPoints.getUserRequest(BearerToken, id);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		String ActualName = response.then().extract().path("name");
		String ExpectedName = (String) context.getAttribute("user_name");
		Assert.assertEquals(ActualName, ExpectedName);

	}

	@Test(priority = 3)
	public void testUpdateUser(ITestContext context) {
		Integer id = (Integer) context.getAttribute("user_id");
		// int id = (int) context.getSuite().getAttribute("user_id");//suite level
		Faker faker = new Faker();
		
		userpayload.setEmail(faker.internet().emailAddress());
		userpayload.setName(faker.name().fullName()+ "_" + new Random().nextInt(1000));
		userpayload.setGender("Male");
		userpayload.setStatus("Active");

		
		Response response = UserEndPoints.updateUserRequest(userpayload, BearerToken, id);
		// Values after update
		response.then().log().all();
		
		// Verify that the name has been updated
	    String nameAfterUpdated = response.then().extract().path("name");
	    System.out.println("Name after updated " + nameAfterUpdated);
	}
	
	@Test(priority = 4)
	public void testDeleteUser(ITestContext context) {
		Integer id = (Integer) context.getAttribute("user_id");
		Response response = UserEndPoints.DeleteUser(BearerToken, id);
		response.then().statusCode(204);
		
	}
}
