package com.shortandprecise.loadgenerator.process;

import com.shortandprecise.loadgenerator.config.PropertyConfig;
import com.shortandprecise.loadgenerator.config.SchemaConfig;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is the brain of this project. It generates load.
 *
 * @author rejuan
 */
public class LoadRunner implements Runnable {

    private final PropertyConfig propertyConfig;
    private final SchemaConfig schemaConfig;
    private final AtomicInteger requestTracker;
    private final Statistics statistics;
    private volatile boolean isRunning;

    public LoadRunner(PropertyConfig propertyConfig, SchemaConfig schemaConfig, Statistics statistics) {
        this.requestTracker = new AtomicInteger(0);
        this.propertyConfig = propertyConfig;
        this.schemaConfig = schemaConfig;
        this.statistics = statistics;
        this.isRunning = true;
    }

    @Override
    public void run() {
        Thread loadRunner = Thread.currentThread();
        RequestFacade requestFacade = new RequestFacade(schemaConfig, statistics);

        while (isRunning) {
            while (requestTracker.get() < propertyConfig.getNumberOfClient()) {
                requestTracker.incrementAndGet();
                requestFacade.request(loadRunner, requestTracker);
                qpsCircuit();
            }

            //TODO need to add proper comment
            sleep(50);
        }
    }

    private void qpsCircuit() {
        long totalRequest = statistics.getSuccessfulRequest() + statistics.getFailureRequest();
        if((totalRequest / propertyConfig.getQpsCountPeriod()) > propertyConfig.getMaxQpsShield()) {
            //TODO need to add proper comment
            sleep(100);
        }
    }

    private void sleep(long timeInMillis) {
        try {
            Thread.sleep(timeInMillis);
        } catch (InterruptedException ignored) {
            // No need to log this exception because when a request completed
            // then this sleeping will be interrupted to assure another call
        }
    }

    public void shutdown() {
        isRunning = false;
    }
}
