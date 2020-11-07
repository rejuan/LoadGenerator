package com.shortandprecise.loadgenerator.model;

import io.netty.handler.codec.http.HttpHeaders;

/**
 * Request model
 */
public class Request {

	private String url;
	private HttpMethod httpMethod;
	private HttpHeaders headers;
	private String body;

	public Request(String url, HttpMethod httpMethod, HttpHeaders headers, String body) {
		this.url = url;
		this.httpMethod = httpMethod;
		this.headers = headers;
		this.body = body;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
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
