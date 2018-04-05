package schemacrawler.spring.boot;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import schemacrawler.schema.Catalog;
import schemacrawler.schema.Column;
import schemacrawler.schema.Schema;
import schemacrawler.schema.Table;
import schemacrawler.schemacrawler.InclusionRule;
import schemacrawler.schemacrawler.SchemaCrawlerException;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaInfoLevelBuilder;
import schemacrawler.utility.SchemaCrawlerUtility;

public class MetaDataReader {
	
	static void extractTables(Connection conn, final String schemaName) throws SchemaCrawlerException, SQLException {

        final SchemaCrawlerOptions options = new SchemaCrawlerOptions();
        options.setSchemaInfoLevel(SchemaInfoLevelBuilder.standard());
        System.out.println("my catalog =" + conn.getCatalog());
        options.setSchemaInclusionRule(new InclusionRule() {
            @Override public boolean test(String anObject) {
                return schemaName.equals(anObject);
            }
        });

        final Catalog catalog = SchemaCrawlerUtility.getCatalog(conn, options);
        System.out.println("schem ! ");


        final Optional<? extends Schema> schema = catalog.lookupSchema(schemaName);
        System.out.println("Schema: " + schema);

        for (final Table table : catalog.getTables()) {
            String tableName = table.getName();

            System.out.println(table + " pk " + table.getPrimaryKey() + " fks " + table.getForeignKeys() + " type " + table.getTableType());
            if (/*rules.skipTable(tableName) ||*/ table.getTableType().isView()) {
                System.out.println("SKIPPED");
                continue;
            }
            List<Column> columns = table.getColumns();
            List<String> fields = new ArrayList<>(columns.size());
            for (final Column column : columns) {
//                    System.out.println("     o--> " + column + " pk: "+ column.isPartOfPrimaryKey() + " fk: " + column.isPartOfForeignKey());
                String columnName = column.getName();
                if (column.isPartOfPrimaryKey() /*&& rules.skipPrimaryKey(tableName, columnName)*/) {
                    // skip, todo strategy
                } else if (column.isPartOfForeignKey()) {
                    // skip, todo strategy
                } else {
                    fields.add(columnName);
                }
            }
        }

    }
}
