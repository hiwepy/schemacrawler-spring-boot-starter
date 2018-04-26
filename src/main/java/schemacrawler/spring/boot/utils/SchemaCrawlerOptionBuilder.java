package schemacrawler.spring.boot.utils;

import java.util.Arrays;
import java.util.List;

import schemacrawler.schemacrawler.IncludeAll;
import schemacrawler.schemacrawler.InclusionRule;
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
	
	/**
	 * Sets the schema inclusion rule.
	 * Sets table types requested for output from a collection of table types. For example: TABLE,VIEW,SYSTEM_TABLE,GLOBAL TEMPORARY,ALIAS,SYNONYM
	 * @author 		： <a href="https://github.com/vindell">vindell</a>
	 * @param schemaInclusionRule Schema inclusion rule
	 * @param tableTypes Collection of table types. Can be null if all supported table types are requested.
	 * @return
	 */
	public static SchemaCrawlerOptions tablecolumns(InclusionRule schemaInclusionRule, String ... tableTypes ) {
		
		final SchemaCrawlerOptions options = new SchemaCrawlerOptions();
		
		// Set what details are required in the schema - this affects the time taken to crawl the schema
		SchemaInfoLevel schemaInfoLevel = SchemaInfoLevelBuilder.minimum();
		schemaInfoLevel.setRetrieveAdditionalColumnAttributes(true);
		schemaInfoLevel.setRetrieveAdditionalTableAttributes(true);
		schemaInfoLevel.setRetrieveColumnDataTypes(true);
		schemaInfoLevel.setRetrieveIndexes(false);
		schemaInfoLevel.setRetrieveRoutineColumns(true);
		schemaInfoLevel.setRetrieveTableColumns(true);
		schemaInfoLevel.setRetrieveUserDefinedColumnDataTypes(true);
		schemaInfoLevel.setRetrieveViewInformation(true);
		
		options.setColumnInclusionRule(new IncludeAll());
		options.setRoutineInclusionRule(new IncludeAll());
		options.setSchemaInclusionRule(schemaInclusionRule);
		options.setSchemaInfoLevel(schemaInfoLevel);
		
		List<String> tableTypeList = (tableTypes == null || tableTypes.length == 0) ? Arrays.asList("BASE TABLE", "TABLE", "VIEW") : Arrays.asList(tableTypes);
		
		options.setTableTypes(tableTypeList);
		options.setTableInclusionRule(new IncludeAll());
		
		return options;
	}

	private SchemaCrawlerOptionBuilder() {
		// Prevent instantiation
	}

}