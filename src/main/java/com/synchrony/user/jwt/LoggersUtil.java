package com.synchrony.user.jwt;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class LoggersUtil {
	private LoggersUtil() {
		// Do Nothing
	}

	private static final String USERNAME = "userId";
	private static final String LOGGING_STATUS = " | Logging Status: ";
	private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
	private static final String SOURCE_IP = " | SourceIp: ";
	private static final String TIMESTAMP = "Timestamp: ";
	private static final String REQUEST_URL = " | RequestUrl: ";
	private static final String IP_ADDRESS = "ipAddress";
	private static final String USER_NAME = " | UserName: ";

	public static String logIpUrlRequest(HttpServletRequest requestToCache) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		Date date = new Date();

		if (!StringUtil.isNull(requestToCache.getHeader(IP_ADDRESS))) {
			requestToCache.setAttribute(IP_ADDRESS, requestToCache.getHeader(IP_ADDRESS));
			requestToCache.setAttribute(USERNAME, requestToCache.getHeader(USERNAME));
			return TIMESTAMP + formatter.format(date) + SOURCE_IP + requestToCache.getHeader(IP_ADDRESS) + USER_NAME
					+ requestToCache.getHeader(USERNAME) + REQUEST_URL + requestToCache.getRequestURI()
					+ LOGGING_STATUS;
		} else {
			return TIMESTAMP + formatter.format(date) + SOURCE_IP + requestToCache.getRemoteAddr() + REQUEST_URL
					+ requestToCache.getRequestURI() + LOGGING_STATUS;

		}
	}

}
