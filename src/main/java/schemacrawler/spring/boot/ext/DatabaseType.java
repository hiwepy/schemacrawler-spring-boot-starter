package schemacrawler.spring.boot.ext;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * 数据库 - 驱动和连接示例
 */
public enum DatabaseType {

	/**
	 * Microsoft Azure Cloud ：jdbc:sqlserver://%s:%d;databaseName=%s
	 */
	AZURE("Azure", "Microsoft Azure Cloud", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://%s:%d;databaseName=%s", 1433, true),
	/**
	 * DB2数据库驱动和链接：jdbc:db2://%s:%d/%s
	 */
	DB2("DB2", "IBM DB2", "com.ibm.db2.jcc.DB2Driver", "jdbc:db2://%s:%d/%s", 50000, true),
	/**
	 * 网络模式Derby数据库驱动和链接
	 */
	DERBY_EMBEDDED("Derby-Embedded", "Derby Embedded", "org.apache.derby.jdbc.EmbeddedDriver", "jdbc:derby:%s;create=true", 1527, false),
	/**
	 * 网络模式Derby数据库驱动和链接
	 */
	DERBY_REMOTE("Derby-Remote", "Derby Remote", "org.apache.derby.jdbc.ClientDriver", "jdbc:derby://%s:%d/%s", 1527, true),
	/**
	 * HyperSQL ： http://hsqldb.org/doc/2.0/guide/index.html
	 */
	/**
	 * Apache Hive ： jdbc:hive2://%s:%d/%s
	 */
	HSQLDB_HSQL("hsqldb-hsql", "HyperSQL HSQL Server", "org.hsqldb.jdbc.JDBCDriver", "jdbc:hsqldb:hsql://%s/%s", 9001, true),
	/**
	 * HyperSQL HSQL Server ： jdbc:hsqldb:hsqls://%s/%s
	 */
	HSQLDB_HSQLS("hsqldb-hsqls", "HyperSQL HSQL Server", "org.hsqldb.jdbc.JDBCDriver", "jdbc:hsqldb:hsqls://%s/%s", 554, true),
	/**
	 * HyperSQL HTTP Server（http） ： jdbc:hsqldb:http://%s/%s
	 */
	HSQLDB_HTTP("hsqldb-http", "HyperSQL HTTP Server（http）", "org.hsqldb.jdbc.JDBCDriver", "jdbc:hsqldb:http://%s/%s",	80, true),
	/**
	 * HyperSQL HTTP Server（https） ： jdbc:hsqldb:https://%s/%s
	 */
	HSQLDB_HTTPS("hsqldb-https", "HyperSQL HTTP Server（https）", "org.hsqldb.jdbc.JDBCDriver", "jdbc:hsqldb:https://%s/%s", 443, true),
	/**
	 * HyperSQL BER ： jdbc:hsqldb:file:%s/%s;ifexists=true
	 */
	HSQLDB_BER("hsqldb-file", "HyperSQL BER", "org.hsqldb.jdbc.JDBCDriver", "jdbc:hsqldb:file:%s/%s;ifexists=true",	9101, true),
	/**
	 * Apache Hive ： jdbc:hive2://%s:%d/%s
	 */
	HIVE("hive", "Apache Hive", "org.apache.hive.jdbc.HiveDriver", "jdbc:hive2://%s:%d/%s", 10000, false),
	/**
	 * Mariadb ：jdbc:mariadb://%s:%d/%s
	 */
	MARIADB("Mariadb", "Mariadb", "org.mariadb.jdbc.Driver", "jdbc:mariadb://%s:%d/%s", 3306, true),
	/**
	 * Microsoft SQL Server
	 */
	MSSQL("MsSQL", "Microsoft SQL Server", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://%s:%d;databaseName=%s", 1433, true),
	/**
	 * MySQL数据库驱动和链接
	 */
	MYSQL("MySQL", "MySQL", "com.mysql.cj.jdbc.Driver", "jdbc:mysql://%s:%d/%s?rewriteBatchedStatements=true&useUnicode=true&characterEncoding=UTF-8", 3306, true),
	/**
	 * Oracle 10g、11g : jdbc:oracle:thin:@%s:%d:%s
	 */
	ORACLE("Oracle", "Oracle 10g、11g", "oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@%s:%d:%s", 1521, true),
	/**
	 * Oracle 12c : jdbc:oracle:thin:@%s:%d/%s
	 */
	ORACLE12C("Oracle12c", "Oracle 12c", "oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@%s:%d/%s", 1521, true),
	/**
	 * PostgreSQL：jdbc:postgresql://%s:%d/%s
	 */
	POSTGRESQL("PostgreSQL", "PostgreSQL", "org.postgresql.Driver", "jdbc:postgresql://%s:%d/%s", 5432, true),
	/**
	 * Amazon Redshift ： jdbc:redshift://%s:%d/%s
	 */
	REDSHIFT("Redshift", "Amazon Redshift", "com.amazon.redshift.jdbc41.Driver", "jdbc:redshift://%s:%d/%s", 5439, true),
	/**
	 * Teradata : jdbc:teradata://%s/DBS_PORT=%d,DATABASE=%s
	 */
	TERADATA("Teradata", "Teradata", "com.teradata.jdbc.TeraDriver", "jdbc:teradata://%s/DBS_PORT=%d,DATABASE=%s", 8002, true),
	/**
	 * IBM Netezza ： jdbc:netezza://%s:%d:%s
	 */
	NETEZZA("Netezza", "IBM Netezza", "org.netezza.Driver", "jdbc:netezza://%s:%d:%s", 5480, true),
	/**
	 * HPE Vertica : jdbc:vertica://%s:%d/%s
	 */
	VERTICA("Netezza", "HPE Vertica", "com.vertica.jdbc.Driver", "jdbc:vertica://%s:%d/%s", 5433, true);

	private String key;
	private String vendor;
	private String driver;
	private String url;
	private int port;
	private boolean standlone;

	private DatabaseType(String key, String vendor, String driver, String url, int port, boolean standlone) {
		this.key = key;
		this.vendor = vendor;
		this.driver = driver;
		this.url = url;
		this.port = port;
		this.standlone = standlone;
	}

	public String getKey() {
		return key;
	}

	public String getVendor() {
		return vendor;
	}

	public int getDefaultPort() {
		return port;
	}

	public String getDriverClass() {
		return driver;
	}

	public boolean isStandlone() {
		return standlone;
	}

	public boolean equals(DatabaseType dbtype) {
		return this.compareTo(dbtype) == 0;
	}

	public boolean equals(String dbtype) {
		return this.compareTo(DatabaseType.valueOfIgnoreCase(dbtype)) == 0;
	}
	
	public static DatabaseType valueOfIgnoreCase(String dbtype) {
		for (DatabaseType dbtypeEnum : DatabaseType.values()) {
			if (dbtypeEnum.getKey().equals(dbtype)) {
				return dbtypeEnum;
			}
		}
		throw new NoSuchElementException("Cannot found DatabaseType with dbtype '" + dbtype + "'.");
	}
	
	public String getDriverURL(String ip, int port, String dbname) {
		return String.format(url, ip, port, dbname);
	}

	public Map<String, String> toMap() {
		Map<String, String> driverMap = new HashMap<String, String>();
		driverMap.put("key", this.getKey());
		driverMap.put("vendor", this.getVendor());
		driverMap.put("driver", this.getDriverClass());
		driverMap.put("port", String.valueOf(this.getDefaultPort()));
		driverMap.put("url", url);
		return driverMap;
	}

	

	public static List<Map<String, String>> driverList() {
		List<Map<String, String>> driverList = new LinkedList<Map<String, String>>();
		for (DatabaseType driverEnum : DatabaseType.values()) {
			if (driverEnum.isStandlone()) {
				driverList.add(driverEnum.toMap());
			}
		}
		return driverList;
	}

	/**
	 * 驱动是否存在
	 */
	public boolean hasDriver() {
		try {
			Class.forName(getDriverClass());
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}
