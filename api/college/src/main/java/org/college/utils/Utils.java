package org.college.utils;

public class Utils {

	public Utils() {
	}

	public static boolean isBlankStr(String value) {
		if (value == null || value.isEmpty())
			return true;

		return false;
	}

	public static boolean isNotBlankStr(String value) {
		return !isBlankStr(value);
	}

}
