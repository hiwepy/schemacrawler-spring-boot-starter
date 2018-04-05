package schemacrawler.spring.boot.ext;

public enum RuleType {

	DEFAULT, INCLUDE_ALL, EXCLUDE_ALL, REGULAR_EXPRESSION;
	
	public boolean equals(RuleType RuleType) {
		return this.compareTo(RuleType) == 0;
	}
	
}
