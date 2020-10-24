package com.shortandprecise.loadgenerator.constant;

public class Constant {

	private static final String MAX_QPS_SHIELD_DEFAULT_VALUE = "100";

	private static final String MAX_QPS_SHIELD_PROPERTY_STRING = "max.qps.shield";

	public static final int MAX_QPS_SHIELD =
			Integer.parseInt(System.getProperty(MAX_QPS_SHIELD_PROPERTY_STRING, MAX_QPS_SHIELD_DEFAULT_VALUE));

	private static final String NUMBER_OF_CLIENT_DEFAULT = "10";

	private static final String NUMBER_OF_CLIENT_PROPERTY_STRING = "number.of.client";

	public static final int NUMBER_OF_CLIENT =
			Integer.parseInt(System.getProperty(NUMBER_OF_CLIENT_PROPERTY_STRING, NUMBER_OF_CLIENT_DEFAULT));

	private static final String QPS_COUNT_PERIOD_DEFAULT = "10";

	private static final String QPS_COUNT_PERIOD_PROPERTY_STRING = "qps.count.period";

	public static final int QPS_COUNT_PERIOD =
			Integer.parseInt(System.getProperty(QPS_COUNT_PERIOD_PROPERTY_STRING, QPS_COUNT_PERIOD_DEFAULT));
}
