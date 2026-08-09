package schemacrawler.spring.boot.ext;

/**
 * Types of inclusion/exclusion rules for SchemaCrawler.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
public enum RuleType {

	DEFAULT, INCLUDE_ALL, EXCLUDE_ALL, REGULAR_EXPRESSION;
	
	public boolean equals(RuleType RuleType) {
		return this.compareTo(RuleType) == 0;
	}
	
}
