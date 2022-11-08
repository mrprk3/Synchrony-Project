package com.synchrony.user.jwt;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {

	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

	private static final String TOKEN_DELIMITER = "@K@";

	private StringUtil() {
		// Do Nothing
	}

	public static boolean isNotNull(Object input) {
		return (input != null);
	}

	public static boolean isListNullNEmpty(List list) {
		return (list == null || list.isEmpty());
	}

	public static boolean isNull(Object input) {
		return (input == null);
	}

	@SuppressWarnings("rawtypes")
	public static boolean isNotNullNEmpty(String data) {
		return (data != null && !data.isEmpty());
	}

	@SuppressWarnings("rawtypes")
	public static boolean isListNotNullNEmpty(List list) {
		return (list != null && !list.isEmpty());
	}

}