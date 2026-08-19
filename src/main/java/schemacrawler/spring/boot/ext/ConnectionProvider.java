package schemacrawler.spring.boot.ext;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * <p>ConnectionProvider implementation.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public interface ConnectionProvider {

	 /**
     * This should return a new connection every time it is called.
     * @return the SQL connection object.
     * @throws SQLException if a database error occurs.
     */
    Connection getConnection() throws SQLException;
    
}
