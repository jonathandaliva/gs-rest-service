package hello;

import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;

//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.web.bind.annotation.RequestMapping;
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
	/* TODO cache in SQL? Would need to cache per point for each request...
	private final AtomicLong lastUpdate = new AtomicLong();
	private final AtomicBoolean updatingWeather = new AtomicBoolean();
	private final AtomicInteger TdyDesc = new AtomicInteger();
	private final AtomicInteger TdyHigh = new AtomicInteger(999);
	private final AtomicInteger TdyLow = new AtomicInteger(999);
	private final AtomicInteger TmwDesc = new AtomicInteger();
	private final AtomicInteger TmwHigh = new AtomicInteger(999);
	private final AtomicInteger TmwLow = new AtomicInteger(999);
	*/
	
	@RequestMapping("/forecast")
	public Greeting greeting(HttpServletRequest request ) throws ClientProtocolException, IOException {
		//call api, parse json, create simple one.
		//boolean callApiNow = true;
		Greeting greetingResponse = new Greeting(counter.incrementAndGet(), false);
		/* //Check if the lastUpdate was more than 7200000 ago and if the values are being updated.
		//	If it's not that old or it's already being updated, use the cached values.
		System.out.println(String.valueOf(System.currentTimeMillis() - lastUpdate.get()));
		if ( System.currentTimeMillis() - lastUpdate.get() > 7200000 ) {
			System.out.println("lastUpdate is older than 2 hours.");
			if ( !updatingWeather.get() ) {
				System.out.println("updatingWeather is false.");
				callApiNow = true;
			}
		}*/
		
		//@RequestParam(value="lat", defaultValue="38.803328") String lat, @RequestParam(value="lon", defaultValue="-77.039026") String lon, 
		String ip = request.getHeader("X-FORWARDED-FOR");
		if ( ip == null || ip == "") {
			ip = request.getRemoteAddr();
		} else {
			ip = ip.split(",")[0];
		}
		//call http://api.ipstack.com/134.201.250.155?access_key=26e8546e6586e706b4d3757e1a3d65bd&fields=latitude,longitude
		System.out.println("Request IP: " + ip);
		greetingResponse.setIP(ip);
		
		//Call the API
		//if ( callApiNow ) {
			try {
				System.out.println("Calling Geo IP API.");
				String[] coordinates = callGeoIpApi(ip);
				
				//updatingWeather.set(true);
				System.out.println("Calling Weather API.");
				greetingResponse = callWeatherApi(greetingResponse, coordinates[0], coordinates[1]);
				//updatingWeather.set(false);
				//lastUpdate.set(System.currentTimeMillis());
			} catch(Exception e) {
				System.out.println("API calls threw an exception. " + e.toString());
				//callApiNow = false;
				greetingResponse.setValidResponse(false);
			}
		//}
		
		/*Either the API was called within 2 hours or the API response was bad.  Set return values to cached values
		if( !callApiNow ) {
			System.out.println("Setting values from cache.");
			greetingResponse.setTdyDesc(getDescText(TdyDesc.get()));
			greetingResponse.setTdyHigh((float)(TdyHigh.get()/100));
			greetingResponse.setTdyLow((float)(TdyLow.get()/100));
			greetingResponse.setTmwDesc(getDescText(TmwDesc.get()));
			greetingResponse.setTmwHigh((float)(TmwHigh.get()/100));
			greetingResponse.setTmwLow((float)(TmwLow.get()/100));
			greetingResponse.setValidResponse(true);
		}*/
		return greetingResponse;
	}
	
	private String[] callGeoIpApi(String ip) throws Exception {

		HttpClient client = HttpClientBuilder.create().build();
		String url = "http://api.ipstack.com/"+ip+"?access_key=26e8546e6586e706b4d3757e1a3d65bd&fields=latitude,longitude";
		HttpGet httpget = new HttpGet(url);
		httpget.setHeader("User-Agent", "Simple Fucking Weather API");
		httpget.setHeader("Accept", "application/vnd.noaa.dwml+xml;version=1");
		
		System.out.println("executing request " + httpget.getURI());
		HttpResponse response = client.execute(httpget);
		final int statusCode = response.getStatusLine().getStatusCode();
		
		if (statusCode != HttpStatus.SC_OK) {
			System.out.println( "Error " + statusCode + " for URL " + url);
			throw new Exception("Api errored out.");
		} else {
			System.out.println("----------------------------------------");
			System.out.println(response.toString());
			System.out.println("----------------------------------------");
			
			HttpEntity getResponseEntity = response.getEntity();
			
			Gson gson = new Gson();
			
			Reader reader = new InputStreamReader(getResponseEntity.getContent());
			
			GeoIpResponse parsedresponse = gson.fromJson(reader, GeoIpResponse.class);
			if ( parsedresponse == null || parsedresponse.latitude == null || parsedresponse.latitude == "null") {
				System.out.println("There was no valid JSON response.");
				//throw new Exception("There was no valid JSON response.");
				return new String[] { "38.803328", "-77.039026" };
			} else {
				System.out.println("------------------DONE----------------------");
				return new String[] { parsedresponse.latitude , parsedresponse.longitude };
			}
			
		}
		
	}
	
	private Greeting callWeatherApi(Greeting greetingResponse, String lat, String lon) throws Exception {

		HttpClient client = HttpClientBuilder.create().build();
		String url = "https://api.weather.gov/points/"+lat+","+lon+"/forecast";
		HttpGet httpget = new HttpGet(url);
		httpget.setHeader("User-Agent", "Simple Fucking Weather API");
		httpget.setHeader("Accept", "application/vnd.noaa.dwml+xml;version=1");
		
		System.out.println("executing request " + httpget.getURI());
		HttpResponse response = client.execute(httpget);
		final int statusCode = response.getStatusLine().getStatusCode();
		
		if (statusCode != HttpStatus.SC_OK) {
			System.out.println( "Error " + statusCode + " for URL " + url);
			throw new Exception("Api errored out.");
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
				throw new Exception("There was no valid JSON response.");
			} else if ( parsedresponse.properties == null ) {
				System.out.println("There were no properties in the JSON.");
				throw new Exception("There were no properties in the JSON.");
			} else if ( parsedresponse.properties.periods == null ) {
				System.out.println("There were no forcast periods in the JSON.");
				throw new Exception("There were no forcast periods in the JSON.");
			} else {
				int index = 0;
				
				WeatherPeriods objPeriod = parsedresponse.properties.periods.get(index);
				index=index+1;
				greetingResponse.setTdyDesc(objPeriod.shortForecast);
				//TdyDesc.set(getDescCode(objPeriod.shortForecast));
				System.out.println("today Desc: " + String.valueOf(objPeriod.shortForecast));
				if(objPeriod.isDaytime) {
					System.out.println("today high: " + String.valueOf(objPeriod.temperature));
					greetingResponse.setTdyHigh(objPeriod.temperature);
					//TdyHigh.set((int)(objPeriod.temperature*100));
					objPeriod = parsedresponse.properties.periods.get(index);
					index=index+1;
				} else {
					greetingResponse.setTdyHigh(999);
				}
				System.out.println("today low: " + String.valueOf(objPeriod.temperature));
				greetingResponse.setTdyLow(objPeriod.temperature);
				//TdyLow.set((int)(objPeriod.temperature*100));
				objPeriod = parsedresponse.properties.periods.get(index);
				index=index+1;
				System.out.println("tomorrow Desc: " + String.valueOf(objPeriod.shortForecast));
				System.out.println("tomorrow high: " + String.valueOf(objPeriod.temperature));
				greetingResponse.setTmwDesc(objPeriod.shortForecast);
				//TmwDesc.set(getDescCode(objPeriod.shortForecast));
				greetingResponse.setTmwHigh(objPeriod.temperature);
				//TmwHigh.set((int)(objPeriod.temperature*100));
				objPeriod = parsedresponse.properties.periods.get(index);
				
				System.out.println("tomorrow low: " + String.valueOf(objPeriod.temperature));
				greetingResponse.setTmwLow(objPeriod.temperature);
				//TmwLow.set((int)(objPeriod.temperature*100));
				greetingResponse.setValidResponse(true);
			}
			System.out.println("------------------DONE----------------------");
			
		}
		return greetingResponse;
	}
	
	/*
	private String getDescText(int code) {
		if( code == 1 ) {
			return "Clear";
		} else if( code == 2 ) {
			return "Mostly Cloudy";
		} else if( code == 3 ) {
			return "Clouds";
		} else if( code == 4 ) {
			return "Overcast";
		} else if( code == 5 ) {
			return "Snow";
		} else if( code == 6 ) {
			return "Ice";
		} else if( code == 7 ) {
			return "Thunderstorm";
		} else if( code == 8 ) {
			return "Rain";
		} else if( code == 9 ) {
			return "Tornado";
		} else if( code == 10 ) {
			return "Wind";
		} else if( code == 11 ) {
			return "Hot";
		} else if( code == 12 ) {
			return "Cold";
		} else {
			return "Clear";
		}
	}
	
	private int getDescCode(String description) {
		if ( description.contains("Fair") || description.contains("fair") || description.contains("Clear") || description.contains("clear") || description.contains("Sunny") ) {
			return 1;
		} else if ( description.contains("Mostly Cloudy") ) {
			return 2;
		} else if ( description.contains("Cloud") ) {
			return 3;
		} else if ( description.contains("Overcast") ) {
			return 4;
		} else if ( description.contains("Snow") ) {
			return 5;
		//} else if ( description.contains("Ice") || description.contains("Freez") || description.contains("Blizzard") ) {
		} else if ( description.contains("Ice") ) {
			return 6;
		} else if ( description.contains("Thunderstorm") ) {
			return 7;
		} else if ( description.contains("Rain") || description.contains("Drizzl") || description.contains("Shower") ) {
			return 8;
		} else if ( description.contains("Tornado") || description.contains("Funnel") || description.contains("Hurricane") || description.contains("Tropical Storm") || description.contains("Dust") || description.contains("Sand") || description.contains("Smoke") ) {
			return 9;
		} else if ( description.contains("Wind") || description.contains("Breez") ) {
			return 10;
		} else if ( description.contains("Hot") ) {
			return 11;
		} else if ( description.contains("Cold") ) {
			return 12;
		} else {
			return 1;
		}
	}
	*/
}
