package schemacrawler.spring.boot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import schemacrawler.spring.boot.ext.DatabaseSchemaCrawlerOptions;

@ConfigurationProperties(SchemaCrawlerProperties.PREFIX)
public class SchemaCrawlerProperties {

	public static final String PREFIX = "spring.schemacrawler";

	/** Enable SchemaCrawler. */
	private boolean enabled = false;
	/**
     *  Database SchemaCrawlerOptions
     */
    private List<DatabaseSchemaCrawlerOptions> crawlerOptions = new ArrayList<DatabaseSchemaCrawlerOptions>();
    
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<DatabaseSchemaCrawlerOptions> getCrawlerOptions() {
		return crawlerOptions;
	}

	public void setCrawlerOptions(List<DatabaseSchemaCrawlerOptions> crawlerOptions) {
		this.crawlerOptions = crawlerOptions;
	}
	
}