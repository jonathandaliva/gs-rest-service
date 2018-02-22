package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

@RestController
public class GreetingController {

	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value="lat", defaultValue="38.803328") String lat, @RequestParam(value="lon", defaultValue="-77.039026") String lon ) throws ClientProtocolException, IOException {
		//38.803328, -77.039026
		//https://api.weather.gov/points/38.803328,-77.039026/forecast
		String data="";
		
		//call api, parse json, create simple one.
		
		HttpClient client = HttpClientBuilder.create().build();
		String url = "https://api.weather.gov/points/"+lat+","+lon+"/forecast";
		HttpGet httpget = new HttpGet(url);
		
		System.out.println("executing request " + httpget.getURI());
		HttpResponse response = client.execute(httpget);
		final int statusCode = response.getStatusLine().getStatusCode();
		
		Greeting greetingResponse = new Greeting(counter.incrementAndGet());
		if (statusCode != HttpStatus.SC_OK) {
			System.out.println( "Error " + statusCode + " for URL " + url);
		} else {
			System.out.println("----------------------------------------");
			System.out.println(response.toString());
			System.out.println("----------------------------------------");
			
			HttpEntity getResponseEntity = response.getEntity();
			
			Gson gson = new Gson();
			
			Reader reader = new InputStreamReader(getResponseEntity.getContent());
			
			WeatherResponse parsedresponse = gson.fromJson(reader, WeatherResponse.class);
			WeatherPeriods objPeriod = parsedresponse.properties.periods.get(0);
			System.out.println("today Desc: " + String.valueOf(objPeriod.shortForecast));
			System.out.println("today high: " + String.valueOf(objPeriod.temperature));
			greetingResponse.setTdyDesc(objPeriod.shortForecast);
			greetingResponse.setTdyHigh(objPeriod.temperature);
			
			objPeriod = parsedresponse.properties.periods.get(1);
			System.out.println("today low: " + String.valueOf(objPeriod.temperature));
			greetingResponse.setTdyLow(objPeriod.temperature);
			
			objPeriod = parsedresponse.properties.periods.get(2);
			System.out.println("tomorrow Desc: " + String.valueOf(objPeriod.shortForecast));
			System.out.println("tomorrow high: " + String.valueOf(objPeriod.temperature));
			greetingResponse.setTmwDesc(objPeriod.shortForecast);
			greetingResponse.setTmwHigh(objPeriod.temperature);
			
			objPeriod = parsedresponse.properties.periods.get(3);
			System.out.println("tomorrow low: " + String.valueOf(objPeriod.temperature));
			greetingResponse.setTmwLow(objPeriod.temperature);
			
			System.out.println("------------------DONE----------------------");
			
		}
		
		return greetingResponse;
	}
}
