package schemacrawler.spring.boot.utils;

import java.util.Arrays;
import java.util.List;

import schemacrawler.schemacrawler.IncludeAll;
import schemacrawler.schemacrawler.InclusionRule;
import schemacrawler.schemacrawler.SchemaCrawlerOptionsBuilder;
import schemacrawler.schemacrawler.SchemaInfoLevel;
import schemacrawler.schemacrawler.SchemaInfoLevelBuilder;
import schemacrawler.tools.options.InfoLevel;
/**
 * SchemaCrawler utility methods.
 * @author vindell
 */
public final class SchemaCrawlerOptionBuilder {

	public static SchemaCrawlerOptionsBuilder custom(SchemaInfoLevel schemaInfoLevel) {
		// Set what details are required in the schema - this affects the time taken to crawl the schema
		return SchemaCrawlerOptionsBuilder.builder().withSchemaInfoLevel(schemaInfoLevel);
	}

	public static SchemaCrawlerOptionsBuilder detailed() {
		// Set what details are required in the schema - this affects the time taken to crawl the schema
		SchemaInfoLevel schemaInfoLevel = InfoLevel.detailed.buildSchemaInfoLevel();
		return SchemaCrawlerOptionsBuilder.builder().withSchemaInfoLevel(schemaInfoLevel);
	}

	public static SchemaCrawlerOptionsBuilder maximum() {
		// Set what details are required in the schema - this affects the time taken to crawl the schema
		SchemaInfoLevel schemaInfoLevel = InfoLevel.maximum.buildSchemaInfoLevel();
		return SchemaCrawlerOptionsBuilder.builder().withSchemaInfoLevel(schemaInfoLevel);
	}

	public static SchemaCrawlerOptionsBuilder minimum() {
		// Set what details are required in the schema - this affects the time taken to crawl the schema
		SchemaInfoLevel schemaInfoLevel = InfoLevel.minimum.buildSchemaInfoLevel();
		return SchemaCrawlerOptionsBuilder.builder().withSchemaInfoLevel(schemaInfoLevel);
	}
	
	public static SchemaCrawlerOptionsBuilder standard() {
		// Set what details are required in the schema - this affects the time taken to crawl the schema
		SchemaInfoLevel schemaInfoLevel = InfoLevel.standard.buildSchemaInfoLevel();
		return SchemaCrawlerOptionsBuilder.builder().withSchemaInfoLevel(schemaInfoLevel);
	}
	
	/**
	 * Sets the schema inclusion rule.
	 * Sets table types requested for output from a collection of table types. For example: TABLE,VIEW,SYSTEM_TABLE,GLOBAL TEMPORARY,ALIAS,SYNONYM
	 * @author 		： <a href="https://github.com/vindell">vindell</a>
	 * @param schemaInclusionRule Schema inclusion rule
	 * @param tableTypes Collection of table types. Can be null if all supported table types are requested.
	 * @return
	 */
	public static SchemaCrawlerOptionsBuilder tablecolumns(InclusionRule schemaInclusionRule, String ... tableTypes ) {
		
		
		// Set what details are required in the schema - this affects the time taken to crawl the schema
		SchemaInfoLevelBuilder schemaInfoLevelBuilder = SchemaInfoLevelBuilder.minimum()
				.setRetrieveAdditionalColumnAttributes(true)
				.setRetrieveAdditionalTableAttributes(true)
				.setRetrieveColumnDataTypes(true)
				.setRetrieveIndexes(false)
				.setRetrieveRoutineColumns(true)
				.setRetrieveTableColumns(true)
				.setRetrieveUserDefinedColumnDataTypes(true)
				.setRetrieveViewInformation(true);
		
		List<String> tableTypeList = (tableTypes == null || tableTypes.length == 0) ? Arrays.asList("BASE TABLE", "TABLE", "VIEW") : Arrays.asList(tableTypes);
		
		return SchemaCrawlerOptionsBuilder.builder()
				.tableTypes(tableTypeList)
				.includeTables(new IncludeAll())
				.includeAllRoutines()
				.includeColumns(new IncludeAll())
				.includeSchemas(schemaInclusionRule)
				.withSchemaInfoLevel(schemaInfoLevelBuilder);
	}

	private SchemaCrawlerOptionBuilder() {
		// Prevent instantiation
	}

}