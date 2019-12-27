/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package schemacrawler.spring.boot;

import java.sql.Connection;

import schemacrawler.schema.*;
import schemacrawler.schemacrawler.RegularExpressionInclusionRule;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaCrawlerOptionsBuilder;
import schemacrawler.schemacrawler.SchemaInfoLevelBuilder;
import schemacrawler.tools.databaseconnector.DatabaseConnectionSource;
import schemacrawler.tools.databaseconnector.SingleUseUserCredentials;
import schemacrawler.utility.SchemaCrawlerUtility;

public final class ApiExample
{

  public static void main(final String[] args)
    throws Exception
  {

    // Create the options
    final SchemaCrawlerOptionsBuilder optionsBuilder = SchemaCrawlerOptionsBuilder
      .builder()
      // Set what details are required in the schema - this affects the
      // time taken to crawl the schema
      .withSchemaInfoLevel(SchemaInfoLevelBuilder.standard())
      .includeSchemas(new RegularExpressionInclusionRule("PUBLIC.BOOKS"))
      .includeTables(tableFullName -> !tableFullName.contains("ΒΙΒΛΊΑ"));
    final SchemaCrawlerOptions options = optionsBuilder.toOptions();

    // Get the schema definition
    final Catalog catalog = SchemaCrawlerUtility
      .getCatalog(getConnection(), options);

    for (final Schema schema : catalog.getSchemas())
    {
      System.out.println(schema);
      for (final Table table : catalog.getTables(schema))
      {
        System.out.print("o--> " + table);
        if (table instanceof View)
        {
          System.out.println(" (VIEW)");
        }
        else
        {
          System.out.println();
        }

        for (final Column column : table.getColumns())
        {
          System.out.println(
            "     o--> " + column + " (" + column.getColumnDataType() + ")");
        }
      }
    }

  }

  private static Connection getConnection()
  {
    final String connectionUrl = "jdbc:hsqldb:hsql://localhost:9001/schemacrawler";
    final DatabaseConnectionSource dataSource = new DatabaseConnectionSource(
      connectionUrl);
    dataSource.setUserCredentials(new SingleUseUserCredentials("sa", ""));
    return dataSource.get();
  }

}
