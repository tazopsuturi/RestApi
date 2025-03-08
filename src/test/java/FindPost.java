import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class FindPost {
	Response response;
	
	@Test
	public void testGetPosts() {
		response = RestAssured.get("https://jsonplaceholder.typicode.com/posts");
		
		JsonPath jsonPath = response.jsonPath();
		
		List<Map<String, Object>> posts = jsonPath.getList("$");
		
		Map<String, Object> post99 = posts.stream()
				.filter(post -> ((Number) post.get("id")).intValue() == 99)
				.findFirst().orElse(null);
		
		if (post99 != null) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String formattedJson = gson.toJson(post99);
			System.out.println(formattedJson);
		}
	}
}
