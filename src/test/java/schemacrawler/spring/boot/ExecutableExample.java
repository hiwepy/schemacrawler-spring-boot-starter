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

import static sf.util.Utility.isBlank;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;

import schemacrawler.inclusionrule.RegularExpressionInclusionRule;
import schemacrawler.schemacrawler.LimitOptionsBuilder;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.spring.boot.utils.SchemaCrawlerOptionBuilder;
import schemacrawler.tools.databaseconnector.DatabaseConnectionSource;
import schemacrawler.tools.databaseconnector.SingleUseUserCredentials;
import schemacrawler.tools.executable.SchemaCrawlerExecutable;
import schemacrawler.tools.options.OutputOptions;
import schemacrawler.tools.options.OutputOptionsBuilder;
import schemacrawler.tools.options.TextOutputFormat;

public final class ExecutableExample {

	public static void main(final String[] args) throws Exception {

		
		final LimitOptionsBuilder limitOptionsBuilder = LimitOptionsBuilder.builder()
				// Set what details are required in the schema - this affects the
				.includeSchemas(new RegularExpressionInclusionRule("PUBLIC.BOOKS"));

		// Create the options
		final SchemaCrawlerOptions options = SchemaCrawlerOptionBuilder
				.standard()
				.withLimitOptionsBuilder(limitOptionsBuilder)
				.toOptions();

		final Path outputFile = getOutputFile(args);
		final OutputOptions outputOptions = OutputOptionsBuilder.newOutputOptions(TextOutputFormat.html, outputFile);
		final String command = "schema";

		final SchemaCrawlerExecutable executable = new SchemaCrawlerExecutable(command);
		executable.setSchemaCrawlerOptions(options);
		executable.setOutputOptions(outputOptions);
		executable.setConnection(getConnection());
		executable.execute();

		System.out.println("Created output file, " + outputFile);
	}

	private static Connection getConnection() {
		final String connectionUrl = "jdbc:hsqldb:hsql://localhost:9001/schemacrawler";
		final DatabaseConnectionSource dataSource = new DatabaseConnectionSource(connectionUrl);
		dataSource.setUserCredentials(new SingleUseUserCredentials("sa", ""));
		return dataSource.get();
	}

	private static Path getOutputFile(final String[] args) {
		final String outputfile;
		if (args != null && args.length > 0 && !isBlank(args[0])) {
			outputfile = args[0];
		} else {
			outputfile = "./schemacrawler_output.html";
		}
		final Path outputFile = Paths.get(outputfile).toAbsolutePath().normalize();
		return outputFile;
	}

}
