package schemacrawler.spring.boot.ext;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Connection provider backed by a Spring DataSource.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public class SchemaCrawlerConnectionProvider implements ConnectionProvider {

	private static Logger LOG = LoggerFactory.getLogger(SchemaCrawlerConnectionProvider.class);
	private DataSource dataSource;
	
	public SchemaCrawlerConnectionProvider(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	@Override
    /**
     * <p>Returns the connection.</p>
     * @return the get connection
     */
	public Connection getConnection() throws SQLException {
		LOG.debug("Get ");
		return dataSource.getConnection();
	}
	
}
