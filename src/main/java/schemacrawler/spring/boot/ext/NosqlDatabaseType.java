package schemacrawler.spring.boot.ext;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * 非关系型数据库 - 驱动和连接示例
 */
public enum NosqlDatabaseType {

	/**
	 * Oracle Timesten Client # jdbc:timesten:client:DSN=[dsn-name];[dsn-attributes]
	 */
	ORACLE_TIMESTEN_CLIENT("oracle-timesten", "Oracle Timesten Client", "com.timesten.jdbc.TimesTenClientDriver",
			"jdbc:timesten:client:DSN=[dsn-name];[dsn-attributes]", "jdbc:timesten:client:DSN=%s;%s", 1521, true),
	/**
	 * Oracle Timesten Direct # jdbc:timesten:direct:DSN=[dsn-name];[dsn-attributes]
	 */
	ORACLE_TIMESTEN_DIRECT("oracle-timesten", "Oracle Timesten Direct", "com.timesten.jdbc.TimesTenDriver",
			"jdbc:timesten:direct:DSN=[dsn-name];[dsn-attributes]", "jdbc:timesten:direct:DSN=%s;%s", 1521, true);
	
	private String key;
	private String vendor;
	private String driver;
	private String placeholder;
	private String url;
	private int port;
	private boolean standlone;

	private NosqlDatabaseType(String key, String vendor, String driver, String placeholder, String url, int port,
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

	public boolean equals(NosqlDatabaseType dbtype) {
		return this.compareTo(dbtype) == 0;
	}

	public boolean equals(String dbtype) {
		return this.compareTo(NosqlDatabaseType.valueOfIgnoreCase(dbtype)) == 0;
	}

	public static NosqlDatabaseType valueOfIgnoreCase(String dbtype) {
		for (NosqlDatabaseType dbtypeEnum : NosqlDatabaseType.values()) {
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
		driverMap.put("placeholder", this.getPlaceholder());
		return driverMap;
	}

	public static List<Map<String, String>> driverList() {
		return driverList(false);
	}
	
	public static List<Map<String, String>> driverList(boolean standloneOnly) {
		List<Map<String, String>> driverList = new LinkedList<Map<String, String>>();
		for (NosqlDatabaseType driverEnum : NosqlDatabaseType.values()) {
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
