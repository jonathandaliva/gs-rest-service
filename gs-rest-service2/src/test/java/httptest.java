import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.google.gson.Gson;

import hello.Greeting;
import hello.WeatherPeriods;
import hello.WeatherResponse;

public class httptest {

	private final AtomicLong counter = new AtomicLong();
	@Test
	public void test() throws ClientProtocolException, IOException {
		//38.803328, -77.039026
		//https://api.weather.gov/points/38.803328,-77.039026/forecast
		//String data="";
		
		//call api, parse json, create simple one.
				
		//HttpClient client = HttpClientBuilder.create().build();
		
		String url = "https://api.weather.gov/points/38.803328,-77.039026/forecast";
		
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
					
		try {
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("User-Agent", "Simple Fucking Weather API");
			httpget.setHeader("Accept", "application/vnd.noaa.dwml+xml;version=1");
					
			System.out.println("Executing request " + httpget.getRequestLine());

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				@Override
				public String handleResponse(
						final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}

			};
			String responseBody = httpclient.execute(httpget, responseHandler);
			System.out.println("----------------------------------------");
			System.out.println(responseBody);
		} finally {
			httpclient.close();
		}
				
				
		/*		
				HttpGet httpget = new HttpGet(url);
				//HttpPost httpPost = new HttpPost(url);
				
				System.out.println("executing request " + httpget.getURI());
				HttpResponse response = client.execute(httpget);
				//HttpResponse response = client.execute(httpPost);
				final int statusCode = response.getStatusLine().getStatusCode();
				
				Greeting greetingResponse = new Greeting(counter.incrementAndGet(), false);
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
					if ( parsedresponse == null ) {
						System.out.println("There was no valid JSON response.");
					} else if ( parsedresponse.properties == null ) {
						System.out.println("There were no properties in the JSON.");
					} else if ( parsedresponse.properties.periods == null ) {
						System.out.println("There were no forcast periods in the JSON.");
					} else {
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
						greetingResponse.setValidResponse(true);
					}
					System.out.println("------------------DONE----------------------");
					
				}
			*/	
	}

}
