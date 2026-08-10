package schemacrawler.spring.boot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import schemacrawler.spring.boot.ext.RuleType;

/**
 * Tests for {@link RuleType}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class RuleTypeTest {

    @Test
    void values() {
        RuleType[] values = RuleType.values();
        assertThat(values).hasSize(4);
        assertThat(values).contains(
                RuleType.DEFAULT,
                RuleType.INCLUDE_ALL,
                RuleType.EXCLUDE_ALL,
                RuleType.REGULAR_EXPRESSION
        );
    }

    @Test
    void valueOf() {
        assertThat(RuleType.valueOf("DEFAULT")).isEqualTo(RuleType.DEFAULT);
        assertThat(RuleType.valueOf("INCLUDE_ALL")).isEqualTo(RuleType.INCLUDE_ALL);
        assertThat(RuleType.valueOf("EXCLUDE_ALL")).isEqualTo(RuleType.EXCLUDE_ALL);
        assertThat(RuleType.valueOf("REGULAR_EXPRESSION")).isEqualTo(RuleType.REGULAR_EXPRESSION);
    }

    @Test
    void equals() {
        assertThat(RuleType.DEFAULT.equals(RuleType.DEFAULT)).isTrue();
        assertThat(RuleType.DEFAULT.equals(RuleType.INCLUDE_ALL)).isFalse();
    }
}
