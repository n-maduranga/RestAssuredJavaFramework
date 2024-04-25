package api.gorest.endpoints;
import static io.restassured.RestAssured.*;

import org.testng.ITestContext;

import api.gorest.payloads.UserPOJO;
import io.restassured.response.Response;



public class UserEndPoints {

	public static Response createUserRequest(UserPOJO payload,String BearerToken ) {
		
		Response res =given()
			.contentType("application/json")
			.headers("Authorization","Bearer "+BearerToken)
			.body(payload)
		.when()
			.post(Routes.postURL);
			//.jsonPath().get("id");//this will extract id from the response
		return res;
		
	}
	public static Response getUserRequest(String BearerToken,int id) {
		
				
		 Response response = given()
		 	.headers("Authorization","Bearer "+BearerToken)
		 	.pathParam("id", id)
		.when()
			.get("https://gorest.co.in/public/v2/users/{id}");
			return response;
		
	}
	
	
}
