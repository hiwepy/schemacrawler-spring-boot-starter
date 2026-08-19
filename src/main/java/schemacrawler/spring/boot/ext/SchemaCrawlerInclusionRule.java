package schemacrawler.spring.boot.ext;

import schemacrawler.inclusionrule.ExcludeAll;
import schemacrawler.inclusionrule.IncludeAll;
import schemacrawler.inclusionrule.InclusionRule;
import schemacrawler.inclusionrule.RegularExpressionRule;

/**
 * <p>SchemaCrawlerInclusionRule implementation.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public class SchemaCrawlerInclusionRule {

	private final static String ALL = ".*";
	private final static String NONE = "";
	
	/** Inclusion Rule Type */
	private RuleType type = RuleType.DEFAULT;
	/** Inclusion pattern. If null, includes everything. default '.*' */
	private String patternInclude = ALL;
	/** Exclusion pattern. If null, excludes nothing. default '' */
	private String patternExclude = NONE;
	
    /**
     * <p>Returns the type.</p>
     * @return the get type
     */
	public RuleType getType() {
		return type;
	}

    /**
     * <p>Sets the type.</p>
     * @param type
     */
	public void setType(RuleType type) {
		this.type = type;
	}

    /**
     * <p>Returns the pattern include.</p>
     * @return the get pattern include
     */
	public String getPatternInclude() {
		return patternInclude;
	}

    /**
     * <p>Sets the pattern include.</p>
     * @param patternInclude
     */
	public void setPatternInclude(String patternInclude) {
		this.patternInclude = patternInclude;
	}

    /**
     * <p>Returns the pattern exclude.</p>
     * @return the get pattern exclude
     */
	public String getPatternExclude() {
		return patternExclude;
	}

    /**
     * <p>Sets the pattern exclude.</p>
     * @param patternExclude
     */
	public void setPatternExclude(String patternExclude) {
		this.patternExclude = patternExclude;
	}

    /**
     * <p>Inclusion rule.</p>
     * @return the inclusion rule
     */
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
