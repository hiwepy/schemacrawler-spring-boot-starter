/*
 * Copyright (c) 2017, vindell (https://github.com/vindell).
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
import schemacrawler.schema.Catalog;
import schemacrawler.schema.RoutineType;
import schemacrawler.schemacrawler.IncludeAll;
import schemacrawler.schemacrawler.InclusionRule;
import schemacrawler.schemacrawler.SchemaCrawlerException;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaCrawlerSQLException;
import schemacrawler.schemacrawler.SchemaInfoLevel;
import schemacrawler.schemacrawler.SchemaRetrievalOptions;
import schemacrawler.schemacrawler.SchemaRetrievalOptionsBuilder;
import schemacrawler.spring.boot.ext.ConnectionProvider;
import schemacrawler.spring.boot.ext.DatabaseSchemaCrawlerOptions;
import schemacrawler.spring.boot.ext.DatabaseType;
import schemacrawler.spring.boot.utils.SchemaCrawlerOptionBuilder;
import schemacrawler.utility.SchemaCrawlerUtility;
import sf.util.DatabaseUtility;
import sf.util.ObjectToString;

public class SchemaCrawlerTemplate {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SchemaCrawlerTemplate.class);
	
	@Autowired	
	private SchemaCrawlerProperties properties;
	
	/**
	 * @param dbType The {@link DatabaseType} Database type.
	 * @return The SchemaCrawlerOptions {@link SchemaCrawlerOptions} Object
	 */
	public SchemaCrawlerOptions getCrawlerOptions(DatabaseType dbType) {
		Iterator<DatabaseSchemaCrawlerOptions> ite = properties.getCrawlerOptions().iterator();
		while (ite.hasNext()) {
			DatabaseSchemaCrawlerOptions crawlerOptions = ite.next();
			if(crawlerOptions.getType().equals(dbType)) {
				return crawlerOptions.getOptions();
			}
		}
		return SchemaCrawlerOptionBuilder.standard().toOptions();
	}

	/**
	 *
	 * Starts the schema crawler and lets it crawl the given DataSource.
	 *
	 * @param dataSource The DataSource
	 * @param dbType The {@link DatabaseType} Database type.
	 * @return The populated {@link Catalog} object containing the metadata for the extractor
	 * @throws SchemaCrawlerException Gets thrown when the database could not be crawled successfully
	 * @throws SQLException Gets thrown when the database access error occurs
	 */
	public Catalog crawl(final DataSource dataSource, DatabaseType dbType) throws SchemaCrawlerException, SQLException {
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
	 * @param dbType The {@link DatabaseType} Database type.
	 * @return The populated {@link Catalog} object containing the metadata for the extractor
	 * @throws SchemaCrawlerException Gets thrown when the database could not be crawled successfully
	 */
	public Catalog crawl(final Connection connection, DatabaseType dbType) throws SchemaCrawlerException {
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
	 * @throws SchemaCrawlerSQLException Gets thrown when the database could not be crawled successfully
	 */
	public Catalog crawl(final Connection connection,
            final SchemaRetrievalOptions schemaRetrievalOptions,
            final SchemaCrawlerOptions schemaCrawlerOptions) throws SchemaCrawlerException, SchemaCrawlerSQLException {
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
	 * @throws SchemaCrawlerSQLException Gets thrown when the database could not be crawled successfully
	 */
	public Catalog crawl(final Connection connection, final SchemaCrawlerOptions schemaCrawlerOptions) throws SchemaCrawlerException, SchemaCrawlerSQLException {
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
	    	final SchemaCrawlerOptions options = SchemaCrawlerOptionBuilder.custom(schemaInfoLevel).toOptions();
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
	    
		final SchemaCrawlerOptions options = SchemaCrawlerOptionBuilder.standard()
				.routineTypes(Arrays.asList(RoutineType.procedure, RoutineType.unknown)) // RoutineType.function not supported by h2
				.includeSchemas(schemaRule == null ? new IncludeAll() : schemaRule)
				.includeTables(tableRule == null ? new IncludeAll() : tableRule)
				.toOptions();

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
	    	final SchemaCrawlerOptions options = SchemaCrawlerOptionBuilder.custom(schemaInfoLevel).toOptions();
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
		
		final SchemaCrawlerOptions options = SchemaCrawlerOptionBuilder.standard()
				.routineTypes(Arrays.asList(RoutineType.procedure, RoutineType.unknown)) // RoutineType.function not supported by h2
				.includeSchemas(schemaRule == null ? new IncludeAll() : schemaRule)
				.includeTables(tableRule == null ? new IncludeAll() : tableRule)
				.toOptions();

	    try {
	        return SchemaCrawlerUtility.getCatalog(connectionProvider.getConnection(), options);
	    } catch (SchemaCrawlerException e) {
	        LOGGER.error("Schema crawling failed with exception", e);
	        throw e;
	    }
	}
	
}
