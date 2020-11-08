package schemacrawler.spring.boot.utils;

import java.util.Arrays;
import java.util.List;

import schemacrawler.inclusionrule.IncludeAll;
import schemacrawler.inclusionrule.InclusionRule;
import schemacrawler.schemacrawler.FilterOptionsBuilder;
import schemacrawler.schemacrawler.GrepOptionsBuilder;
import schemacrawler.schemacrawler.LimitOptionsBuilder;
import schemacrawler.schemacrawler.LoadOptions;
import schemacrawler.schemacrawler.LoadOptionsBuilder;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaCrawlerOptionsBuilder;
import schemacrawler.schemacrawler.SchemaInfoLevel;
import schemacrawler.schemacrawler.SchemaInfoLevelBuilder;
/**
 * SchemaCrawler utility methods.
 * @author hiwepy
 */
public final class SchemaCrawlerOptionBuilder {

	public static SchemaCrawlerOptions custom(SchemaInfoLevel schemaInfoLevel) {
		// Set what details are required in the schema - this affects the time taken to crawl the schema
		LoadOptions loadOptions = LoadOptionsBuilder.builder().withSchemaInfoLevel(schemaInfoLevel).toOptions();
		return SchemaCrawlerOptionsBuilder
				.newSchemaCrawlerOptions()
				.withFilterOptions(FilterOptionsBuilder.newFilterOptions())
				.withGrepOptions(GrepOptionsBuilder.newGrepOptions()) 
				.withLimitOptions( LimitOptionsBuilder.newLimitOptions())
				.withLoadOptions(loadOptions);
	}

	public static SchemaCrawlerOptions detailed() {
		// Set what details are required in the schema - this affects the time taken to crawl the schema
		LoadOptions loadOptions = LoadOptionsBuilder.builder().withSchemaInfoLevel(SchemaInfoLevelBuilder.detailed()).toOptions();
		return SchemaCrawlerOptionsBuilder
				.newSchemaCrawlerOptions()
				.withFilterOptions(FilterOptionsBuilder.newFilterOptions())
				.withGrepOptions(GrepOptionsBuilder.newGrepOptions()) 
				.withLimitOptions( LimitOptionsBuilder.newLimitOptions())
				.withLoadOptions(loadOptions);
	}

	public static SchemaCrawlerOptions maximum() {
		// Set what details are required in the schema - this affects the time taken to crawl the schema
		LoadOptions loadOptions = LoadOptionsBuilder.builder().withSchemaInfoLevel(SchemaInfoLevelBuilder.maximum()).toOptions();
		return SchemaCrawlerOptionsBuilder
				.newSchemaCrawlerOptions()
				.withFilterOptions(FilterOptionsBuilder.newFilterOptions())
				.withGrepOptions(GrepOptionsBuilder.newGrepOptions()) 
				.withLimitOptions( LimitOptionsBuilder.newLimitOptions())
				.withLoadOptions(loadOptions);
	}

	public static SchemaCrawlerOptions minimum() {
		// Set what details are required in the schema - this affects the time taken to crawl the schema
		LoadOptions loadOptions = LoadOptionsBuilder.builder().withSchemaInfoLevel(SchemaInfoLevelBuilder.minimum()).toOptions();
		return SchemaCrawlerOptionsBuilder
				.newSchemaCrawlerOptions()
				.withFilterOptions(FilterOptionsBuilder.newFilterOptions())
				.withGrepOptions(GrepOptionsBuilder.newGrepOptions()) 
				.withLimitOptions( LimitOptionsBuilder.newLimitOptions())
				.withLoadOptions(loadOptions);
	}
	
	public static SchemaCrawlerOptions standard() {
		// Set what details are required in the schema - this affects the time taken to crawl the schema
		LoadOptions loadOptions = LoadOptionsBuilder.builder().withSchemaInfoLevel(SchemaInfoLevelBuilder.standard()).toOptions();
		return SchemaCrawlerOptionsBuilder
				.newSchemaCrawlerOptions()
				.withFilterOptions(FilterOptionsBuilder.newFilterOptions())
				.withGrepOptions(GrepOptionsBuilder.newGrepOptions()) 
				.withLimitOptions( LimitOptionsBuilder.newLimitOptions())
				.withLoadOptions(loadOptions);
	}
	
	/**
	 * Sets the schema inclusion rule.
	 * Sets table types requested for output from a collection of table types. For example: TABLE,VIEW,SYSTEM_TABLE,GLOBAL TEMPORARY,ALIAS,SYNONYM
	 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
	 * @param schemaInclusionRule Schema inclusion rule
	 * @param tableTypes Collection of table types. Can be null if all supported table types are requested.
	 * @return {@link SchemaCrawlerOptionsBuilder} instance
	 */
	public static SchemaCrawlerOptions tablecolumns(InclusionRule schemaInclusionRule, String ... tableTypes ) {
		
		// Set what details are required in the schema - this affects the time taken to crawl the schema
		SchemaInfoLevelBuilder schemaInfoLevelBuilder = SchemaInfoLevelBuilder.builder()
				.setRetrieveAdditionalColumnAttributes(true)
				.setRetrieveAdditionalTableAttributes(true)
				.setRetrieveColumnDataTypes(true)
				.setRetrieveIndexes(false)
				.setRetrieveRoutines(true)
				.setRetrieveTableColumns(true)
				.setRetrieveUserDefinedColumnDataTypes(true)
				.setRetrieveViewInformation(true);
		
		List<String> tableTypeList = (tableTypes == null || tableTypes.length == 0) ? Arrays.asList("BASE TABLE", "TABLE", "VIEW") : Arrays.asList(tableTypes);
		
		final LimitOptionsBuilder limitOptionsBuilder = LimitOptionsBuilder.builder()
				// Set what details are required in the schema - this affects the
				.tableTypes(tableTypeList)
				.includeTables(new IncludeAll())
				.includeAllRoutines()
				.includeColumns(new IncludeAll())
				.includeSchemas(schemaInclusionRule);

		LoadOptions loadOptions = LoadOptionsBuilder.builder().withSchemaInfoLevelBuilder(schemaInfoLevelBuilder).toOptions();
		
		return SchemaCrawlerOptionsBuilder
				.newSchemaCrawlerOptions()
				.withFilterOptions(FilterOptionsBuilder.newFilterOptions())
				.withGrepOptions(GrepOptionsBuilder.newGrepOptions()) 
				.withLimitOptions( limitOptionsBuilder.toOptions())
				.withLoadOptions(loadOptions);
		
	}

	private SchemaCrawlerOptionBuilder() {
		// Prevent instantiation
	}

}