package api.gorest.tests;

import static io.restassured.RestAssured.given;

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
	//public Logger logger;
	
	@BeforeClass
	public void setup() {
		//Creating payload - data will be create using faker class
		faker = new Faker();
		userpayload= new UserPOJO();
		userpayload.setEmail(faker.internet().emailAddress());
		userpayload.setName(faker.name().fullName());
		userpayload.setGender("Male");
		userpayload.setStatus("Inactive");

		
		//logs
		//logger = LogManager.getLogger(this.getClass()); 
	}
	
	@Test
	public void testCreateUser(ITestContext context) {
		Response response = UserEndPoints.createUserRequest(userpayload, BearerToken);
		
		//response.then().log().all();
		
		int id=response.then().extract().path("id");
		System.out.println("Generated ID is:"+id);
		//Set the id using setAttribute
		
		context.setAttribute("user_id", id); //Refer test level
	   	// set userid
		//context.setAttribute("user_id", id); Refer test level
		//context.getSuite().setAttribute("user_id", id);//suite level
		
	}
	
	@Test(priority=2)
	public void testGetUserById(ITestContext context) {
		
		int id = (int) context.getAttribute("user_id");
		Response response = UserEndPoints.getUserRequest(BearerToken,id);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
}
