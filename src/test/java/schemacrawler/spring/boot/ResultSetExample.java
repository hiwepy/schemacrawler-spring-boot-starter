/*
 * Copyright (c) 2018, vindell (https://github.com/vindell).
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
import java.sql.ResultSet;
import java.sql.Statement;

import schemacrawler.schema.ResultsColumn;
import schemacrawler.schema.ResultsColumns;
import schemacrawler.tools.databaseconnector.DatabaseConnectionSource;
import schemacrawler.tools.databaseconnector.SingleUseUserCredentials;
import schemacrawler.utility.SchemaCrawlerUtility;

public final class ResultSetExample
{

  public static void main(final String[] args)
    throws Exception
  {

    final String query =
      "SELECT                                                      \n"
      + "  BOOKS.TITLE AS BOOKTITLE,                               \n"
      + "  AUTHORS.FIRSTNAME + ' ' + AUTHORS.FIRSTNAME AS AUTHOR   \n"
      + "FROM                                                      \n"
      + "  PUBLIC.BOOKS.BOOKS AS BOOKS                             \n"
      + "  INNER JOIN PUBLIC.BOOKS.BOOKAUTHORS AS BOOKAUTHORS      \n"
      + "    ON BOOKS.ID = BOOKAUTHORS.BOOKID                      \n"
      + "  INNER JOIN PUBLIC.BOOKS.AUTHORS AS AUTHORS              \n"
      + "    ON BOOKAUTHORS.AUTHORID = AUTHORS.ID                  \n";
    try (final Connection connection = getConnection();
      final Statement statement = connection.createStatement();
      final ResultSet results = statement.executeQuery(query))
    {
      // Get result set metadata
      final ResultsColumns resultColumns = SchemaCrawlerUtility
        .getResultsColumns(results);
      for (final ResultsColumn column : resultColumns)
      {
        System.out.println("o--> " + column);
        System.out.println("     - label:     " + column.getLabel());
        System.out.println("     - data-type: " + column.getColumnDataType());
        System.out.println("     - table:     " + column.getParent());
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
