package com.shortandprecise.loadgenerator.process;

import com.shortandprecise.loadgenerator.config.SchemaConfig;
import com.shortandprecise.loadgenerator.model.Request;
import com.shortandprecise.loadgenerator.model.Schema;
import io.netty.handler.codec.http.HttpHeaders;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class select url and send request through {@link Client} class
 *
 * @author rejuan
 */
public class RequestFacade {

	private final Schema schema;
	private final Client client;
	private int index = 0;
	private final int numberOfUrl;
	private final AsyncHttpClient asyncHttpClient;

	public RequestFacade(SchemaConfig schemaConfig, Statistics statistics) {
		this.schema = schemaConfig.getSchema();
		this.asyncHttpClient = new DefaultAsyncHttpClient();
		this.client = new Client(this.asyncHttpClient, statistics);
		this.numberOfUrl = schema.getRequests().size();
	}

	public void request(Thread loadRunner, AtomicInteger requestTracker) {
		Request request = getRequestObject();
		String url = request.getUrl();
		String body = request.getBody();
		HttpHeaders httpHeaders = request.getHeaders();

		if (request.getMethod().equalsIgnoreCase("get")) {
			client.getRequest(url, httpHeaders, loadRunner, requestTracker);
		} else {
			client.postRequest(url, body, httpHeaders, loadRunner, requestTracker);
		}
	}

	private Request getRequestObject() {
		index = ((index + 1) % numberOfUrl);
		return schema.getRequests().get(index);
	}
}
