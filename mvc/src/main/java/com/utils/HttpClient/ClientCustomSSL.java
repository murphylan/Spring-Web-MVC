package com.utils.HttpClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

/*
 * 1、客户端是通过HTTPS请求服务端支付宝WAP支付平台；
 * 2、POST方式提交请求
 * 3、紧凑方式  XML格式参数
 */
public class ClientCustomSSL {

	public HttpPost getHttppost(DefaultHttpClient httpclient, String https)
			throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, IOException, KeyManagementException,
			UnrecoverableKeyException {
		KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
		// 本地证书
		FileInputStream instream = new FileInputStream(new File("D:/server.jks"));
		try {
			trustStore.load(instream, "password".toCharArray());// 设置密码
		} finally {
			instream.close();
		}
		SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
		Scheme sch = new Scheme("https", socketFactory,443);
		httpclient.getConnectionManager().getSchemeRegistry().register(sch);
		httpclient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT,
				3000); // 超时设置
		httpclient.getParams().setIntParameter(
				HttpConnectionParams.CONNECTION_TIMEOUT, 3000);// 连接超时
		// 支付宝接口URL
		HttpPost httppost = new HttpPost(https);

		return httppost;
	}

	public InputStreamEntity getReqEntity(String xml) throws FileNotFoundException{
		File file = new File(xml);
		InputStreamEntity reqEntity = new InputStreamEntity(
				new FileInputStream(file), -1);
		reqEntity.setContentType("binary/octet-stream");
		reqEntity.setChunked(true);
		reqEntity.setContentEncoding("utf-8");
		return reqEntity;
	}
	/**
	 * @param args
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public final static void main(String[] args) throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		ClientCustomSSL clientCustomSSL = new ClientCustomSSL();

		// 支付宝接口URL
		HttpPost httppost = clientCustomSSL.getHttppost(httpclient,"https://localhost:8443/");
		// XML格式文件
//		httppost.setEntity(clientCustomSSL.getReqEntity("<reg>请求参数</reg>"));
		
		System.out.println("executing request " + httppost.getRequestLine());
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();

		System.out.println("----------------------------------------");
		System.out.println(response.getStatusLine());
		if (entity != null) {
			System.out.println("Response content length: "
					+ entity.getContentLength());
			InputStream read = entity.getContent();
			while (read.read() != -1) {
				System.out.println("Response content: " + read.read());
			}
		}
		if (entity != null) {
			entity.consumeContent();
		}
		httpclient.getConnectionManager().shutdown();
	}
}
