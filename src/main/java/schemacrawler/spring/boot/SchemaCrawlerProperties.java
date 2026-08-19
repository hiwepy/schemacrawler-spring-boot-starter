package schemacrawler.spring.boot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import schemacrawler.spring.boot.ext.DatabaseSchemaCrawlerOptions;

@ConfigurationProperties(SchemaCrawlerProperties.PREFIX)
/**
 * <p>Configuration properties for SchemaCrawler.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public class SchemaCrawlerProperties {

	public static final String PREFIX = "spring.schemacrawler";

	/** Enable SchemaCrawler. */
	private boolean enabled = false;
	/**
     *  Database SchemaCrawlerOptions
     */
    private List<DatabaseSchemaCrawlerOptions> crawlerOptions = new ArrayList<DatabaseSchemaCrawlerOptions>();
    
    /**
     * <p>Checks if enabled.</p>
     * @return the is enabled
     */
	public boolean isEnabled() {
		return enabled;
	}

    /**
     * <p>Sets the enabled.</p>
     * @param enabled
     */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

    /**
     * <p>Returns the crawler options.</p>
     * @return the get crawler options
     */
	public List<DatabaseSchemaCrawlerOptions> getCrawlerOptions() {
		return crawlerOptions;
	}

    /**
     * <p>Sets the crawler options.</p>
     * @param crawlerOptions
     */
	public void setCrawlerOptions(List<DatabaseSchemaCrawlerOptions> crawlerOptions) {
		this.crawlerOptions = crawlerOptions;
	}
	
}