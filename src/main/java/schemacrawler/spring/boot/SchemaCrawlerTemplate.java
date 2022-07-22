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
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import schemacrawler.crawl.SchemaCrawler;
import schemacrawler.inclusionrule.IncludeAll;
import schemacrawler.inclusionrule.InclusionRule;
import schemacrawler.schema.Catalog;
import schemacrawler.schema.RoutineType;
import schemacrawler.schemacrawler.LimitOptionsBuilder;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaInfoLevel;
import schemacrawler.schemacrawler.SchemaRetrievalOptions;
import schemacrawler.schemacrawler.SchemaRetrievalOptionsBuilder;
import schemacrawler.schemacrawler.exceptions.SchemaCrawlerException;
import schemacrawler.spring.boot.ext.ConnectionProvider;
import schemacrawler.spring.boot.ext.DatabaseSchemaCrawlerOptions;
import schemacrawler.spring.boot.utils.SchemaCrawlerOptionBuilder;
import schemacrawler.tools.utility.SchemaCrawlerUtility;
import us.fatehi.utility.ObjectToString;
import us.fatehi.utility.database.DatabaseUtility;

public class SchemaCrawlerTemplate {

	private static final Logger LOGGER = LoggerFactory.getLogger(SchemaCrawlerTemplate.class);

	@Autowired
	private SchemaCrawlerProperties properties;

	/**
	 * @param dbType The Database type.
	 * @return The SchemaCrawlerOptions {@link SchemaCrawlerOptions} Object
	 */
	public SchemaCrawlerOptions getCrawlerOptions(String dbType) {
		Iterator<DatabaseSchemaCrawlerOptions> ite = properties.getCrawlerOptions().iterator();
		while (ite.hasNext()) {
			DatabaseSchemaCrawlerOptions crawlerOptions = ite.next();
			if(crawlerOptions.getType().equals(dbType)) {
				return crawlerOptions.getOptions();
			}
		}
		return SchemaCrawlerOptionBuilder.standard();
	}

	/**
	 *
	 * Starts the schema crawler and lets it crawl the given DataSource.
	 *
	 * @param dataSource The DataSource
	 * @param dbType The Database type.
	 * @return The populated {@link Catalog} object containing the metadata for the extractor
	 * @throws SchemaCrawlerException Gets thrown when the database could not be crawled successfully
	 * @throws SQLException Gets thrown when the database access error occurs
	 */
	public Catalog crawl(final DataSource dataSource, String dbType) throws SchemaCrawlerException, SQLException {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			return this.crawl(dataSource.getConnection(), getCrawlerOptions(dbType));
		} finally {
			if(connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}

	/**
	 *
	 * Starts the schema crawler and lets it crawl the given DataSource.
	 *
	 * @param dataSource The DataSource
	 * @param schemaInfoLevel The {@link SchemaInfoLevel} to be Descriptor for level of schema detail.
	 * @return The populated {@link Catalog} object containing the metadata for the extractor
	 * @throws SchemaCrawlerException Gets thrown when the database could not be crawled successfully
	 * @throws SQLException Gets thrown when the database access error occurs
	 */
	public Catalog crawl(final DataSource dataSource, final SchemaInfoLevel schemaInfoLevel) throws SchemaCrawlerException, SQLException {

		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			return this.crawl(dataSource.getConnection(), schemaInfoLevel);
		} finally {
			if(connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}

	/**
	 *
	 * Starts the schema crawler and lets it crawl the given DataSource.
	 *
	 * @param dataSource The DataSource
	 * @param schemaRule The {@link InclusionRule} to be passed to SchemaCrawler that specifies which schemas should be analyzed
	 * @param tableRule  The {@link InclusionRule} to be passed to SchemaCrawler that specifies which tables should be analyzed. If a table is included by the
	 *                   {@code tableRule} but excluded by the {@code schemaRule} it will not be analyzed.
	 * @return The populated {@link Catalog} object containing the metadata for the extractor
	 * @throws SchemaCrawlerException Gets thrown when the database could not be crawled successfully
	 * @throws SQLException Gets thrown when the database access error occurs
	 */
	public Catalog crawl(final DataSource dataSource, final InclusionRule schemaRule, final InclusionRule tableRule) throws SchemaCrawlerException, SQLException {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			return this.crawl(dataSource.getConnection(), schemaRule, tableRule);
		} finally {
			if(connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}

	/**
	 *
	 * Starts the schema crawler and lets it crawl the given JDBC connection.
	 *
	 * @param connection The JDBC connection
	 * @param dbType The Database type.
	 * @return The populated {@link Catalog} object containing the metadata for the extractor
	 * @throws SchemaCrawlerException Gets thrown when the database could not be crawled successfully
	 */
	public Catalog crawl(final Connection connection, String dbType) throws SchemaCrawlerException {
		try {
	    	final SchemaCrawlerOptions options = getCrawlerOptions(dbType);
	        return SchemaCrawlerUtility.getCatalog(connection, options);
	    } catch (SchemaCrawlerException e) {
	        LOGGER.error("Schema crawling failed with exception", e);
	        throw e;
	    }
	}

	/**
	 *
	 * Starts the schema crawler and lets it crawl the given JDBC connection.
	 *
	 * @param connection The JDBC connection
	 * @param schemaRetrievalOptions Database-specific schema retrieval overrides
     * @param schemaCrawlerOptions SchemaCrawler options
	 * @return The populated {@link Catalog} object containing the metadata for the extractor
	 * @throws SchemaCrawlerException Gets thrown when the database could not be crawled successfully
	 * @throws SQLException Gets thrown when the database could not be crawled successfully
	 */
	public Catalog crawl(final Connection connection,
            final SchemaRetrievalOptions schemaRetrievalOptions,
            final SchemaCrawlerOptions schemaCrawlerOptions) throws SchemaCrawlerException, SQLException {
		try {

			DatabaseUtility.checkConnection(connection);
		    if (LOGGER.isDebugEnabled()) {
		    	LOGGER.debug(ObjectToString.toString(schemaCrawlerOptions));
		    }

			final SchemaCrawler schemaCrawler = new SchemaCrawler(connection, schemaRetrievalOptions, schemaCrawlerOptions);
			final Catalog catalog = schemaCrawler.crawl();
			return catalog;
	    } catch (SchemaCrawlerException e) {
	        LOGGER.error("Schema crawling failed with exception", e);
	        throw e;
	    }
	}

	/**
	 *
	 * Starts the schema crawler and lets it crawl the given JDBC connection.
	 *
	 * @param connection The JDBC connection
	 * @param schemaCrawlerOptions The {@link SchemaCrawlerOptions} Options.
	 * @return The populated {@link Catalog} object containing the metadata for the extractor
	 * @throws SchemaCrawlerException Gets thrown when the database could not be crawled successfully
	 * @throws SQLException Gets thrown when the database could not be crawled successfully
	 */
	public Catalog crawl(final Connection connection, final SchemaCrawlerOptions schemaCrawlerOptions) throws SchemaCrawlerException, SQLException {
		return crawl(connection, SchemaRetrievalOptionsBuilder.newSchemaRetrievalOptions(), schemaCrawlerOptions);
	}

	/**
	 *
	 * Starts the schema crawler and lets it crawl the given JDBC connection.
	 *
	 * @param connection The JDBC connection
	 * @param schemaInfoLevel The {@link SchemaInfoLevel} to be Descriptor for level of schema detail.
	 * @return The populated {@link Catalog} object containing the metadata for the extractor
	 * @throws SchemaCrawlerException Gets thrown when the database access error occurs
	 */
	public Catalog crawl(final Connection connection, final SchemaInfoLevel schemaInfoLevel) throws SchemaCrawlerException {
	    try {
	    	final SchemaCrawlerOptions options = SchemaCrawlerOptionBuilder.custom(schemaInfoLevel);
	        return SchemaCrawlerUtility.getCatalog(connection, options);
	    } catch (SchemaCrawlerException e) {
	        LOGGER.error("Schema crawling failed with exception", e);
	        throw e;
	    }
	}

	/**
	 *
	 * Starts the schema crawler and lets it crawl the given JDBC connection.
	 *
	 * @param connection The JDBC connection
	 * @param schemaRule The {@link InclusionRule} to be passed to SchemaCrawler that specifies which schemas should be analyzed
	 * @param tableRule  The {@link InclusionRule} to be passed to SchemaCrawler that specifies which tables should be analyzed. If a table is included by the
	 *                   {@code tableRule} but excluded by the {@code schemaRule} it will not be analyzed.
	 * @return The populated {@link Catalog} object containing the metadata for the extractor
	 * @throws SchemaCrawlerException Gets thrown when the database could not be crawled successfully
	 */
	public Catalog crawl(final Connection connection, final InclusionRule schemaRule, final InclusionRule tableRule) throws SchemaCrawlerException {

		final LimitOptionsBuilder limitOptionsBuilder = LimitOptionsBuilder.builder()
				// Set what details are required in the schema - this affects the
				.routineTypes(Arrays.asList(RoutineType.procedure, RoutineType.unknown)) // RoutineType.function not supported by h2
				.includeSchemas(schemaRule == null ? new IncludeAll() : schemaRule)
				.includeTables(tableRule == null ? new IncludeAll() : tableRule);

		final SchemaCrawlerOptions options = SchemaCrawlerOptionBuilder.standard()
				.withLimitOptions( limitOptionsBuilder.toOptions());

	    try {
	        return SchemaCrawlerUtility.getCatalog(connection, options);
	    } catch (SchemaCrawlerException e) {
	        LOGGER.error("Schema crawling failed with exception", e);
	        throw e;
	    }
	}

	/**
	 *
	 * Starts the schema crawler and lets it crawl the given JDBC connection.
	 *
	 * @param connectionProvider The JDBC connection Provider
	 * @param schemaInfoLevel The {@link SchemaInfoLevel} to be Descriptor for level of schema detail.
	 * @return The populated {@link Catalog} object containing the metadata for the extractor
	 * @throws SchemaCrawlerException Gets thrown when the database could not be crawled successfully
	 * @throws SQLException Gets thrown when the database access error occurs
	 */
	public Catalog crawl(final ConnectionProvider connectionProvider, final SchemaInfoLevel schemaInfoLevel) throws SchemaCrawlerException, SQLException {
	    try {
	    	final SchemaCrawlerOptions options = SchemaCrawlerOptionBuilder.custom(schemaInfoLevel);
	        return SchemaCrawlerUtility.getCatalog(connectionProvider.getConnection(), options);
	    } catch (SchemaCrawlerException e) {
	        LOGGER.error("Schema crawling failed with exception", e);
	        throw e;
	    }
	}

	/**
	 *
	 * Starts the schema crawler and lets it crawl the given JDBC connection.
	 *
	 * @param connectionProvider The JDBC connection Provider
	 * @param schemaRule The {@link InclusionRule} to be passed to SchemaCrawler that specifies which schemas should be analyzed
	 * @param tableRule  The {@link InclusionRule} to be passed to SchemaCrawler that specifies which tables should be analyzed. If a table is included by the
	 *                   {@code tableRule} but excluded by the {@code schemaRule} it will not be analyzed.
	 * @return The populated {@link Catalog} object containing the metadata for the extractor
	 * @throws SchemaCrawlerException Gets thrown when the database could not be crawled successfully
	 * @throws SQLException Gets thrown when the database access error occurs
	 */
	public Catalog crawl(final ConnectionProvider connectionProvider, final InclusionRule schemaRule, final InclusionRule tableRule) throws SchemaCrawlerException, SQLException {

		final LimitOptionsBuilder limitOptionsBuilder = LimitOptionsBuilder.builder()
				// Set what details are required in the schema - this affects the
				.routineTypes(Arrays.asList(RoutineType.procedure, RoutineType.unknown)) // RoutineType.function not supported by h2
				.includeSchemas(schemaRule == null ? new IncludeAll() : schemaRule)
				.includeTables(tableRule == null ? new IncludeAll() : tableRule);

		final SchemaCrawlerOptions options = SchemaCrawlerOptionBuilder.standard()
				.withLimitOptions( limitOptionsBuilder.toOptions());

	    try {
	        return SchemaCrawlerUtility.getCatalog(connectionProvider.getConnection(), options);
	    } catch (SchemaCrawlerException e) {
	        LOGGER.error("Schema crawling failed with exception", e);
	        throw e;
	    }
	}

}
