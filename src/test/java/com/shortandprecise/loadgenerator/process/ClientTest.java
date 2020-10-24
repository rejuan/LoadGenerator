package com.shortandprecise.loadgenerator.process;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;
import org.eclipse.jetty.server.Server;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

	private final AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
	private final Thread clientGenerator = new Thread();
	private final AtomicInteger tracker = new AtomicInteger(1);
	private final int port = ThreadLocalRandom.current().nextInt(10000, 60000);
	private Client client;
	private Server server;

	@BeforeEach
	void setUp() throws Exception {
		this.server = new Server(port);
		this.server.start();
		this.client = new Client(asyncHttpClient, clientGenerator, tracker);
	}

	@AfterEach
	void tearDown() throws Exception {
		this.server.stop();
		this.server = null;
		this.client = null;
	}

	@Test
	void testGetRequestSuccess() throws ExecutionException, InterruptedException {
		String url = "http://localhost:" + port;
		ListenableFuture<Response> request = client.getRequest(url, null);
		request.get();

		assertEquals(0, tracker.get());
	}

	@Test
	void testGetRequestFailure() {
		try {
			String url = "http://localhost:80/";
			ListenableFuture<Response> request = client.getRequest(url, null);
			request.get();
		} catch (Exception ex) {
			// Expected exception to verify failure case
		}

		assertEquals(0, tracker.get());
	}

	@Test
	void testPostRequestSuccess() throws ExecutionException, InterruptedException {
		String url = "http://localhost:" + port;
		String body = "[1,2]";
		ListenableFuture<Response> request = client.postRequest(url, body, null);
		request.get();

		assertEquals(0, tracker.get());
	}

	@Test
	void testPostRequestFailure() {
		try {
			String url = "http://localhost:80/";
			String body = "[1,2]";
			ListenableFuture<Response> request = client.postRequest(url, body, null);
			request.get();
		} catch (Exception ex) {
			// Expected exception to verify failure case
		}

		assertEquals(0, tracker.get());
	}
}