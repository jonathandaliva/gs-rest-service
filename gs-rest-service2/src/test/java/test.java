import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

public class test {
    public static void main(String[] args) throws ClientProtocolException, IOException {
    	System.setProperty("https.proxyHost", "10.100.0.18");
    	System.setProperty("https.proxyPort", "9090");
    	HttpClient client = HttpClientBuilder.create().build();
    	//System.setProperty("java.net.useSystemProxies", "true");

    		String url = "https://api.darksky.net/forecast/dec84d9b5c99a82a25e386b2257cf9b0/38.7609,-77.1852?exclude=currently,minutely,hourly,alerts,flags";
    		//String url = "http://localhost:8080/greeting";
	    	HttpGet httpget = new HttpGet(url);
	    	System.out.println("executing request " + httpget.getURI());

	        // Body contains your json string
	        HttpResponse response = client.execute(httpget);
	        
	        final int statusCode = response.getStatusLine().getStatusCode();
	        System.out.println("reponse code " + String.valueOf(statusCode));
	        if (statusCode != HttpStatus.SC_OK) {

	         	 System.out.println( "Error " + statusCode + " for URL " + url);

	            //return null;

	        } else {

		        System.out.println("----------------------------------------");
		        System.out.println(response.toString());
		        System.out.println("----------------------------------------");
		        HttpEntity getResponseEntity = response.getEntity();

		        Gson gson = new Gson();
		        Reader reader = new InputStreamReader(getResponseEntity.getContent());
		        
		        SearchResponse[] parsedresponse = gson.fromJson(reader, SearchResponse[].class);
		        //SearchResponse parsedresponse = gson.fromJson(reader, SearchResponse.class);
		        //System.out.println("id: " + String.valueOf(parsedresponse.id));
		        
		        System.out.println("------------------DONE----------------------");
	        }

    }
}

