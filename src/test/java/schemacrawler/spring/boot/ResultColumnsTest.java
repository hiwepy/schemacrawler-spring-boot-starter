/*
========================================================================
SchemaCrawler
http://www.schemacrawler.com
Copyright (c) 2000-2018, Sualeh Fatehi <sualeh@hotmail.com>.
All rights reserved.
------------------------------------------------------------------------

SchemaCrawler is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

SchemaCrawler and the accompanying materials are made available under
the terms of the Eclipse Public License v1.0, GNU General Public License
v3 or GNU Lesser General Public License v3.

You may elect to redistribute this code under any of these licenses.

The Eclipse Public License is available at:
http://www.eclipse.org/legal/epl-v10.html

The GNU General Public License v3 and the GNU Lesser General Public
License v3 are available at:
http://www.gnu.org/licenses/

========================================================================
*/

package schemacrawler.spring.boot;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import schemacrawler.SchemaCrawlerLogger;
import schemacrawler.schema.ResultsColumn;
import schemacrawler.schema.ResultsColumns;
import schemacrawler.spring.boot.utility.TestName;
import schemacrawler.spring.boot.utility.TestWriter;
import schemacrawler.utility.SchemaCrawlerUtility;

public class ResultColumnsTest extends BaseDatabaseTest {

	private static final SchemaCrawlerLogger LOGGER = SchemaCrawlerLogger.getLogger(ResultColumnsTest.class.getName());

	@Rule
	public TestName testName = new TestName();
 
	@Before 
	public void setUp() throws Exception { 
		// Create a database connection
		super.setUp("jdbc:sqlserver://192.168.0.118:1433;DatabaseName=91118net;integratedSecurity=false");
	}
	
	@Test
	public void columns() throws Exception {

		try (final TestWriter out = new TestWriter("text");) {
			final String sql = "" + "SELECT                                                                    "
					+ " PUBLIC.BOOKS.BOOKS.TITLE AS BOOK,                                        "
					+ " PUBLIC.BOOKS.AUTHORS.FIRSTNAME + ' ' + PUBLIC.BOOKS.AUTHORS.LASTNAME,    "
					+ " PUBLIC.BOOKS.BOOKS.PRICE                                                 "
					+ "FROM                                                                      "
					+ " PUBLIC.BOOKS.BOOKS                                                       "
					+ " INNER JOIN PUBLIC.BOOKS.BOOKAUTHORS                                      "
					+ "   ON PUBLIC.BOOKS.BOOKS.ID = PUBLIC.BOOKS.BOOKAUTHORS.BOOKID             "
					+ " INNER JOIN PUBLIC.BOOKS.AUTHORS                                          "
					+ "   ON PUBLIC.BOOKS.AUTHORS.ID = PUBLIC.BOOKS.BOOKAUTHORS.AUTHORID         ";

			try (final Connection connection =  super.getConnection("sa", "sa");
					final Statement statement = connection.createStatement();
					final ResultSet resultSet = statement.executeQuery(sql);) {
				
				final ResultsColumns resultColumns = SchemaCrawlerUtility.getResultsColumns(resultSet);

				assertNotNull("Could not obtain result columns", resultColumns);
				final ResultsColumn[] columns = resultColumns.getColumns().toArray(new ResultsColumn[0]);
				for (final ResultsColumn column : columns) {
					LOGGER.log(Level.FINE, column.toString());
					out.println("column: " + column.getFullName());
					out.println("  database type: " + column.getColumnDataType().getDatabaseSpecificTypeName());
					out.println("  type: " + column.getColumnDataType().getJavaSqlType().getName());
				}
			}

			out.assertEquals(testName.currentMethodFullName());
		}
	}

}
