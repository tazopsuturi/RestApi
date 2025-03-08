import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;

public class GetEmptyPostTest {
	public Response response;
	public RequestSpecification request;
	
	@Test
	public void test() {
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
		
		request = RestAssured.given();
		response = request.get("/posts/150");
		
		Assert.assertEquals("Expected status code 404", 404, response.getStatusCode());
		
		Assert.assertEquals("Expected response body to be an empty JSON object", "{}", response.asString());
		
		if (response.asString().equals("{}")) {
			System.out.println("The response body is an empty JSON object.");
		} else {
			System.out.println("The response body is not an empty JSON object.");
		}
	}
}
