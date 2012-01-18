package com.utils.HttpClient;

import java.security.Principal;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class ClientConnectionRelease {
	public final static void main(String[] args) throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpContext localContext1 = new BasicHttpContext();
		HttpGet httpget1 = new HttpGet("http://www.google.com/"); 
		HttpResponse response1 = httpclient.execute(httpget1, localContext1);
		HttpEntity entity1 = response1.getEntity();
		if (entity1 != null) {
		    entity1.consumeContent();
		}
		Principal principal = (Principal) localContext1.getAttribute(
		        ClientContext.USER_TOKEN);

		HttpContext localContext2 = new BasicHttpContext();
		localContext2.setAttribute(ClientContext.USER_TOKEN, principal);
		HttpGet httpget2 = new HttpGet("http://www.google.com/"); 
		HttpResponse response2 = httpclient.execute(httpget2, localContext2);
		HttpEntity entity2 = response2.getEntity();
		if (entity2 != null) {
		    entity2.consumeContent();
		}   
    }
}
