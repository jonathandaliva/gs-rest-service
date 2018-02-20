package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

@RestController
public class GreetingController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="lat", defaultValue="38.803328") String lat, @RequestParam(value="lon", defaultValue="-77.039026") String lon, ) {
    	//38.803328, -77.039026
    	//"https://api.darksky.net/forecast/dec84d9b5c99a82a25e386b2257cf9b0/38.7609,-77.1852?exclude=currently,minutely,hourly,alerts,flags"
    	String data="";
    	
    	//TODO call darksky, parse json, create simple one.
        return new Greeting(counter.incrementAndGet(), data);
    }
}
