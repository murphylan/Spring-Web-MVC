package com.utils.htmlcode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class HtmlCode {
	/*
	 * org.apache.commons工具类 传入url，得到Html代码
	 */
	public static String getHtml(String httpUrl) throws IOException {
		InputStream in = new URL(httpUrl).openStream();
		try {
			return IOUtils.toString(in);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	/*
	 * org.apache.commons工具类 传入url，生成文件
	 */
	public static void setHtmlToFile(String httpUrl, String filePath)
			throws IOException {
		File destination = new File(filePath);
		URL source = new URL(httpUrl);
		FileUtils.copyURLToFile(source, destination);
	}

	public static void main(String args[]) throws IOException {
		String httpUrl = "http://www.baidu.com";
		String filePath = "c:/new.html";
		System.out.println(HtmlCode.getHtml(httpUrl));
		HtmlCode.setHtmlToFile(httpUrl, filePath);
	}
}
