package hello;

public class Greeting {

    private long id;
    private boolean validResponse;
    private String tdyDesc;
    private float tdyHigh;
    private float tdyLow;
    private String tmwDesc;
    private float tmwHigh;
    private float tmwLow;
    
    public Greeting(long id, boolean validResponse) {
        this.id = id;
        this.validResponse = validResponse;
    }
    
    public Greeting(long id, String tdyDesc, float tdyHigh, float tdyLow, String tmwDesc, float tmwHigh, float tmwLow) {
        this.id = id;
        this.tdyDesc = tdyDesc;
        this.tdyHigh = tdyHigh;
        this.tdyLow = tdyLow;
        this.tmwDesc = tmwDesc;
        this.tmwHigh = tmwHigh;
        this.tmwLow = tmwLow;
    }

    public long getId() {
        return id;
    }

    public void setTdyDesc(String tdyDesc) {
    	this.tdyDesc = tdyDesc;
    }
    
    public boolean getValidResponse() {
        return validResponse;
    }

    public void setValidResponse(boolean validResponse) {
    	this.validResponse = validResponse;
    }
    
    public void setTdyHigh(float tdyHigh) {
    	this.tdyHigh = tdyHigh;
    }
    
    public void setTdyLow(float tdyLow) {
    	this.tdyLow = tdyLow;
    }
    
    public void setTmwDesc(String tmwDesc) {
    	this.tmwDesc = tmwDesc;
    }
    
    public void setTmwHigh(float tmwHigh) {
    	this.tmwHigh = tmwHigh;
    }
    
    public void setTmwLow(float tmwLow) {
    	this.tmwLow = tmwLow;
    }
    
    public String getTdyDesc() {
        return tdyDesc;
    }
    
    public float getTdyHigh() {
    	return tdyHigh;
    }
    
    public float getTdyLow() {
    	return tdyLow;
    }
    
    public String getTmwDesc() {
        return tmwDesc;
    }
    
    public float getTmwHigh() {
    	return tmwHigh;
    }
    
    public float getTmwLow() {
    	return tmwLow;
    }
}
