package com.shortandprecise;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by rejuan on 6/12/17.
 */
public class ClientGenerator implements Runnable {

    private AsyncHttpClient asyncHttpClient;
    private AtomicInteger tracker;
    private int numberOfClient;
    private String url;

    public ClientGenerator(String url, int numberOfClient) {
        this.asyncHttpClient = new DefaultAsyncHttpClient();
        this.tracker = new AtomicInteger(0);
        this.numberOfClient = numberOfClient;
        this.url = url;
    }

    @Override
    public void run() {

        while (true) {
            while (tracker.get() < numberOfClient) {
                request();
                tracker.incrementAndGet();
            }

            try {
                Thread.sleep(100);
            } catch (Exception ex) {

            }
        }
    }

    private void request() {

        asyncHttpClient.prepareGet(url).execute(new AsyncCompletionHandler<Response>() {

            @Override
            public Response onCompleted(Response response) throws Exception {
                return response;
            }

            @Override
            public void onThrowable(Throwable t) {
                tracker.decrementAndGet();
            }
        });
    }
}
