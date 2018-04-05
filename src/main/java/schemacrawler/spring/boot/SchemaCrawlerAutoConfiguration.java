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
import schemacrawler.schemacrawler.SchemaCrawlerException;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
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

	//private static final Logger LOG = LoggerFactory.getLogger(SchemaCrawlerAutoConfiguration.class);
	private ApplicationContext applicationContext;
	@Autowired
	private SchemaCrawlerProperties properties;

	@Bean
	public DatabaseConnectorRegistry databaseConnectorRegistry() throws SchemaCrawlerException {
		
		// 
		Iterator<DatabaseSchemaCrawlerOptions> ite = properties.getCrawlerOptions().iterator();
		while (ite.hasNext()) {
			DatabaseSchemaCrawlerOptions crawlerOptions = ite.next();
			SchemaCrawlerOptions options = crawlerOptions.getOptions();
			// 获取自定义的规则
			SchemaCrawlerInclusionRules rules = crawlerOptions.getRules();
			if (!ObjectUtils.isEmpty(rules)) {

				/*
				 * InclusionRule schemaInclusionRule; InclusionRule synonymInclusionRule;
				 * InclusionRule sequenceInclusionRule;
				 */
				if (!ObjectUtils.isEmpty(rules.getSchemaInclusionRule())) {
					options.setSchemaInclusionRule(rules.getSchemaInclusionRule().inclusionRule());
				}
				if (!ObjectUtils.isEmpty(rules.getSequenceInclusionRule())) {
					options.setSequenceInclusionRule(rules.getSequenceInclusionRule().inclusionRule());
				}
				if (!ObjectUtils.isEmpty(rules.getSynonymInclusionRule())) {
					options.setSynonymInclusionRule(rules.getSynonymInclusionRule().inclusionRule());
				}
				/*
				 * InclusionRule tableInclusionRule; InclusionRule columnInclusionRule;
				 */
				if (!ObjectUtils.isEmpty(rules.getTableInclusionRule())) {
					options.setTableInclusionRule(rules.getTableInclusionRule().inclusionRule());
				}
				if (!ObjectUtils.isEmpty(rules.getColumnInclusionRule())) {
					options.setColumnInclusionRule(rules.getColumnInclusionRule().inclusionRule());
				}
				/*
				 * InclusionRule routineInclusionRule; InclusionRule routineColumnInclusionRule;
				 */
				if (!ObjectUtils.isEmpty(rules.getRoutineColumnInclusionRule())) {
					options.setRoutineColumnInclusionRule(rules.getRoutineColumnInclusionRule().inclusionRule());
				}
				if (!ObjectUtils.isEmpty(rules.getRoutineInclusionRule())) {
					options.setRoutineInclusionRule(rules.getRoutineInclusionRule().inclusionRule());
				}
				/*
				 * InclusionRule grepColumnInclusionRule; InclusionRule
				 * grepRoutineColumnInclusionRule; InclusionRule grepDefinitionInclusionRule;
				 */
				if (!ObjectUtils.isEmpty(rules.getGrepColumnInclusionRule())) {
					options.setGrepColumnInclusionRule(rules.getGrepColumnInclusionRule().inclusionRule());
				}
				if (!ObjectUtils.isEmpty(rules.getGrepDefinitionInclusionRule())) {
					options.setGrepDefinitionInclusionRule(rules.getGrepDefinitionInclusionRule().inclusionRule());
				}
				if (!ObjectUtils.isEmpty(rules.getGrepRoutineColumnInclusionRule())) {
					options.setGrepRoutineColumnInclusionRule(rules.getGrepRoutineColumnInclusionRule().inclusionRule());
				}

			}
		}
	   
		return new DatabaseConnectorRegistry();
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
