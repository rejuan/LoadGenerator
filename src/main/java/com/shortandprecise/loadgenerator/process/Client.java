package com.shortandprecise.loadgenerator.process;

import com.shortandprecise.loadgenerator.model.HttpMethod;
import io.netty.handler.codec.http.HttpHeaders;
import org.apache.commons.lang3.StringUtils;
import org.asynchttpclient.*;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class makes HTTP request and send stat to Statistics class
 *
 * @author rejuan
 */
public class Client {

	private final AsyncHttpClient asyncHttpClient;
	private final Statistics statistics;

	public Client(AsyncHttpClient asyncHttpClient, Statistics statistics) {
		this.asyncHttpClient = asyncHttpClient;
		this.statistics = statistics;
	}

	/**
	 * Make a GET request to provided URL. Send given header as part of request
	 *
	 * @param url         Target URL
	 * @param httpMethod  {@link HttpMethod}
	 * @param httpHeaders {@link HttpHeaders}
	 * @param body        Request body
	 * @return ListenableFuture<Response>
	 */
	public ListenableFuture<Response> request(String url, HttpMethod httpMethod, HttpHeaders httpHeaders,
	                                          String body, Thread loadRunner, AtomicInteger requestTracker) {
		long startTime = System.currentTimeMillis();
		BoundRequestBuilder requestBuilder = asyncHttpClient.prepare(httpMethod.getMethod(), url)
				.setHeaders(httpHeaders);
		if (StringUtils.isNotBlank(body)) {
			requestBuilder.setBody(body);
		}

		return requestBuilder.execute(new AsyncCompletionHandler<>() {

			@Override
			public Response onCompleted(Response response) {
				requestTracker.decrementAndGet();
				statistics.storeSuccessStat((System.currentTimeMillis() - startTime));
				loadRunner.interrupt();
				return response;
			}

			@Override
			public void onThrowable(Throwable t) {
				requestTracker.decrementAndGet();
				statistics.storeFailureStat((System.currentTimeMillis() - startTime));
				loadRunner.interrupt();
			}
		});
	}
}
