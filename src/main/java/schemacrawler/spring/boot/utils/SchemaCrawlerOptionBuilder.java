package schemacrawler.spring.boot.utils;

import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaInfoLevel;
import schemacrawler.schemacrawler.SchemaInfoLevelBuilder;
/**
 * SchemaCrawler utility methods.
 * @author vindell
 */
public final class SchemaCrawlerOptionBuilder {

	public static SchemaCrawlerOptions custom(SchemaInfoLevel schemaInfoLevel) {
		
		final SchemaCrawlerOptions options = new SchemaCrawlerOptions();
		// Set what details are required in the schema - this affects the time taken to crawl the schema
		options.setSchemaInfoLevel(schemaInfoLevel);
		
		return options;
	}

	public static SchemaCrawlerOptions detailed() {
		
		final SchemaCrawlerOptions options = new SchemaCrawlerOptions();
		// Set what details are required in the schema - this affects the time taken to crawl the schema
		SchemaInfoLevel schemaInfoLevel = SchemaInfoLevelBuilder.detailed();
		options.setSchemaInfoLevel(schemaInfoLevel);
		
		return options;
	}

	public static SchemaCrawlerOptions maximum() {
		
		final SchemaCrawlerOptions options = new SchemaCrawlerOptions();
		// Set what details are required in the schema - this affects the time taken to crawl the schema
		SchemaInfoLevel schemaInfoLevel = SchemaInfoLevelBuilder.maximum();
		options.setSchemaInfoLevel(schemaInfoLevel);
		
		return options;
	}

	public static SchemaCrawlerOptions minimum() {
		
		final SchemaCrawlerOptions options = new SchemaCrawlerOptions();
		// Set what details are required in the schema - this affects the time taken to crawl the schema
		SchemaInfoLevel schemaInfoLevel = SchemaInfoLevelBuilder.minimum();
		options.setSchemaInfoLevel(schemaInfoLevel);
		
		return options;
	}
	
	public static SchemaCrawlerOptions standard() {
		
		final SchemaCrawlerOptions options = new SchemaCrawlerOptions();
		// Set what details are required in the schema - this affects the time taken to crawl the schema
		SchemaInfoLevel schemaInfoLevel = SchemaInfoLevelBuilder.standard();
		options.setSchemaInfoLevel(schemaInfoLevel);
		
		return options;
	}
	
	public static SchemaCrawlerOptions tablecolumns() {
		
		final SchemaCrawlerOptions options = new SchemaCrawlerOptions();
		
		// Set what details are required in the schema - this affects the time taken to crawl the schema
		SchemaInfoLevel schemaInfoLevel = SchemaInfoLevelBuilder.minimum();
		schemaInfoLevel.setRetrieveColumnDataTypes(true);
		schemaInfoLevel.setRetrieveTableColumns(true);
		schemaInfoLevel.setRetrieveRoutines(false);
		options.setSchemaInfoLevel(schemaInfoLevel);
		
		return options;
	}

	private SchemaCrawlerOptionBuilder() {
		// Prevent instantiation
	}

}