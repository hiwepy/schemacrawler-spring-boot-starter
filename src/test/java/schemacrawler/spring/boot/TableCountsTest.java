/*
========================================================================
SchemaCrawler
http://www.schemacrawler.com
Copyright (c) 2000-2016, Sualeh Fatehi <sualeh@hotmail.com>.
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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import schemacrawler.inclusionrule.IncludeAll;
import schemacrawler.inclusionrule.InclusionRule;
import schemacrawler.inclusionrule.RegularExpressionInclusionRule;
import schemacrawler.schema.Catalog;
import schemacrawler.schema.Schema;
import schemacrawler.schema.Table;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.spring.boot.utility.TestName;
import schemacrawler.spring.boot.utility.TestWriter;
import schemacrawler.spring.boot.utils.SchemaCrawlerOptionBuilder;
import schemacrawler.utility.NamedObjectSort;

public class TableCountsTest extends BaseDatabaseTest {

	@Rule
	public TestName testName = new TestName();

	@Before
	public void setUp() throws Exception {
		// Create a database connection
		super.setUp("jdbc:sqlserver://192.168.0.118:1433;DatabaseName=91118net;integratedSecurity=false");
	}

	@Test
	public void tableCounts() throws Exception {
		try (final TestWriter out = new TestWriter("text");) {

			final InclusionRule schemaInclusionRule = new RegularExpressionInclusionRule("91118net");
			final SchemaCrawlerOptions options = SchemaCrawlerOptionBuilder
					.tablecolumns(new IncludeAll(), "TABLE", "VIEW");
			final Catalog baseCatalog = super.getCatalog("sa", "sa", options);

			final SchemaCrawlerOptions schemaCrawlerOptions = SchemaCrawlerOptionBuilder.maximum();
			final Schema[] schemas = baseCatalog.getSchemas().toArray(new Schema[0]);
			assertEquals("Schema count does not match", 5, schemas.length);
			for (final Schema schema : schemas) {
				out.println("schema: " + schema.getFullName());
				final Table[] tables = baseCatalog.getTables(schema).toArray(new Table[0]);
				Arrays.sort(tables, NamedObjectSort.alphabetical);
				for (final Table table : tables) {
					out.println("  table: " + table.getFullName());
					//final long count = SchemaCrawlerUtility.getRowCount(table);
					//out.println(String.format("    row count: %d", count));
				}
			}

			out.assertEquals(testName.currentMethodFullName());
		}
	}

}