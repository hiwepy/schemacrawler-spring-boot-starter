package schemacrawler.spring.boot.ext;

import schemacrawler.schemacrawler.ExcludeAll;
import schemacrawler.schemacrawler.IncludeAll;
import schemacrawler.schemacrawler.InclusionRule;
import schemacrawler.schemacrawler.RegularExpressionRule;

public class SchemaCrawlerInclusionRule {

	private final static String ALL = ".*";
	private final static String NONE = "";
	
	/** Inclusion Rule Type */
	private RuleType type = RuleType.DEFAULT;
	/** Inclusion pattern. If null, includes everything. default '.*' */
	private String patternInclude = ALL;
	/** Exclusion pattern. If null, excludes nothing. default '' */
	private String patternExclude = NONE;
	
	public RuleType getType() {
		return type;
	}

	public void setType(RuleType type) {
		this.type = type;
	}

	public String getPatternInclude() {
		return patternInclude;
	}

	public void setPatternInclude(String patternInclude) {
		this.patternInclude = patternInclude;
	}

	public String getPatternExclude() {
		return patternExclude;
	}

	public void setPatternExclude(String patternExclude) {
		this.patternExclude = patternExclude;
	}

	public InclusionRule inclusionRule() {
		if(RuleType.INCLUDE_ALL.equals(getType())) {
			return new IncludeAll();
		}else if(RuleType.EXCLUDE_ALL.equals(getType())) {
			return new ExcludeAll();
		}else if(RuleType.REGULAR_EXPRESSION.equals(getType())) {
			return new RegularExpressionRule(getPatternInclude(), getPatternExclude());
		}
		return null;
	}
	
}
