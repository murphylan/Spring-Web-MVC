package com.utils.date;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;

/*
 * joda类的使用
 * DateFormatUtils类的使用
 * DateUtils类的使用
 */
public class DateUtil {
	static String[] parsePatterns = { "yyyy-MM-dd", "yyyy/MM/dd" };

	/*
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return DateFormatUtils.format(new Date(), "yyyy-MM-dd");
	}

	/*
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/*
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return (new DateTime()).toString("HH:mm:ss");
	}

	/*
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return (new DateTime()).toString("yyyy-MM-dd HH:mm:ss");
	}

	/*
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return (new DateTime()).toString("E");
	}

	/*
	 * 天数相加
	 */
	public static Date addDay(Date date, int d) {
		return DateUtils.addDays(date, d);
	}

	/*
	 * 月份相加
	 */
	public static Date addMonths(Date date, int m) {
		return DateUtils.addMonths(date, m);
	}

	/*
	 * 日期型字符串转化为日期 格式（"yyyy-MM-dd","yyyy/MM/dd"）
	 */
	public static Date parseDate(String str) throws ParseException {
		return DateUtils.parseDate(str, parsePatterns);
	}

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		System.out.println(formatDate(parseDate("2010/3/6")));
		System.out.println(formatDate(new Date(),"E"));
	}

}
