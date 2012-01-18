package com.utils.encode;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public abstract class EncodeUtils {

	private static final String DEFAULT_URL_ENCODING = "UTF-8";
	public static Md5PasswordEncoder md5coder = null;

	static {
		md5coder = new Md5PasswordEncoder();
	}

	/*
	 * md5加密
	 */
	public static String getMd5PasswordEncoder(String password, String salt) {
		return md5coder.encodePassword(password, salt);
	}

	/**
	 * URL 编码, Encode默认为UTF-8.
	 */
	public static String urlEncode(String input) {
		return urlEncode(input, DEFAULT_URL_ENCODING);
	}

	/**
	 * URL 编码.
	 */
	public static String urlEncode(String input, String encoding) {
		try {
			return URLEncoder.encode(input, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(
					"Unsupported Encoding Exception", e);
		}
	}

	/**
	 * URL 解码, Encode默认为UTF-8.
	 */
	public static String urlDecode(String input) {
		return urlDecode(input, DEFAULT_URL_ENCODING);
	}

	/**
	 * URL 解码.
	 */
	public static String urlDecode(String input, String encoding) {
		try {
			return URLDecoder.decode(input, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(
					"Unsupported Encoding Exception", e);
		}
	}

	/**
	 * Html 转码.
	 */
	public static String htmlEscape(String html) {
		return StringEscapeUtils.escapeHtml(html);
	}

	/**
	 * Html 解码.
	 */
	public static String htmlUnescape(String htmlEscaped) {
		return StringEscapeUtils.unescapeHtml(htmlEscaped);
	}

	/**
	 * Xml 转码.
	 */
	public static String xmlEscape(String xml) {
		return StringEscapeUtils.escapeXml(xml);
	}

	/**
	 * Xml 解码.
	 */
	public static String xtmlUnescape(String xmlEscaped) {
		return StringEscapeUtils.unescapeXml(xmlEscaped);
	}

	/**
	 * Cookie 转码(保存中文).
	 */
	public static String cookieEscape(String java) {
		String temp=StringEscapeUtils.escapeJava(java);
		return StringUtils.replace(temp, "\\", "");
	}
	
	public static void main(String[] args) {
		String temp="月";
		System.out.println(StringEscapeUtils.escapeCsv(temp));
		System.out.println(StringEscapeUtils.escapeHtml(temp));
		System.out.println(StringEscapeUtils.escapeJava(temp));
		System.out.println(StringEscapeUtils.escapeJavaScript(temp));
		System.out.println(StringEscapeUtils.escapeSql(temp));
		System.out.println(StringEscapeUtils.escapeXml(temp));
		System.out.println(cookieEscape(temp));
	}
}
