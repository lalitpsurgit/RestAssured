package E2EFlow;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
public class Scenario {
	JSONObject obj;
	JSONObject orderDetails;
	String baseUrl="https://simple-tool-rental-api.glitch.me";
	AccessToken token = null;
	@BeforeTest 
	public void beforeTest() throws IOException, ParseException {
		JSONParser parser = new JSONParser();

		Reader reader = new FileReader(".//test2.json");

		obj = (JSONObject) parser.parse(reader);
		//System.out.println("This will execute before the Test1");
		
		Reader orderDetailsreader = new FileReader(".//order.json");

		orderDetails = (JSONObject) parser.parse(orderDetailsreader);
		//System.out.println("This will execute before the Test1");
		
	}
	
	@Test
	public void postRequest() {
		token =  given().contentType("application/json; charset=UTF-16").body(obj).post(baseUrl+"/api-clients").as(AccessToken.class);
		
		Map <String, String> headers= new HashMap<String, String>();
		
		headers.put("Authorization", token.getToken());
				
		given().contentType("application/json; charset=UTF-16").body(orderDetails).headers(headers).post(baseUrl+"/orders").then().statusCode(201);

	}
	
}
