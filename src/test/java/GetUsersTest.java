import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;
import org.testng.Assert;

public class GetUsersTest {
	public Response response;
	public ResponseBody body;
	public RequestSpecification httpRequest;
	
	@Test
	public void test() {
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
		
		httpRequest = RestAssured.given();
		response = httpRequest.get("/users/5");
		body = response.getBody();
		
		Assert.assertEquals(200, response.getStatusCode());
	}
}
