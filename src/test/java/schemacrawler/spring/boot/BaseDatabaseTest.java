package schemacrawler.spring.boot;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import javax.sql.DataSource;

import schemacrawler.schema.Catalog;
import schemacrawler.schemacrawler.DatabaseConnectionOptions;
import schemacrawler.schemacrawler.ExcludeAll;
import schemacrawler.schemacrawler.IncludeAll;
import schemacrawler.schemacrawler.RegularExpressionExclusionRule;
import schemacrawler.schemacrawler.RegularExpressionInclusionRule;
import schemacrawler.schemacrawler.RegularExpressionRule;
import schemacrawler.schemacrawler.SchemaCrawlerException;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaInfoLevel;
import schemacrawler.schemacrawler.SchemaInfoLevelBuilder;
import schemacrawler.utility.SchemaCrawlerUtility;

public abstract class BaseDatabaseTest {


	public SchemaCrawlerOptions getOptions() throws SchemaCrawlerException, SQLException {
		// Create the options
		final SchemaCrawlerOptions options = new SchemaCrawlerOptions();
		// Set what details are required in the schema - this affects the
		// time taken to crawl the schema

		SchemaInfoLevel level = SchemaInfoLevelBuilder.detailed();

		// level.setRetrieveTableColumns(false);

		options.setSchemaInfoLevel(level);
		options.setColumnInclusionRule(new IncludeAll());
		options.setRoutineInclusionRule(new ExcludeAll());
		options.setSchemaInclusionRule(new RegularExpressionInclusionRule("tablename"));
		options.setTableInclusionRule(new RegularExpressionExclusionRule(".*\\..{0,1}schema_version.*") );
		
		
		return options;
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
