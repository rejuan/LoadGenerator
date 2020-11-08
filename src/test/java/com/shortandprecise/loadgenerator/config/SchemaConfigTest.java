package com.shortandprecise.loadgenerator.config;

import com.shortandprecise.loadgenerator.exception.SchemaValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class SchemaConfigTest {

	private String resourcePath = "src/test/resources";

	@Mock
	PropertyConfig propertyConfig;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void tearDown() {
		propertyConfig = null;
	}

	@Test
	void testGetSchema() throws IOException {
		//When
		when(propertyConfig.getSchemaPath()).thenReturn(resourcePath + "/schema/schema.json");

		//Then
		SchemaConfig schemaConfig = new SchemaConfig(propertyConfig);

		//Verify
		verify(propertyConfig, times(1)).getSchemaPath();
		assertEquals(2, schemaConfig.getSchema().getRequests().size());
	}

	@Test
	void testWrongMethod() {
		//When
		when(propertyConfig.getSchemaPath()).thenReturn(resourcePath + "/schema/wrongMethodSchema.json");

		//Then+Verify
		assertThrows(SchemaValidationException.class, () -> new SchemaConfig(propertyConfig));
	}

	@Test
	void testWrongFilePath() {
		//When
		when(propertyConfig.getSchemaPath()).thenReturn(resourcePath + "/schema/wrongFile.json");

		//Then+Verify
		assertThrows(IOException.class, () -> new SchemaConfig(propertyConfig));
	}
}