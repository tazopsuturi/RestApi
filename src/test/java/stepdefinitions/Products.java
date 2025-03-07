package stepdefinitions;

import io.cucumber.java.en.And;
import org.json.simple.JSONObject;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.Assert;
import io.restassured.specification.RequestSpecification;

public class Products {
	
	public RequestSpecification request;
	public Response response;
	public int ResponseCode;
	public RequestSpecification httpRequest;
	public ResponseBody body;
	public JSONObject requestParams;
	
	
	@Given("I hit the url of get products api endpoint")
	public void i_hit_the_url_of_get_products_api_endpoint() {
		RestAssured.baseURI = "https://fakestoreapi.com/";
	}
	
	@When("I pass the url in the request")
	public void i_pass_the_url_in_the_request() {
		request = RestAssured.given();
		response = request.get("products");
	}
	
	@Then("I receive the response code as {int}")
	public void i_receive_the_response_code_as(Integer int1) {
		ResponseCode = response.getStatusCode();
		Assert.assertEquals(ResponseCode, 200);
	}
	
	@Then("I verify that the rate of first product is {}")
	public void i_verify_that_the_rate_of_first_product_is(String rate) {
		body = response.getBody();
		
		JsonPath jsonPath = response.jsonPath();
		
		String rateOfProduct = jsonPath.getJsonObject("rating[0].rate").toString();
		System.out.println(body);
		
		Assert.assertEquals(rate, rateOfProduct);
	}
	
	@Given("I hit the url of post products api endpoint")
	public void i_hit_the_url_of_post_products_api_endpoint() {
		RestAssured.baseURI = "https://fakestoreapi.com/";
		
		httpRequest = RestAssured.given();
		
		requestParams = new JSONObject();
		requestParams.put("title", "shoes");
		requestParams.put("price", 13.5);
		requestParams.put("description", "Shoes");
		requestParams.put("image", "https://i.pravatar.cc/");
		requestParams.put("category", "electronic");
	}
	
	@And("I pass the request body of product title {}")
	public void iPassTheRequestBodyOfProductTitleProductTitle(String productTitle) {
		httpRequest.body(requestParams.toJSONString());
		Response response = httpRequest.post("products");
		body = response.getBody();
		System.out.println(response.getStatusLine());
		System.out.println(body.asString());
	}
}
