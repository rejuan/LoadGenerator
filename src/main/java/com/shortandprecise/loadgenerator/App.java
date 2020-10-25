package com.shortandprecise.loadgenerator;

import com.shortandprecise.loadgenerator.config.PropertyConfig;
import com.shortandprecise.loadgenerator.process.LoadRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Starting point of the application
 *
 * @author rejuan
 */
public class App {

	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {

		try {
			PropertyConfig.getInstance();
		} catch (Exception ex) {
			LOGGER.error("Property doesn't loaded properly", ex);
			System.exit(0);
		}

		new Thread(new LoadRunner()).start();
	}
}
