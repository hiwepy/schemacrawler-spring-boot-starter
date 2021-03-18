package schemacrawler.spring.boot.ext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.StringUtils;

/**
 * 关系型数据库 - 驱动和连接示例
 */
public enum DatabaseType {

	/**
	 * Apache Hive # jdbc:hive2://[host-name]:[port]/[database-name]
	 */
	APACHE_HIVE("hive", "Apache Hive", "org.apache.hive.jdbc.HiveDriver", 
			"jdbc:hive2://[host-name]:[port]/[database-name]", "jdbc:hive2://%s:%d/%s", 10000, true),
	/**
	 * Apache Impala 2.x # jdbc:impala://[host-name]:[port]/[database-name]
	 */
	APACHE_IMPALA2("impala", "Apache Impala", "com.cloudera.impala.jdbc41.Driver", 
			"jdbc:impala://[host-name]:[port]/[database-name]", "jdbc:impala://%s:%d/%s", 21050, true),
	/**
	 * Apache Impala 3.x # jdbc:impala://[host-name]:[port]/[database-name]
	 */
	APACHE_IMPALA3("impala", "Apache Impala", "com.cloudera.impala.jdbc41.Driver", 
			"jdbc:impala://[host-name]:[port]/[database-name];UseSasl=0;AuthMech=3;UID=impala;PWD= ", "jdbc:impala://%s:%d/%s;UseSasl=0;AuthMech=3;UID=impala;PWD= ", 21050, true),
	/**
	 * Apache Hive # jdbc:kylin://[host-name]:[port]/[database-name]
	 */
	APACHE_KYLIN("kylin", "Apache Kylin", "org.apache.kylin.jdbc.Driver", 
			"jdbc:kylin://[host-name]:[port]/[database-name]", "jdbc:kylin://%s:%d/%s", 7070, true),
	/**
	 * Apache Phoenix # jdbc:phoenix:[host-name]:[port]/[database-name]
	 */
	APACHE_PHOENIX("phoenix", "Apache Phoenix", "org.apache.phoenix.jdbc.PhoenixDriver", 
			"jdbc:phoenix:[host-name]:[port]/[database-name]", "jdbc:phoenix:%s:%d/%s", 7070, true),
	/**
	 * ClickHouse # jdbc:clickhouse://[database-name]:[port]
	 */
	CLICKHOUSE("clickHouse", "ClickHouse", "ru.yandex.clickhouse.ClickHouseDriver",
			"jdbc:clickhouse://[database-name]:[port]", "jdbc:clickhouse:%s:%d", 8123, false),
	/**
	 * Derby Embedded # jdbc:derby:[database-name];create=true
	 */
	DERBY_EMBEDDED("derby-embedded", "Derby Embedded", "org.apache.derby.jdbc.EmbeddedDriver",
			"jdbc:derby:[database-name];create=true", "jdbc:derby:%s;create=true", 1527, false),
	/**
	 * Derby Network # jdbc:derby://[host-name]:[port]/[database-name]
	 */
	DERBY_REMOTE("derby-network", "Derby Network", "org.apache.derby.jdbc.ClientDriver",
			"jdbc:derby://[host-name]:[port]/[database-name]", "jdbc:derby://%s:%d/%s", 1527, true),

	/*
	 * HyperSQL ： http://hsqldb.org/doc/2.0/guide/index.html
	 * http://hsqldb.org/doc/2.0/guide/running-chapt.html#rgc_connecting_db
	 */

	/**
	 * HyperSQL HSQL Server # jdbc:hsqldb:hsql://[host-name]/[database-name]
	 */
	HSQLDB_HSQL("hsqldb-hsql", "HyperSQL HSQL Server", "org.hsqldb.jdbc.JDBCDriver",
			"jdbc:hsqldb:hsql://[host-name]/[database-name]", "jdbc:hsqldb:hsql://%s/%s", 9001, true),
	/**
	 * HyperSQL HSQL Server（SSL） # jdbc:hsqldb:hsqls://[host-name]/[database-name]
	 */
	HSQLDB_HSQLS("hsqldb-hsqls", "HyperSQL HSQL Server（SSL）", "org.hsqldb.jdbc.JDBCDriver",
			"jdbc:hsqldb:hsqls://[host-name]/[database-name]", "jdbc:hsqldb:hsqls://%s/%s", 554, true),
	/**
	 * HyperSQL HTTP Server # jdbc:hsqldb:http://[host-name]:[port]/[database-name]
	 */
	HSQLDB_HTTP("hsqldb-http", "HyperSQL HTTP Server", "org.hsqldb.jdbc.JDBCDriver",
			"jdbc:hsqldb:http://[host-name]:[port]/[database-name]", "jdbc:hsqldb:http://%s:%d/%s", 80, true),
	/**
	 * HyperSQL HTTP Server（SSL） #
	 * jdbc:hsqldb:https://[host-name]:[port]/[database-name]
	 */
	HSQLDB_HTTPS("hsqldb-https", "HyperSQL HTTP Server（SSL）", "org.hsqldb.jdbc.JDBCDriver",
			"jdbc:hsqldb:https://[host-name]:[port]/[database-name]", "jdbc:hsqldb:https://%s:%d/%s", 443, true),
	/**
	 * HyperSQL BER # jdbc:hsqldb:file:[file-path]/[database-name];ifexists=true
	 */
	HSQLDB_BER("hsqldb-file", "HyperSQL BER", "org.hsqldb.jdbc.JDBCDriver",
			"jdbc:hsqldb:file:[file-path]/[database-name];ifexists=true", "jdbc:hsqldb:file:%s/%s;ifexists=true", 
			9101, false),
	/**
	 * IBM DB2 # jdbc:db2://[host-name]:[port]/[database-name]
	 */
	IBM_DB2("db2", "IBM DB2", "com.ibm.db2.jcc.DB2Driver", 
			"jdbc:db2://[host-name]:[port]/[database-name]", "jdbc:db2://%s:%d/%s", 50000, true),
	/**
	 * IBM Informix # jdbc:informix-sqli://[host-name]:[port]/[database-name]:INFORMIXSERVER=server
	 */
	IBM_INFORMIX("db2", "IBM Informix", "com.informix.jdbc.IfxDriver", 
			"jdbc:informix-sqli://[host-name]:[port]/[database-name]:INFORMIXSERVER=server",
			"jdbc:informix-sqli://%s:%d/%s:INFORMIXSERVER=server", 9088, true),
	/**
	 * IBM Netezza # jdbc:netezza://[host-name]:[port]:[database-name]
	 */
	IBM_NETEZZA("netezza", "IBM Netezza", "org.netezza.Driver", 
			"jdbc:netezza://[host-name]:[port]:[database-name]", "jdbc:netezza://%s:%d:%s", 5480, true),
	/**
	 * Kingbase # jdbc:kingbase://[host-name]:[port]/[database-name]
	 * https://help.fanruan.com/finebi/doc-view-304.html
	 */
	KINGBASE("kingbase", "Kingbase", "com.kingbase.Driver", 
			"jdbc:kingbase://[host-name]:[port]/[database-name]", "jdbc:kingbase://%s:%d/%s", 54321, true),
	/**
	 * Presto # jdbc:kingbase://[host-name]:[port]/[database-name]
	 * https://help.fanruan.com/finebi/doc-view-305.html
	 */
	PRESTO("presto", "Presto", "com.facebook.presto.jdbc.PrestoDriver", 
			"jdbc:presto://[host-name]:[port]/[database-name]", "jdbc:presto://%s:%d/%s", 8080, true),
	/**
	 * Mariadb # jdbc:mariadb://[host-name]:[port]/[database-name]
	 */
	MARIADB("mariadb", "Mariadb", "org.mariadb.jdbc.Driver", 
			"jdbc:mariadb://[host-name]:[port]/[database-name]", "jdbc:mariadb://%s:%d/%s", 3306, true),
	/**
	 * Microsoft Azure Cloud #
	 * jdbc:sqlserver://[host-name]:[port];databaseName=[database-name]
	 */
	MS_AZURE("azure", "Microsoft Azure Cloud", "com.microsoft.sqlserver.jdbc.SQLServerDriver",
			"jdbc:sqlserver://[host-name]:[port];databaseName=[database-name]",
			"jdbc:sqlserver://%s:%d;databaseName=%s", 1433, true),
	/**
	 * Microsoft SQL Server 2000 #
	 * jdbc:microsoft:sqlserver://[host-name]:[port];DatabaseName=[database-name]
	 * https://docs.microsoft.com/zh-cn/sql/connect/jdbc/microsoft-jdbc-driver-for-sql-server-support-matrix?view=sql-server-2017
	 */
	MS_SQL_2000("sqlserver2000", "Microsoft SQL Server 2000", "com.microsoft.sqlserver.jdbc.SQLServerDriver",
			"jdbc:microsoft:sqlserver://[host-name]:[port];DatabaseName=[database-name]",
			"jdbc:microsoft:sqlserver://%s:%d;DatabaseName=%s;integratedSecurity=false;", 1433, true),
	/**
	 * Microsoft SQL Server 2005 + #
	 * jdbc:sqlserver://[host-name]:[port];DatabaseName=[database-name]
	 */
	MS_SQL("sqlserver", "Microsoft SQL Server 2005及以上版本", "com.microsoft.sqlserver.jdbc.SQLServerDriver",
			"jdbc:sqlserver://[host-name]:[port];DatabaseName=[database-name]",
			"jdbc:sqlserver://%s:%d;DatabaseName=%s;integratedSecurity=false;", 1433, true),
	/**
	 * MySQL #
	 * jdbc:mysql://[host-name]:[port]/[database-name]?rewriteBatchedStatements=true&amp;useUnicode=true&amp;characterEncoding=UTF-8
	 */
	MYSQL("mysql", "MySQL", "com.mysql.cj.jdbc.Driver",
			"jdbc:mysql://[host-name]:[port]/[database-name]?rewriteBatchedStatements=true&useUnicode=true&characterEncoding=UTF-8",
			"jdbc:mysql://%s:%d/%s?rewriteBatchedStatements=true&useUnicode=true&characterEncoding=UTF-8", 3306, true),
	/**
	 * Oracle 10g、11g # jdbc:oracle:thin:@[host-name]:[port]:[database-name]
	 */
	ORACLE("oracle", "Oracle 10g、11g", "oracle.jdbc.driver.OracleDriver",
			"jdbc:oracle:thin:@[host-name]:[port]:[database-name]", "jdbc:oracle:thin:@%s:%d:%s", 1521, true),
	/**
	 * Oracle 12c # jdbc:oracle:thin:@[host-name]:[port]/[database-name]
	 */
	ORACLE_12C("oracle-12c", "Oracle 12c", "oracle.jdbc.driver.OracleDriver",
			"jdbc:oracle:thin:@[host-name]:[port]/[database-name]", "jdbc:oracle:thin:@%s:%d/%s", 1521, true),
	/**
	 * Oracle 18c # jdbc:oracle:thin:@//[host-name]:[port]/[database-name]
	 */
	ORACLE_18C("oracle-18c", "Oracle 18c", "oracle.jdbc.driver.OracleDriver",
			"jdbc:oracle:thin:@[host-name]:[port]/[database-name]", "jdbc:oracle:thin:@%s:%d/%s", 1521, true),
	/**
	 * PostgreSQL # jdbc:postgresql://[host-name]:[port]/[database-name]
	 */
	POSTGRESQL("postgreSQL", "PostgreSQL", "org.postgresql.Driver",
			"jdbc:postgresql://[host-name]:[port]/[database-name]", "jdbc:postgresql://%s:%d/%s", 5432, true),
	/**
	 * Amazon Redshift ： jdbc:redshift://[host-name]:[port]/[database-name]
	 * https://help.fanruan.com/finebi/doc-view-292.html
	 */
	AMAZON_REDSHIFT("redshift", "Amazon Redshift", "com.amazon.redshift.jdbc41.Driver",
			"jdbc:redshift://[host-name]:[port]/[database-name]", "jdbc:redshift://%s:%d/%s", 5439, true),
	/**
	 * Gbase 8A ： jdbc:redshift://[host-name]:[port]/[database-name]
	 * https://help.fanruan.com/finebi/doc-view-295.html
	 */
	GBASE_8A("gbase-8A", "Gbase 8A", "com.gbase.jdbc.Driver",
			"jdbc:gbase://[host-name]:[port]/[database-name]", "jdbc:gbase://%s:%d/%s", 5258, true),
	/**
	 * Gbase 8S ： jdbc:redshift://[host-name]:[port]/[database-name]
	 * https://help.fanruan.com/finebi/doc-view-296.html
	 */
	GBASE_8S("gbase-8S", "Gbase 8S", "com.gbasedbt.jdbc.IfxDriver",
			"jdbc:gbasedbt-sqli://[host-name]:[port]/[database-name]", "jdbc:gbasedbt-sqli://%s:%d/%s", 9088, true),
	/**
	 * Teradata # jdbc:teradata://[host-name]/DBS_PORT=[port],DATABASE=[database-name]
	 * https://help.fanruan.com/finebi/doc-view-310.html
	 */
	TERADATA("teradata", "Teradata", "com.teradata.jdbc.TeraDriver",
			"jdbc:teradata://[host-name]/DBS_PORT=[port],DATABASE=[database-name]",
			"jdbc:teradata://%s/DBS_PORT=%d,DATABASE=%s", 8002, true),
	
	/**
	 * HPE Vertica # jdbc:vertica://[host-name]:[port]/[database-name]
	 */
	VERTICA("vertica", "HPE Vertica", "com.vertica.jdbc.Driver", 
			"jdbc:vertica://[host-name]:[port]/[database-name]", "jdbc:vertica://%s:%d/%s", 5433, true);

	private String key;
	private String vendor;
	private String driver;
	private String placeholder;
	private String url;
	private int port;
	private boolean standlone;

	private DatabaseType(String key, String vendor, String driver, String placeholder, String url, int port,
			boolean standlone) {
		this.key = key;
		this.vendor = vendor;
		this.driver = driver;
		this.placeholder = placeholder;
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
	
	public String getPlaceholder() {
		return placeholder;
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
	
	public String getDriverURL(String ip, int port, String dbname, Map<String,String> properties, String separator) {
		if(properties != null && !properties.isEmpty()) {
			Iterator<Entry<String, String>> ite = properties.entrySet().iterator();
			List<String> args = new ArrayList<>();
			while (ite.hasNext()) {
				Entry<String, String> entry = ite.next();
				args.add(entry.getKey() + "=" + entry.getValue());
			}
			return String.format(url, ip, port, dbname) + separator + StringUtils.join(args, separator);
		}
		return String.format(url, ip, port, dbname);
	}

	public Map<String, String> toMap() {
		Map<String, String> driverMap = new HashMap<String, String>();
		driverMap.put("key", this.getKey());
		driverMap.put("vendor", this.getVendor());
		driverMap.put("driver", this.getDriverClass());
		driverMap.put("port", String.valueOf(this.getDefaultPort()));
		driverMap.put("placeholder", this.getPlaceholder());
		return driverMap;
	}

	public static List<Map<String, String>> driverList() {
		return driverList(false);
	}
	
	public static List<Map<String, String>> driverList(boolean standloneOnly) {
		List<Map<String, String>> driverList = new LinkedList<Map<String, String>>();
		for (DatabaseType driverEnum : DatabaseType.values()) {
			if(standloneOnly) {
				if (driverEnum.isStandlone()) {
					driverList.add(driverEnum.toMap());
				}
			} else {
				driverList.add(driverEnum.toMap());
			}
		}
		return driverList;
	}

	public boolean hasDriver() {
		try {
			Class.forName(getDriverClass());
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}