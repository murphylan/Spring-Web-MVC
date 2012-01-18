package com.utils.HttpClient;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;

public class ClientCustomContext {
	public final static void main(String[] args) throws Exception {

		HttpClient httpclient = new DefaultHttpClient();

		// Create a local instance of cookie store
		CookieStore cookieStore = new BasicCookieStore();

		// Create local HTTP context
		BasicHttpContext localContext = new BasicHttpContext();
		// Bind custom cookie store to the local context
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

		HttpGet httpget = new HttpGet("http://www.google.com/");

		System.out.println("executing request " + httpget.getURI());

		// Pass local context as a parameter
		HttpResponse response = httpclient.execute(httpget, localContext);
		HttpEntity entity = response.getEntity();

		System.out.println("----------------------------------------");
		System.out.println(response.getStatusLine());
		if (entity != null) {
			System.out.println("Response content length: "
					+ entity.getContentLength());
		}
		List<Cookie> cookies = cookieStore.getCookies();
		for (int i = 0; i < cookies.size(); i++) {
			System.out.println("Local cookie: " + cookies.get(i));
		}

		// Consume response content
		if (entity != null) {
			entity.consumeContent();
		}

		System.out.println("----------------------------------------");

		// When HttpClient instance is no longer needed,
		// shut down the connection manager to ensure
		// immediate deallocation of all system resources
		httpclient.getConnectionManager().shutdown();
	}
}
