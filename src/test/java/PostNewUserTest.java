import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PostNewUserTest {
	public RequestSpecification httpRequest;
	public JSONObject requestParams;
	public ResponseBody body;
	
	@Test
	public void test() {
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
		httpRequest = RestAssured.given();
		
		requestParams = new JSONObject();
		
		requestParams.put("title", "Test");
		requestParams.put("body", "This is a test post");
		requestParams.put("userId", "1");
		requestParams.put("id", "101");
		
		httpRequest.body(requestParams.toJSONString());
		
		Response response = httpRequest.post("/posts");
		body = response.getBody();
		System.out.println(response.getStatusLine());
		System.out.println(body.asString());
		
		Assert.assertEquals(201, response.getStatusCode());
		
	}
}
