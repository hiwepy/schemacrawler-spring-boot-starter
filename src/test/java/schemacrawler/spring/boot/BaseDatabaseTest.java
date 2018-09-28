package schemacrawler.spring.boot;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import schemacrawler.schema.Catalog;
import schemacrawler.schemacrawler.DatabaseConnectionOptions;
import schemacrawler.schemacrawler.ExcludeAll;
import schemacrawler.schemacrawler.IncludeAll;
import schemacrawler.schemacrawler.RegularExpressionExclusionRule;
import schemacrawler.schemacrawler.RegularExpressionInclusionRule;
import schemacrawler.schemacrawler.SchemaCrawlerException;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaCrawlerOptionsBuilder;
import schemacrawler.schemacrawler.SchemaInfoLevel;
import schemacrawler.tools.options.InfoLevel;
import schemacrawler.utility.SchemaCrawlerUtility;

public abstract class BaseDatabaseTest {


	public static SchemaCrawlerOptionsBuilder getOptions() throws SchemaCrawlerException, SQLException {
		
		// Set what details are required in the schema - this affects the
		// time taken to crawl the schema

		SchemaInfoLevel schemaInfoLevel = InfoLevel.detailed.buildSchemaInfoLevel();

		// Create the options
		return SchemaCrawlerOptionsBuilder.builder()
				.includeColumns(new IncludeAll())
				.includeRoutines(new ExcludeAll())
				.includeSchemas(new RegularExpressionInclusionRule("tablename"))
				.includeTables(new RegularExpressionExclusionRule(".*\\..{0,1}schema_version.*"))
				.withSchemaInfoLevel(schemaInfoLevel);
	}
	
	public Catalog getCatalog(SchemaCrawlerOptions options) throws SchemaCrawlerException, SQLException {
		// Get the schema definition
		return SchemaCrawlerUtility.getCatalog(getConnection(), options);
	}

	public Connection getConnection() throws SchemaCrawlerException, SQLException {
		// Create a database connection
		final DataSource dataSource = new DatabaseConnectionOptions("jdbc:oracle:thin:@127.0.0.1:1521:orcl");
		final Connection connection = dataSource.getConnection("username", "password");
		return connection;
	}

}
