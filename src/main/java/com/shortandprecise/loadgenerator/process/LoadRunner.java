package com.shortandprecise.loadgenerator.process;

import com.shortandprecise.loadgenerator.constant.Constant;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is the brain of this project. It generates load.
 *
 * @author rejuan
 */
public class LoadRunner implements Runnable {

    private final AsyncHttpClient asyncHttpClient;
    private final AtomicInteger requestTracker;
    private final Statistics statistics;

    public LoadRunner() {
        this.asyncHttpClient = new DefaultAsyncHttpClient();
        this.requestTracker = new AtomicInteger(0);
        this.statistics = new Statistics();
    }

    @Override
    public void run() {
        Client client = new Client(asyncHttpClient, Thread.currentThread(), requestTracker);

        while (true) {
            while (requestTracker.get() < Constant.NUMBER_OF_CLIENT) {
                requestTracker.incrementAndGet();
                //TODO need to create request from here
                qpsCircuit();
            }

            //TODO need to add proper comment
            sleep(50);
        }
    }

    private void qpsCircuit() {
        long totalRequest = statistics.getSuccessfulRequest() + statistics.getFailureRequest();
        if((totalRequest / Constant.QPS_COUNT_PERIOD) > Constant.MAX_QPS_SHIELD) {
            //TODO need to add proper comment
            sleep(100);
        }
    }

    private void sleep(long timeInMillis) {
        try {
            Thread.sleep(timeInMillis);
        } catch (Exception ignored) {
        }
    }
}
