package com.shortandprecise.loadgenerator;

import com.shortandprecise.loadgenerator.config.PropertyConfig;
import com.shortandprecise.loadgenerator.config.SchemaConfig;
import com.shortandprecise.loadgenerator.process.LoadRunner;
import com.shortandprecise.loadgenerator.process.Statistics;
import com.shortandprecise.loadgenerator.process.StatisticsTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Starting point of the application
 *
 * @author rejuan
 */
public class App {

	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {

		PropertyConfig propertyConfig = null;
		try {
			propertyConfig = new PropertyConfig();
		} catch (Exception ex) {
			LOGGER.error("Property loading problem", ex);
			System.exit(0);
		}

		SchemaConfig schemaConfig = null;
		try {
			schemaConfig = new SchemaConfig(propertyConfig);
		} catch (Exception ex) {
			LOGGER.error("Schema loading problem", ex);
			System.exit(0);
		}

		Statistics statistics = new Statistics();
		StatisticsTask statisticsTask = new StatisticsTask(statistics, propertyConfig);

		new Thread(new LoadRunner(propertyConfig, schemaConfig, statistics)).start();
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(statisticsTask, propertyConfig.getQpsCountPeriod(),
				propertyConfig.getQpsCountPeriod(), TimeUnit.SECONDS);

	}
}
