package schemacrawler.spring.boot;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import schemacrawler.schema.Catalog;
import schemacrawler.schemacrawler.IncludeAll;
import schemacrawler.schemacrawler.InclusionRule;
import schemacrawler.schemacrawler.SchemaCrawlerException;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.spring.boot.utils.SchemaCrawlerOptionBuilder;
import schemacrawler.tools.commandline.command.DatabaseUrlConnectionOptions;
import schemacrawler.utility.SchemaCrawlerUtility;

public abstract class BaseDatabaseTest {

	protected DataSource dataSource;

	protected void setUp(String connectionUrl) throws Exception {
		// Create a DataSource
		dataSource = new DatabaseUrlConnectionOptions(connectionUrl);
	}

	protected Connection getConnection(String username, String password) throws SchemaCrawlerException, SQLException {
		// Create a database connection
		final Connection connection = dataSource.getConnection(username, password);
		return connection;
	}

	protected Catalog getCatalog(String username, String password, InclusionRule schemaInclusionRule) throws Exception {

		final SchemaCrawlerOptions options = SchemaCrawlerOptionBuilder.tablecolumns(new IncludeAll(), "TABLE", "VIEW")
				.toOptions();

		// Get the schema definition
		final Catalog catalog = SchemaCrawlerUtility.getCatalog(getConnection(username, password), options);
		return catalog;
	}

	protected Catalog getCatalog(String username, String password, SchemaCrawlerOptions options)
			throws SchemaCrawlerException, SQLException {
		// Get the schema definition
		return SchemaCrawlerUtility.getCatalog(getConnection(username, password), options);
	}

}
