package schemacrawler.spring.boot;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import schemacrawler.schema.Catalog;
import schemacrawler.schema.Schema;
import schemacrawler.schema.Table;
import schemacrawler.schemacrawler.IncludeAll;
import schemacrawler.schemacrawler.SchemaCrawlerException;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaCrawlerOptionsBuilder;
import schemacrawler.schemacrawler.SchemaInfoLevelBuilder;
import schemacrawler.utility.SchemaCrawlerUtility;

public class CatalogTest {
	
	public Catalog getCatalog(final Connection connection) throws SQLException, SchemaCrawlerException {
	
		final SchemaCrawlerOptions options = SchemaCrawlerOptionsBuilder.builder()
				.includeTables(new IncludeAll())
				.tableNamePattern("*")
				.withSchemaInfoLevel(SchemaInfoLevelBuilder.standard())
				.toOptions();

		final Catalog catalog = SchemaCrawlerUtility.getCatalog(connection, options);

		for (final Schema schema : catalog.getSchemas()) {
			Collection<Table> tables = catalog.getTables(schema);
			//
			// The size of tables is always 0
			//
			System.out.println(tables);
		}

		return catalog;

	}

}
