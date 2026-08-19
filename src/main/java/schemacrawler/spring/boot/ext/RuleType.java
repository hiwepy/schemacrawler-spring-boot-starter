package schemacrawler.spring.boot.ext;

/**
 * Types of inclusion/exclusion rules for SchemaCrawler.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public enum RuleType {

	DEFAULT, INCLUDE_ALL, EXCLUDE_ALL, REGULAR_EXPRESSION;
	
    /**
     * <p>Equals.</p>
     * @param RuleType
     * @return the equals
     */
	public boolean equals(RuleType RuleType) {
		return this.compareTo(RuleType) == 0;
	}
	
}
