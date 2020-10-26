package com.shortandprecise.loadgenerator.model;

import io.netty.handler.codec.http.HttpHeaders;

import java.util.Map;

/**
 * Request model
 */
public class Request {

	private String url;
	private String method;
	private HttpHeaders headers;
	private String body;

	public Request(String url, String method, HttpHeaders headers, String body) {
		this.url = url;
		this.method = method;
		this.headers = headers;
		this.body = body;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public HttpHeaders getHeaders() {
		return headers;
	}

	public void setHeaders(HttpHeaders headers) {
		this.headers = headers;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
