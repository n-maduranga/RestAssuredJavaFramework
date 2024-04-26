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
			.get(Routes.getURL);
			return response;
		
	}
	
	public static Response updateUserRequest(UserPOJO payloadUp,String BearerToken,int id) {
		
	Response response=	given()
			
			.headers("Authorization","Bearer "+BearerToken)
			.contentType("application/json")
			.pathParam("id",id)
			.body(payloadUp)
		
		
	.when()
		.put(Routes.updatetURL);
		return response;
		
	}
	
	public static Response DeleteUser(String BearerToken,int id) {
		
		Response response = given()
			.headers("Authorization","Bearer "+BearerToken)
			.pathParam("id", id)
		.when()
			.delete(Routes.deleteURL);
		return response;
	}
	
	
}
