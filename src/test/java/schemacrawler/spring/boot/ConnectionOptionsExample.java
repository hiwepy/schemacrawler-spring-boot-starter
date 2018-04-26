package schemacrawler.spring.boot;

import java.sql.Connection;
import java.util.Arrays;
import java.util.Properties;

import javax.sql.DataSource;

import oracle.jdbc.OracleConnection;
import schemacrawler.crawl.MetadataRetrievalStrategy;
import schemacrawler.schema.Catalog;
import schemacrawler.schema.Column;
import schemacrawler.schema.Schema;
import schemacrawler.schema.Table;
import schemacrawler.schema.View;
import schemacrawler.schemacrawler.DatabaseConnectionOptions;
import schemacrawler.schemacrawler.ExcludeAll;
import schemacrawler.schemacrawler.IncludeAll;
import schemacrawler.schemacrawler.RegularExpressionInclusionRule;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaInfoLevel;
import schemacrawler.schemacrawler.SchemaInfoLevelBuilder;
import schemacrawler.spring.boot.ext.DatabaseType;
import schemacrawler.tools.databaseconnector.DatabaseConnectorRegistry;
import schemacrawler.utility.SchemaCrawlerUtility;

public final class ConnectionOptionsExample {

	public static void main(final String[] args) throws Exception {

		// Create a database connection
		final DataSource dataSource = new DatabaseConnectionOptions("jdbc:oracle:thin:@10.71.19.133:1521:orcl");
		final Connection connection = dataSource.getConnection("ZFSOFT_DXC", "ZFSOFT_DXC");

		// Create the options
		final SchemaCrawlerOptions options = new SchemaCrawlerOptions();
		// Set what details are required in the schema - this affects the
		// time taken to crawl the schema

		SchemaInfoLevel level = SchemaInfoLevelBuilder.detailed();
		level.setRetrieveAdditionalColumnAttributes(true);
		level.setRetrieveAdditionalTableAttributes(true);
		
		// level.setRetrieveTableColumns(false);
		options.setTableTypes(Arrays.asList("TABLE","VIEW"));
		options.setSchemaInfoLevel(level);
		options.setColumnInclusionRule(new IncludeAll());
		options.setRoutineInclusionRule(new ExcludeAll());
		options.setSchemaInclusionRule(new RegularExpressionInclusionRule("ZFSOFT_DXC"));

		// https://blog.csdn.net/hao7030187/article/details/56480735
		//设置可以读取REMARKS
		if (connection instanceof OracleConnection) {//设置Oracle数据库的表注释可读
		    ((OracleConnection) connection).setRemarksReporting(true);//设置连接属性,使得可获取到表的REMARK(备注)
		}
		
		// Get the schema definition
		final Catalog catalog = SchemaCrawlerUtility.getCatalog(connection, options);

		for (final Schema schema : catalog.getSchemas()) {
			System.out.println(schema);

			for (final Table table : catalog.getTables(schema)) {
				System.out.print("o--> " + table.getName());
				if (table instanceof View) {
					System.out.println(" (VIEW)");
				} else {
					System.out.println();
				}
				System.out.println(String.format("  Attrs [%s]",table.getAttributes()));
				System.out.println(table.getSchema().getRemarks());
				System.out.println(String.format("  %s [%s] [%s]", table.getName(), table.getTableType(), table.getRemarks()));
				final Column[] columns = table.getColumns().toArray(new Column[0]);
				Arrays.sort(columns);
				for (final Column column : columns) {
					System.out.println("     o--> " + column.getName() + " (" + column.getColumnDataType() + ") ："
							+ column.getRemarks());
				}

			}
		}

	}

}