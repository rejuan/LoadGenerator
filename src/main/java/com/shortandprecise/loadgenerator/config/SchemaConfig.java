package com.shortandprecise.loadgenerator.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shortandprecise.loadgenerator.exception.SchemaValidationException;
import com.shortandprecise.loadgenerator.model.HttpMethod;
import com.shortandprecise.loadgenerator.model.Request;
import com.shortandprecise.loadgenerator.model.Schema;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Load schema from file
 */
public class SchemaConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(SchemaConfig.class);

	private Schema schema;
	private final PropertyConfig propertyConfig;
	private static final String BASE_URL = "baseUrl";
	private static final String REQUESTS = "requests";
	private static final String URL = "url";
	private static final String METHOD = "method";
	private static final String HEADERS = "headers";
	private static final String BODY = "body";

	public SchemaConfig(PropertyConfig propertyConfig) {
		this.propertyConfig = propertyConfig;
		loadSchema();
	}

	private void loadSchema() {
		Map<String, Object> schemaMap;
		try {
			File file = new File(propertyConfig.getSchemaPath());
			schemaMap = new ObjectMapper().readValue(file, Map.class);
			schema = prepareSchema(schemaMap);
		} catch (Exception ex) {
			LOGGER.error("Schema loading problem", ex);
			System.exit(0);
		}
	}

	/**
	 * Convert schema map to {@link Schema} object
	 *
	 * @param schemaMap Schema map
	 * @return Schema
	 */
	private Schema prepareSchema(Map<String, Object> schemaMap) {
		List<Request> requestList = new ArrayList<>();
		String baseUrl = (String) schemaMap.get(BASE_URL);
		List<Map<String, Object>> requests = (List<Map<String, Object>>) schemaMap.get(REQUESTS);
		requests.forEach(requestMap -> {
			String url = baseUrl + requestMap.get(URL);
			String method = (String) requestMap.get(METHOD);
			String body = (String) requestMap.get(BODY);
			HttpHeaders httpHeaders = getHttpHeaders(requestMap);

			requestList.add(new Request(url, getHttpMethod(method), httpHeaders, body));
		});

		return new Schema(requestList);
	}

	private HttpHeaders getHttpHeaders(Map<String, Object> requestMap) {
		Map<String, String> headers = (Map<String, String>) requestMap.get(HEADERS);

		HttpHeaders httpHeaders = new DefaultHttpHeaders();
		headers.forEach(httpHeaders::add);
		return httpHeaders;
	}

	private HttpMethod getHttpMethod(String method) {
		HttpMethod httpMethod = HttpMethod.get(method);
		if(Objects.isNull(httpMethod)) {
			throw new SchemaValidationException("Invalid http method in schema");
		} else {
			return httpMethod;
		}
	}

	public Schema getSchema() {
		return schema;
	}
}
