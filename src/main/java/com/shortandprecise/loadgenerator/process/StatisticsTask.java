package com.shortandprecise.loadgenerator.process;

import com.shortandprecise.loadgenerator.config.PropertyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Write statistics to log
 */
public class StatisticsTask implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsTask.class);
	private final Statistics statistics;
	private final PropertyConfig propertyConfig;

	public StatisticsTask(Statistics statistics, PropertyConfig propertyConfig) {
		this.statistics = statistics;
		this.propertyConfig = propertyConfig;
	}

	@Override
	public void run() {
		long successfulRequest = statistics.resetAndGetSuccessfulRequest();
		long failureRequest = statistics.resetAndGetFailureRequest();
		long totalResponseTime = statistics.resetAndGetTotalResponseTime();
		long maxResponseTime = statistics.resetAndGetMaxResponseTime();

		long averageResponseTime = (totalResponseTime / (successfulRequest + failureRequest));
		long successQps = (successfulRequest / propertyConfig.getQpsCountPeriod());
		long failureQps = (failureRequest / propertyConfig.getQpsCountPeriod());

		LOGGER.info("Max response time: {} ## Avg response time: {} ## Success QPS: {} ## Failure QPS: {}",
				maxResponseTime, averageResponseTime, successQps, failureQps);
	}
}
