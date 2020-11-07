package com.shortandprecise.loadgenerator.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SchemaConfigTest {

	@Mock
	PropertyConfig propertyConfig;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetSchema() throws IOException {
		//When
		when(propertyConfig.getSchemaPath()).thenReturn("schema/schema.json");

		//Then
		SchemaConfig schemaConfig = new SchemaConfig(propertyConfig);

		//Verify
		verify(propertyConfig, times(1)).getSchemaPath();
		assertEquals(2, schemaConfig.getSchema().getRequests().size());
	}
}