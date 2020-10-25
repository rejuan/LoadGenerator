package com.shortandprecise.loadgenerator.config;

import com.shortandprecise.loadgenerator.constant.Constant;

public class PropertyConfig {

	private static PropertyConfig intance;

	private static final String MAX_QPS_SHIELD_DEFAULT_VALUE = "100";
	private static final String NUMBER_OF_CLIENT_DEFAULT = "10";
	private static final String QPS_COUNT_PERIOD_DEFAULT = "10";

	private int maxQpsShield;
	private int numberOfClient;
	private int qpsCountPeriod;

	private PropertyConfig() {
		maxQpsShield = Integer.parseInt(
				System.getProperty(Constant.MAX_QPS_SHIELD_PROPERTY_STRING, MAX_QPS_SHIELD_DEFAULT_VALUE));

		numberOfClient =
				Integer.parseInt(System.getProperty(Constant.NUMBER_OF_CLIENT_PROPERTY_STRING, NUMBER_OF_CLIENT_DEFAULT));

		qpsCountPeriod =
				Integer.parseInt(System.getProperty(Constant.QPS_COUNT_PERIOD_PROPERTY_STRING, QPS_COUNT_PERIOD_DEFAULT));
	}

	public static PropertyConfig getInstance() {
		if (intance == null) {
			intance = new PropertyConfig();
		}

		return intance;
	}

	public int getMaxQpsShield() {
		return maxQpsShield;
	}

	public int getNumberOfClient() {
		return numberOfClient;
	}

	public int getQpsCountPeriod() {
		return qpsCountPeriod;
	}
}
