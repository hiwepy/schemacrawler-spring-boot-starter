package schemacrawler.spring.boot.ext;

import lombok.Data;
import schemacrawler.inclusionrule.IncludeAll;
import schemacrawler.schemacrawler.SchemaCrawlerException;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.spring.boot.utils.SchemaCrawlerOptionBuilder;
import schemacrawler.tools.databaseconnector.DatabaseConnectionOptions;
import schemacrawler.tools.databaseconnector.DatabaseUrlConnectionOptions;

@Data
public class DatabaseSchemaCrawlerOptions {

	/** 数据库类型 */
	private String type;
	/**
	 * JDBC URL of the database.
	 */
	private String url;
	/**
	 * Login username of the database.
	 */
	private String username;
	/**
	 * Login password of the database.
	 */
	private String password;
	/** 数据库Schema获取操作配置 */
	private SchemaCrawlerOptions options = SchemaCrawlerOptionBuilder.tablecolumns(new IncludeAll());
	/** 数据库Schema获取操作配置，扩展InclusionRule不方便设置问题 */
	private SchemaCrawlerInclusionRules rules = new SchemaCrawlerInclusionRules();

	public DatabaseConnectionOptions toConnectionOptions() throws SchemaCrawlerException {
		final DatabaseConnectionOptions connectionOptions = new DatabaseUrlConnectionOptions(getUrl());
		return connectionOptions;
	}
}
