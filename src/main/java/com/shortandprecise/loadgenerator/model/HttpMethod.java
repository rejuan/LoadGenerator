package com.shortandprecise.loadgenerator.model;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Http method enum
 */
public enum HttpMethod {

	GET("get"),
	POST("post");

	private final String method;

	HttpMethod(String method) {
		this.method = method;
	}

	public String getMethod() {
		return method;
	}

	private static final Map<String, HttpMethod> httpMethodMap = new HashMap<>();

	static {
		for (HttpMethod httpMethod : HttpMethod.values()) {
			httpMethodMap.put(httpMethod.getMethod(), httpMethod);
		}
	}

	public static HttpMethod get(String method) {
		if(StringUtils.isBlank(method)) {
			return null;
		}
		return httpMethodMap.get(method.toLowerCase());
	}
}
