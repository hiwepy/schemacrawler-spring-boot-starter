package schemacrawler.spring.boot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import schemacrawler.inclusionrule.ExcludeAll;
import schemacrawler.inclusionrule.IncludeAll;
import schemacrawler.inclusionrule.RegularExpressionRule;
import schemacrawler.spring.boot.ext.RuleType;
import schemacrawler.spring.boot.ext.SchemaCrawlerInclusionRule;

/**
 * Tests for {@link SchemaCrawlerInclusionRule}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class SchemaCrawlerInclusionRuleTest {

    @Test
    void defaultValues() {
        SchemaCrawlerInclusionRule rule = new SchemaCrawlerInclusionRule();
        assertThat(rule.getType()).isEqualTo(RuleType.DEFAULT);
        assertThat(rule.getPatternInclude()).isEqualTo(".*");
        assertThat(rule.getPatternExclude()).isEmpty();
    }

    @Test
    void setterGetter() {
        SchemaCrawlerInclusionRule rule = new SchemaCrawlerInclusionRule();
        rule.setType(RuleType.INCLUDE_ALL);
        assertThat(rule.getType()).isEqualTo(RuleType.INCLUDE_ALL);

        rule.setPatternInclude("test.*");
        assertThat(rule.getPatternInclude()).isEqualTo("test.*");

        rule.setPatternExclude("exclude.*");
        assertThat(rule.getPatternExclude()).isEqualTo("exclude.*");
    }

    @Test
    void inclusionRule_includeAll() {
        SchemaCrawlerInclusionRule rule = new SchemaCrawlerInclusionRule();
        rule.setType(RuleType.INCLUDE_ALL);
        assertThat(rule.inclusionRule()).isInstanceOf(IncludeAll.class);
    }

    @Test
    void inclusionRule_excludeAll() {
        SchemaCrawlerInclusionRule rule = new SchemaCrawlerInclusionRule();
        rule.setType(RuleType.EXCLUDE_ALL);
        assertThat(rule.inclusionRule()).isInstanceOf(ExcludeAll.class);
    }

    @Test
    void inclusionRule_regularExpression() {
        SchemaCrawlerInclusionRule rule = new SchemaCrawlerInclusionRule();
        rule.setType(RuleType.REGULAR_EXPRESSION);
        rule.setPatternInclude("test.*");
        rule.setPatternExclude("exclude.*");
        assertThat(rule.inclusionRule()).isInstanceOf(RegularExpressionRule.class);
    }

    @Test
    void inclusionRule_default_returnsNull() {
        SchemaCrawlerInclusionRule rule = new SchemaCrawlerInclusionRule();
        assertThat(rule.inclusionRule()).isNull();
    }
}
