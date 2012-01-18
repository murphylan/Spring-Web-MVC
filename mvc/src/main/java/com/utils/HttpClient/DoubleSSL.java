package com.utils.HttpClient;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * This example demonstrates how to create secure connections with a custom SSL
 * context.
 */
public class DoubleSSL {

    public final static void main(String[] args) throws Exception {
        DefaultHttpClient httpclient = new DefaultHttpClient();

        KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());   
        FileInputStream instream = new FileInputStream(new File("D:/server.jks"));
        try {
            trustStore.load(instream, "password".toCharArray());
        } finally {
            instream.close();
        }
        
        SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore,"password",trustStore);
        Scheme sch = new Scheme("https", socketFactory, 443);
        httpclient.getConnectionManager().getSchemeRegistry().register(sch);

        HttpGet httpget = new HttpGet("https://localhost:8443/");

        System.out.println("executing request" + httpget.getRequestLine());
        
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();

        System.out.println("----------------------------------------");
        System.out.println(response.getStatusLine());
        if (entity != null) {
            System.out.println("Response content length: " + entity.getContentLength());
        }
        if (entity != null) {
            entity.consumeContent();
        }
        httpclient.getConnectionManager().shutdown();        
    }

}