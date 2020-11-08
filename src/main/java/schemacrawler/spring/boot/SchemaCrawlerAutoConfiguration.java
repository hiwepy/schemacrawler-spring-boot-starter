package schemacrawler.spring.boot;

import java.util.Iterator;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import schemacrawler.crawl.SchemaCrawler;
import schemacrawler.schemacrawler.FilterOptionsBuilder;
import schemacrawler.schemacrawler.GrepOptions;
import schemacrawler.schemacrawler.GrepOptionsBuilder;
import schemacrawler.schemacrawler.LimitOptions;
import schemacrawler.schemacrawler.LimitOptionsBuilder;
import schemacrawler.schemacrawler.LoadOptions;
import schemacrawler.schemacrawler.LoadOptionsBuilder;
import schemacrawler.schemacrawler.SchemaCrawlerException;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaCrawlerOptionsBuilder;
import schemacrawler.schemacrawler.SchemaInfoLevelBuilder;
import schemacrawler.spring.boot.ext.ConnectionProvider;
import schemacrawler.spring.boot.ext.DatabaseSchemaCrawlerOptions;
import schemacrawler.spring.boot.ext.SchemaCrawlerConnectionProvider;
import schemacrawler.spring.boot.ext.SchemaCrawlerInclusionRules;
import schemacrawler.tools.databaseconnector.DatabaseConnectorRegistry;

@Configuration
@ConditionalOnClass({ SchemaCrawler.class })
@ConditionalOnProperty(prefix = SchemaCrawlerProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ SchemaCrawlerProperties.class })
public class SchemaCrawlerAutoConfiguration implements ApplicationContextAware {

	// private static final Logger LOG =
	// LoggerFactory.getLogger(SchemaCrawlerAutoConfiguration.class);
	private ApplicationContext applicationContext;
	@Autowired
	private SchemaCrawlerProperties properties;

	@Bean
	public DatabaseConnectorRegistry databaseConnectorRegistry() throws SchemaCrawlerException {

		//
		Iterator<DatabaseSchemaCrawlerOptions> ite = properties.getCrawlerOptions().iterator();
		while (ite.hasNext()) {
			DatabaseSchemaCrawlerOptions crawlerOptions = ite.next();
			// 获取自定义的规则
			SchemaCrawlerInclusionRules rules = crawlerOptions.getRules();
			if (!ObjectUtils.isEmpty(rules)) {

				final GrepOptions grepOptions = GrepOptionsBuilder.builder()
						/*
						 * InclusionRule grepColumnInclusionRule; InclusionRule
						 * grepRoutineColumnInclusionRule; InclusionRule grepDefinitionInclusionRule;
						 */
						.includeGreppedColumns(rules.getGrepColumnInclusionRule().inclusionRule())
						.includeGreppedDefinitions(rules.getGrepDefinitionInclusionRule().inclusionRule())
				// .includeGreppedRoutineColumns(rules.getGrepRoutineColumnInclusionRule().inclusionRule())
						.toOptions();

				final LimitOptions limitOptions = LimitOptionsBuilder.builder()
						// Set what details are required in the schema - this affects the
						/*
						 * InclusionRule schemaInclusionRule; InclusionRule synonymInclusionRule; InclusionRule sequenceInclusionRule;
						 */
						.includeSchemas(rules.getSchemaInclusionRule().inclusionRule())
						.includeSequences(rules.getSequenceInclusionRule().inclusionRule())
						.includeSynonyms(rules.getSynonymInclusionRule().inclusionRule())
						/*
						 * InclusionRule tableInclusionRule; InclusionRule columnInclusionRule;
						 */
						.includeTables(rules.getTableInclusionRule().inclusionRule())
						.includeColumns(rules.getColumnInclusionRule().inclusionRule())
						/*
						 * InclusionRule routineInclusionRule; InclusionRule routineColumnInclusionRule;
						 */
						// .includeRoutineColumns(rules.getRoutineColumnInclusionRule().inclusionRule())
						.includeRoutines(rules.getRoutineInclusionRule().inclusionRule())
						.toOptions();
				
				LoadOptions loadOptions = LoadOptionsBuilder.builder()
						.withSchemaInfoLevel(SchemaInfoLevelBuilder.standard()).toOptions();
				
				SchemaCrawlerOptions schemaCrawlerOptions =  SchemaCrawlerOptionsBuilder
						.newSchemaCrawlerOptions()
						.withFilterOptions(FilterOptionsBuilder.newFilterOptions())
						.withGrepOptions(grepOptions) 
						.withLimitOptions(limitOptions)
						.withLoadOptions(loadOptions);

				// reset options
				crawlerOptions.setOptions(schemaCrawlerOptions);
			}
		}

		return DatabaseConnectorRegistry.getDatabaseConnectorRegistry();
	}

	@Bean
	public ConnectionProvider connectionProvider(DataSource datasource) {
		return new SchemaCrawlerConnectionProvider(datasource);
	}

	@Bean
	public SchemaCrawlerTemplate disruptorTemplate() {
		return new SchemaCrawlerTemplate();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
