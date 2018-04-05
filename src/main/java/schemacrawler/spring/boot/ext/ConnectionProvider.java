package schemacrawler.spring.boot.ext;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionProvider {

	 /**
     * This should return a new connection every time it is called.
     * @return the SQL connection object.
     * @throws SQLException if a database error occurs.
     */
    Connection getConnection() throws SQLException;
    
}
