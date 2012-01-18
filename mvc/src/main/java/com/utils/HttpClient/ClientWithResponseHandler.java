package com.utils.HttpClient;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class ClientWithResponseHandler {
	 public final static void main(String[] args) throws Exception {
	        
	        HttpClient httpclient = new DefaultHttpClient();

	        HttpGet httpget = new HttpGet("http://www.google.com/"); 

	        System.out.println("executing request " + httpget.getURI());

	        // Create a response handler
	        ResponseHandler<String> responseHandler = new BasicResponseHandler();
	        String responseBody = httpclient.execute(httpget, responseHandler);
	        System.out.println(responseBody);
	        
	        System.out.println("----------------------------------------");

	        // When HttpClient instance is no longer needed, 
	        // shut down the connection manager to ensure
	        // immediate deallocation of all system resources
	        httpclient.getConnectionManager().shutdown();        
	    }
}
