package com.shortandprecise.loadgenerator.process;

import io.netty.handler.codec.http.HttpHeaders;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class makes HTTP request and send stat to Statistics class
 *
 * @author rejuan
 */
public class Client {

	private final AsyncHttpClient asyncHttpClient;
	private final Thread clientGenerator;
	private final AtomicInteger tracker;

	public Client(AsyncHttpClient asyncHttpClient, Thread clientGenerator, AtomicInteger tracker) {
		this.asyncHttpClient = asyncHttpClient;
		this.clientGenerator = clientGenerator;
		this.tracker = tracker;
	}

	/**
	 * Make a GET request to provided URL. Send given header as part of request
	 *
	 * @param url         Target URL
	 * @param httpHeaders {@link HttpHeaders}
	 * @return ListenableFuture<Response>
	 */
	public ListenableFuture<Response> getRequest(String url, HttpHeaders httpHeaders) {
		return asyncHttpClient.prepareGet(url).setHeaders(httpHeaders).execute(new AsyncCompletionHandler<>() {

			@Override
			public Response onCompleted(Response response) {
				tracker.decrementAndGet();
				clientGenerator.interrupt();
				return response;
			}

			@Override
			public void onThrowable(Throwable t) {
				tracker.decrementAndGet();
				clientGenerator.interrupt();
			}
		});
	}

	/**
	 * Make a POST request to provided URL. Send given header as part of request
	 *
	 * @param url         Target URL
	 * @param httpHeaders {@link HttpHeaders}
	 * @return ListenableFuture<Response>
	 */
	public ListenableFuture<Response> postRequest(String url, String body, HttpHeaders httpHeaders) {
		return asyncHttpClient.preparePost(url).setHeaders(httpHeaders).setBody(body)
				.execute(new AsyncCompletionHandler<>() {

					@Override
					public Response onCompleted(Response response) {
						tracker.decrementAndGet();
						clientGenerator.interrupt();
						return response;
					}

					@Override
					public void onThrowable(Throwable t) {
						tracker.decrementAndGet();
						clientGenerator.interrupt();
					}
				});
	}
}
