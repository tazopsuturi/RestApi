
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OpenWeather {
	private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/forecast";
	private static final String CITY = "London";
	
	public static String getApiKey() {
		Properties properties = new Properties();
		try (FileInputStream input = new FileInputStream("src/main/resources/config.properties")) {
			properties.load(input);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return properties.getProperty("api_key");
	}
	
	public static void main(String[] args) {
		
		String API_KEY = getApiKey();
		
		Response response = RestAssured.given()
				.queryParam("q", CITY)
				.queryParam("appid", API_KEY)
				.queryParam("units", "metric")
				.queryParam("cnt", "40")
				.when()
				.get(BASE_URL);
		
		// Parse the response JSON
		JSONObject jsonResponse = new JSONObject(response.getBody().asString());
		JSONArray list = jsonResponse.getJSONArray("list");
		
		List<Double> dailyTemperatures = new ArrayList<>();
		
		for (int i = 0; i < list.length(); i++) {
			JSONObject weatherData = list.getJSONObject(i);
			long dt = weatherData.getLong("dt");
			JSONObject main = weatherData.getJSONObject("main");
			double temp = main.getDouble("temp");
			
			int dayIndex = i / 8;
			
			if (dayIndex >= dailyTemperatures.size()) {
				dailyTemperatures.add(temp);
			} else {
				dailyTemperatures.set(dayIndex, dailyTemperatures.get(dayIndex) + temp);
			}
		}
		
		double hottestTemperature = Double.MIN_VALUE;
		double coldestTemperature = Double.MAX_VALUE;
		int hottestDay = -1;
		int coldestDay = -1;
		
		for (int i = 0; i < dailyTemperatures.size(); i++) {
			double avgTemp = dailyTemperatures.get(i) / 8.0;
			if (avgTemp > hottestTemperature) {
				hottestTemperature = avgTemp;
				hottestDay = i;
			}
			if (avgTemp < coldestTemperature) {
				coldestTemperature = avgTemp;
				coldestDay = i;
			}
		}
		
		String hottestDayName = getDayOfWeek(hottestDay);
		String coldestDayName = getDayOfWeek(coldestDay);
		
		System.out.println("The hottest day is " + hottestDayName + " with temperature: " + hottestTemperature + "°C");
		System.out.println("The coldest day is " + coldestDayName + " with temperature: " + coldestTemperature + "°C");
	}
	
	private static String getDayOfWeek(int dayIndex) {
		LocalDate currentDate = LocalDate.now();
		LocalDate targetDate = currentDate.plusDays(dayIndex);
		return targetDate.getDayOfWeek().toString();
	}
}


