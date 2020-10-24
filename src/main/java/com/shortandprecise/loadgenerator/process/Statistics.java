package com.shortandprecise.loadgenerator.process;

import java.util.concurrent.atomic.AtomicLong;

/**
 * The class handle request-response related statistics
 *
 * @author rejuan
 */
public class Statistics {

	private final AtomicLong totalResponseTime;
	private final AtomicLong successfulRequest;
	private final AtomicLong failureRequest;
	private final AtomicLong maxResponseTime;

	public Statistics() {
		this.totalResponseTime = new AtomicLong(0);
		this.successfulRequest = new AtomicLong(0);
		this.failureRequest = new AtomicLong(0);
		this.maxResponseTime = new AtomicLong(0);
	}

	/**
	 * Store successful request stat
	 *
	 * @param responseTime Response time in long
	 */
	public void storeSuccessStat(long responseTime) {
		successfulRequest.incrementAndGet();
		totalResponseTime.addAndGet(responseTime);
		updateMaxResponseTime(responseTime);
	}

	/**
	 * Store failure request stat
	 *
	 * @param responseTime Response time in long
	 */
	public void storeFailureStat(long responseTime) {
		failureRequest.incrementAndGet();
		totalResponseTime.addAndGet(responseTime);
		updateMaxResponseTime(responseTime);
	}

	private void updateMaxResponseTime(long responseTime) {
		if (responseTime > maxResponseTime.get()) {
			maxResponseTime.set(responseTime);
		}
	}

	/**
	 * Provide total response time
	 *
	 * @return long
	 */
	public long getTotalResponseTime() {
		return totalResponseTime.get();
	}

	/**
	 * Provide total successful request number
	 *
	 * @return long
	 */
	public long getSuccessfulRequest() {
		return successfulRequest.get();
	}

	/**
	 * Provide total failure request number
	 *
	 * @return long
	 */
	public long getFailureRequest() {
		return failureRequest.get();
	}

	/**
	 * Provide max response time
	 *
	 * @return long
	 */
	public long getMaxResponseTime() {
		return maxResponseTime.get();
	}
}
