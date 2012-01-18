package com.utils.threadlocal;

public class FakeSession {
	public static ThreadLocal<String> messageSegment = new ThreadLocal<String>() {
		public String initialValue() {
			return "";
		}
	};
}
