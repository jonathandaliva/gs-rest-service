import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class test {
    public static void main(String[] args) throws ClientProtocolException, IOException {
    	System.setProperty("https.proxyHost", "10.100.0.18");
    	System.setProperty("https.proxyPort", "9090");
    	HttpClient client = HttpClientBuilder.create().build();
    	//System.setProperty("java.net.useSystemProxies", "true");
    		//https://api.weather.gov/points/39.0693,-94.6716/forecast
    		String url = "https://api.darksky.net/forecast/dec84d9b5c99a82a25e386b2257cf9b0/38.7609,-77.1852?exclude=currently,minutely,hourly,alerts,flags";
    		//String url = "http://localhost:8080/greeting";
	    	HttpGet httpget = new HttpGet(url);
	    	System.out.println("executing request " + httpget.getURI());

	        // Body contains your json string
	        //HttpResponse response = client.execute(httpget);
	        
	        //final int statusCode = response.getStatusLine().getStatusCode();
	    	final int statusCode = HttpStatus.SC_OK;
	        System.out.println("reponse code " + String.valueOf(statusCode));
	        if (statusCode != HttpStatus.SC_OK) {

	         	 System.out.println( "Error " + statusCode + " for URL " + url);

	            //return null;

	        } else {

		        System.out.println("----------------------------------------");
		        //System.out.println(response.toString());
		        //System.out.println("----------------------------------------");
		        //HttpEntity getResponseEntity = response.getEntity();
		        
		        String response = "{\"@context\": [\"https://raw.githubusercontent.com/geojson/geojson-ld/master/contexts/geojson-base.jsonld\",{\"wx\": \"https://api.weather.gov/ontology#\",\"geo\": \"http://www.opengis.net/ont/geosparql#\",\"unit\": \"http://codes.wmo.int/common/unit/\",\"@vocab\": \"https://api.weather.gov/ontology#\"}],\"type\": \"Feature\",\"geometry\": {\"type\": \"GeometryCollection\",\"geometries\": [{\"type\": \"Point\",\"coordinates\": [-77.039803652466,38.80053440889]},{\"type\": \"Polygon\",\"coordinates\": [[[-77.052018576115,38.81297938125],[-77.055771397329,38.791014509956],[-77.027591606174,38.788087871404],[-77.02383303017,38.810052427942],[-77.052018576115,38.81297938125]]]}]},\"properties\": {\"updated\": \"2018-02-20T18:18:08+00:00\",\"units\": \"us\",\"forecastGenerator\": \"BaselineForecastGenerator\",\"generatedAt\": \"2018-02-20T20:03:00+00:00\",\"updateTime\": \"2018-02-20T18:18:08+00:00\",\"validTimes\": \"2018-02-20T12:00:00+00:00/P7DT13H\",\"elevation\": {\"value\": 0.9144,\"unitCode\": \"unit:m\"},\"periods\": [{\"number\": 1,\"name\": \"This Afternoon\",\"startTime\": \"2018-02-20T15:00:00-05:00\",\"endTime\": \"2018-02-20T18:00:00-05:00\",\"isDaytime\": true,\"temperature\": 75,\"temperatureUnit\": \"F\",\"temperatureTrend\": null,\"windSpeed\": \"16 mph\",\"windDirection\": \"S\",\"icon\": \"https://api.weather.gov/icons/land/day/few?size=medium\",\"shortForecast\": \"Sunny\",\"detailedForecast\": \"Sunny, with a high near 75. South wind around 16 mph, with gusts as high as 21 mph.\"},{\"number\": 2,\"name\": \"Tonight\",\"startTime\": \"2018-02-20T18:00:00-05:00\",\"endTime\": \"2018-02-21T06:00:00-05:00\",\"isDaytime\": false,\"temperature\": 58,\"temperatureUnit\": \"F\",\"temperatureTrend\": null,\"windSpeed\": \"8 to 16 mph\",\"windDirection\": \"S\",\"icon\": \"https://api.weather.gov/icons/land/night/sct/rain?size=medium\",\"shortForecast\": \"Partly Cloudy then Patchy Drizzle\",\"detailedForecast\": \"Patchy fog between 1am and 4am, then patchy drizzle and patchy fog. Partly cloudy, with a low around 58. South wind 8 to 16 mph, with gusts as high as 21 mph.\"},{\"number\": 3,\"name\": \"Wednesday\",\"startTime\": \"2018-02-21T06:00:00-05:00\",\"endTime\": \"2018-02-21T18:00:00-05:00\",\"isDaytime\": true,\"temperature\": 78,\"temperatureUnit\": \"F\",\"temperatureTrend\": null,\"windSpeed\": \"12 to 16 mph\",\"windDirection\": \"SW\",\"icon\": \"https://api.weather.gov/icons/land/day/rain/bkn?size=medium\",\"shortForecast\": \"Patchy Drizzle then Partly Sunny\",\"detailedForecast\": \"Patchy drizzle and patchy fog before 10am. Partly sunny, with a high near 78. Southwest wind 12 to 16 mph, with gusts as high as 21 mph.\"},{\"number\": 4,\"name\": \"Wednesday Night\",\"startTime\": \"2018-02-21T18:00:00-05:00\",\"endTime\": \"2018-02-22T06:00:00-05:00\",\"isDaytime\": false,\"temperature\": 52,\"temperatureUnit\": \"F\",\"temperatureTrend\": null,\"windSpeed\": \"3 to 16 mph\",\"windDirection\": \"W\",\"icon\": \"https://api.weather.gov/icons/land/night/rain,40?size=medium\",\"shortForecast\": \"Chance Light Rain\",\"detailedForecast\": \"A chance of rain after 7pm. Mostly cloudy, with a low around 52. West wind 3 to 16 mph, with gusts as high as 21 mph. Chance of precipitation is 40%.\"},{\"number\": 5,\"name\": \"Thursday\",\"startTime\": \"2018-02-22T06:00:00-05:00\",\"endTime\": \"2018-02-22T18:00:00-05:00\",\"isDaytime\": true,\"temperature\": 54,\"temperatureUnit\": \"F\",\"temperatureTrend\": null,\"windSpeed\": \"9 mph\",\"windDirection\": \"NE\",\"icon\": \"https://api.weather.gov/icons/land/day/rain,70?size=medium\",\"shortForecast\": \"Light Rain Likely\",\"detailedForecast\": \"Rain likely. Cloudy, with a high near 54. Northeast wind around 9 mph. Chance of precipitation is 70%.\"},{\"number\": 6,\"name\": \"Thursday Night\",\"startTime\": \"2018-02-22T18:00:00-05:00\",\"endTime\": \"2018-02-23T06:00:00-05:00\",\"isDaytime\": false,\"temperature\": 39,\"temperatureUnit\": \"F\",\"temperatureTrend\": null,\"windSpeed\": \"7 mph\",\"windDirection\": \"NE\",\"icon\": \"https://api.weather.gov/icons/land/night/rain,70/rain,60?size=medium\",\"shortForecast\": \"Light Rain Likely\",\"detailedForecast\": \"Rain likely. Cloudy, with a low around 39. Chance of precipitation is 70%.\"},{\"number\": 7,\"name\": \"Friday\",\"startTime\": \"2018-02-23T06:00:00-05:00\",\"endTime\": \"2018-02-23T18:00:00-05:00\",\"isDaytime\": true,\"temperature\": 53,\"temperatureUnit\": \"F\",\"temperatureTrend\": null,\"windSpeed\": \"6 mph\",\"windDirection\": \"SE\",\"icon\": \"https://api.weather.gov/icons/land/day/rain,60/rain_showers,40?size=medium\",\"shortForecast\": \"Light Rain Likely then Chance Rain Showers\",\"detailedForecast\": \"Rain likely before 7am, then a chance of rain showers. Cloudy, with a high near 53. Chance of precipitation is 60%.\"},{\"number\": 8,\"name\": \"Friday Night\",\"startTime\": \"2018-02-23T18:00:00-05:00\",\"endTime\": \"2018-02-24T06:00:00-05:00\",\"isDaytime\": false,\"temperature\": 49,\"temperatureUnit\": \"F\",\"temperatureTrend\": null,\"windSpeed\": \"6 mph\",\"windDirection\": \"SW\",\"icon\": \"https://api.weather.gov/icons/land/night/rain_showers,40/rain_showers,30?size=medium\",\"shortForecast\": \"Chance Rain Showers\",\"detailedForecast\": \"A chance of rain showers. Mostly cloudy, with a low around 49. Chance of precipitation is 40%.\"},{\"number\": 9,\"name\": \"Saturday\",\"startTime\": \"2018-02-24T06:00:00-05:00\",\"endTime\": \"2018-02-24T18:00:00-05:00\",\"isDaytime\": true,\"temperature\": 64,\"temperatureUnit\": \"F\",\"temperatureTrend\": null,\"windSpeed\": \"5 mph\",\"windDirection\": \"SW\",\"icon\": \"https://api.weather.gov/icons/land/day/rain_showers,60?size=medium\",\"shortForecast\": \"Rain Showers Likely\",\"detailedForecast\": \"Rain showers likely. Cloudy, with a high near 64. Chance of precipitation is 60%.\"},{\"number\": 10,\"name\": \"Saturday Night\",\"startTime\": \"2018-02-24T18:00:00-05:00\",\"endTime\": \"2018-02-25T06:00:00-05:00\",\"isDaytime\": false,\"temperature\": 54,\"temperatureUnit\": \"F\",\"temperatureTrend\": null,\"windSpeed\": \"6 mph\",\"windDirection\": \"S\",\"icon\": \"https://api.weather.gov/icons/land/night/rain_showers,70?size=medium\",\"shortForecast\": \"Rain Showers Likely\",\"detailedForecast\": \"Rain showers likely. Cloudy, with a low around 54. Chance of precipitation is 70%.\"},{\"number\": 11,\"name\": \"Sunday\",\"startTime\": \"2018-02-25T06:00:00-05:00\",\"endTime\": \"2018-02-25T18:00:00-05:00\",\"isDaytime\": true,\"temperature\": 66,\"temperatureUnit\": \"F\",\"temperatureTrend\": null,\"windSpeed\": \"8 mph\",\"windDirection\": \"SW\",\"icon\": \"https://api.weather.gov/icons/land/day/rain_showers,70?size=medium\",\"shortForecast\": \"Rain Showers Likely\",\"detailedForecast\": \"Rain showers likely. Mostly cloudy, with a high near 66. Chance of precipitation is 70%.\"},{\"number\": 12,\"name\": \"Sunday Night\",\"startTime\": \"2018-02-25T18:00:00-05:00\",\"endTime\": \"2018-02-26T06:00:00-05:00\",\"isDaytime\": false,\"temperature\": 46,\"temperatureUnit\": \"F\",\"temperatureTrend\": null,\"windSpeed\": \"8 mph\",\"windDirection\": \"NW\",\"icon\": \"https://api.weather.gov/icons/land/night/rain_showers,70/rain_showers,30?size=medium\",\"shortForecast\": \"Rain Showers Likely\",\"detailedForecast\": \"Rain showers likely. Partly cloudy, with a low around 46. Chance of precipitation is 70%.\"},{\"number\": 13,\"name\": \"Monday\",\"startTime\": \"2018-02-26T06:00:00-05:00\",\"endTime\": \"2018-02-26T18:00:00-05:00\",\"isDaytime\": true,\"temperature\": 59,\"temperatureUnit\": \"F\",\"temperatureTrend\": null,\"windSpeed\": \"7 mph\",\"windDirection\": \"NW\",\"icon\": \"https://api.weather.gov/icons/land/day/rain_showers,30/sct?size=medium\",\"shortForecast\": \"Chance Rain Showers then Mostly Sunny\",\"detailedForecast\": \"A chance of rain showers before 7am. Mostly sunny, with a high near 59. Chance of precipitation is 30%.\"},{\"number\": 14,\"name\": \"Monday Night\",\"startTime\": \"2018-02-26T18:00:00-05:00\",\"endTime\": \"2018-02-27T06:00:00-05:00\",\"isDaytime\": false,\"temperature\": 40,\"temperatureUnit\": \"F\",\"temperatureTrend\": null,\"windSpeed\": \"5 mph\",\"windDirection\": \"SW\",\"icon\": \"https://api.weather.gov/icons/land/night/few?size=medium\",\"shortForecast\": \"Mostly Clear\",\"detailedForecast\": \"Mostly clear, with a low around 40.\"}]}}";
		        
		        Gson gson = new Gson();
		        //Reader reader = new InputStreamReader(getResponseEntity.getContent());
		        
		        WeatherResponse parsedresponse = gson.fromJson(response, WeatherResponse.class);
		        //SearchResponse parsedresponse = gson.fromJson(reader, SearchResponse.class);
		        //System.out.println("id: " + String.valueOf(parsedresponse.id));
		        
		        WeatherPeriods objPeriod = parsedresponse.properties.periods.get(0);
		        System.out.println("today Desc: " + String.valueOf(objPeriod.shortForecast));
		        System.out.println("today high: " + String.valueOf(objPeriod.temperature));
		        
		        objPeriod = parsedresponse.properties.periods.get(1);
		        System.out.println("today low: " + String.valueOf(objPeriod.temperature));
		        
		        objPeriod = parsedresponse.properties.periods.get(2);
		        System.out.println("tomorrow Desc: " + String.valueOf(objPeriod.shortForecast));
		        System.out.println("tomorrow high: " + String.valueOf(objPeriod.temperature));
		        
		        objPeriod = parsedresponse.properties.periods.get(3);
		        System.out.println("tomorrow low: " + String.valueOf(objPeriod.temperature));
		        
		        System.out.println("------------------DONE----------------------");
	        }

    }
}

