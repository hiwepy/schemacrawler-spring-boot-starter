package schemacrawler.spring.boot.ext;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Connection provider backed by a Spring DataSource.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
public class SchemaCrawlerConnectionProvider implements ConnectionProvider {

	private static Logger LOG = LoggerFactory.getLogger(SchemaCrawlerConnectionProvider.class);
	private DataSource dataSource;
	
	public SchemaCrawlerConnectionProvider(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		LOG.debug("Get ");
		return dataSource.getConnection();
	}
	
}
