package schemacrawler.spring.boot.ext;

import java.util.Collection;

import schemacrawler.schema.RoutineType;

/**
 * Configuration container for SchemaCrawler inclusion/exclusion rules.
 * <p>Defines rules for schemas, tables, columns, routines, synonyms, sequences and grep patterns.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public class SchemaCrawlerInclusionRules {

	private SchemaCrawlerInclusionRule schemaInclusionRule;
	private SchemaCrawlerInclusionRule synonymInclusionRule;
	private SchemaCrawlerInclusionRule sequenceInclusionRule;

	private SchemaCrawlerInclusionRule tableInclusionRule;
	private SchemaCrawlerInclusionRule columnInclusionRule;

	Collection<RoutineType> routineTypes;
	private SchemaCrawlerInclusionRule routineInclusionRule;
	private SchemaCrawlerInclusionRule routineColumnInclusionRule;

	private SchemaCrawlerInclusionRule grepColumnInclusionRule;
	private SchemaCrawlerInclusionRule grepRoutineColumnInclusionRule;
	private SchemaCrawlerInclusionRule grepDefinitionInclusionRule;

    /**
     * <p>Returns the schema inclusion rule.</p>
     * @return the get schema inclusion rule
     */
	public SchemaCrawlerInclusionRule getSchemaInclusionRule() {
		return schemaInclusionRule;
	}

    /**
     * <p>Sets the schema inclusion rule.</p>
     * @param schemaInclusionRule
     */
	public void setSchemaInclusionRule(SchemaCrawlerInclusionRule schemaInclusionRule) {
		this.schemaInclusionRule = schemaInclusionRule;
	}

    /**
     * <p>Returns the synonym inclusion rule.</p>
     * @return the get synonym inclusion rule
     */
	public SchemaCrawlerInclusionRule getSynonymInclusionRule() {
		return synonymInclusionRule;
	}

    /**
     * <p>Sets the synonym inclusion rule.</p>
     * @param synonymInclusionRule
     */
	public void setSynonymInclusionRule(SchemaCrawlerInclusionRule synonymInclusionRule) {
		this.synonymInclusionRule = synonymInclusionRule;
	}

    /**
     * <p>Returns the sequence inclusion rule.</p>
     * @return the get sequence inclusion rule
     */
	public SchemaCrawlerInclusionRule getSequenceInclusionRule() {
		return sequenceInclusionRule;
	}

    /**
     * <p>Sets the sequence inclusion rule.</p>
     * @param sequenceInclusionRule
     */
	public void setSequenceInclusionRule(SchemaCrawlerInclusionRule sequenceInclusionRule) {
		this.sequenceInclusionRule = sequenceInclusionRule;
	}

    /**
     * <p>Returns the table inclusion rule.</p>
     * @return the get table inclusion rule
     */
	public SchemaCrawlerInclusionRule getTableInclusionRule() {
		return tableInclusionRule;
	}

    /**
     * <p>Sets the table inclusion rule.</p>
     * @param tableInclusionRule
     */
	public void setTableInclusionRule(SchemaCrawlerInclusionRule tableInclusionRule) {
		this.tableInclusionRule = tableInclusionRule;
	}

    /**
     * <p>Returns the column inclusion rule.</p>
     * @return the get column inclusion rule
     */
	public SchemaCrawlerInclusionRule getColumnInclusionRule() {
		return columnInclusionRule;
	}

    /**
     * <p>Sets the column inclusion rule.</p>
     * @param columnInclusionRule
     */
	public void setColumnInclusionRule(SchemaCrawlerInclusionRule columnInclusionRule) {
		this.columnInclusionRule = columnInclusionRule;
	}

    /**
     * <p>Returns the routine types.</p>
     * @return the get routine types
     */
	public Collection<RoutineType> getRoutineTypes() {
		return routineTypes;
	}

    /**
     * <p>Sets the routine types.</p>
     * @param routineTypes
     */
	public void setRoutineTypes(Collection<RoutineType> routineTypes) {
		this.routineTypes = routineTypes;
	}

    /**
     * <p>Returns the routine inclusion rule.</p>
     * @return the get routine inclusion rule
     */
	public SchemaCrawlerInclusionRule getRoutineInclusionRule() {
		return routineInclusionRule;
	}

    /**
     * <p>Sets the routine inclusion rule.</p>
     * @param routineInclusionRule
     */
	public void setRoutineInclusionRule(SchemaCrawlerInclusionRule routineInclusionRule) {
		this.routineInclusionRule = routineInclusionRule;
	}

    /**
     * <p>Returns the routine column inclusion rule.</p>
     * @return the get routine column inclusion rule
     */
	public SchemaCrawlerInclusionRule getRoutineColumnInclusionRule() {
		return routineColumnInclusionRule;
	}

    /**
     * <p>Sets the routine column inclusion rule.</p>
     * @param routineColumnInclusionRule
     */
	public void setRoutineColumnInclusionRule(SchemaCrawlerInclusionRule routineColumnInclusionRule) {
		this.routineColumnInclusionRule = routineColumnInclusionRule;
	}

    /**
     * <p>Returns the grep column inclusion rule.</p>
     * @return the get grep column inclusion rule
     */
	public SchemaCrawlerInclusionRule getGrepColumnInclusionRule() {
		return grepColumnInclusionRule;
	}

    /**
     * <p>Sets the grep column inclusion rule.</p>
     * @param grepColumnInclusionRule
     */
	public void setGrepColumnInclusionRule(SchemaCrawlerInclusionRule grepColumnInclusionRule) {
		this.grepColumnInclusionRule = grepColumnInclusionRule;
	}

    /**
     * <p>Returns the grep routine column inclusion rule.</p>
     * @return the get grep routine column inclusion rule
     */
	public SchemaCrawlerInclusionRule getGrepRoutineColumnInclusionRule() {
		return grepRoutineColumnInclusionRule;
	}

    /**
     * <p>Sets the grep routine column inclusion rule.</p>
     * @param grepRoutineColumnInclusionRule
     */
	public void setGrepRoutineColumnInclusionRule(SchemaCrawlerInclusionRule grepRoutineColumnInclusionRule) {
		this.grepRoutineColumnInclusionRule = grepRoutineColumnInclusionRule;
	}

    /**
     * <p>Returns the grep definition inclusion rule.</p>
     * @return the get grep definition inclusion rule
     */
	public SchemaCrawlerInclusionRule getGrepDefinitionInclusionRule() {
		return grepDefinitionInclusionRule;
	}

    /**
     * <p>Sets the grep definition inclusion rule.</p>
     * @param grepDefinitionInclusionRule
     */
	public void setGrepDefinitionInclusionRule(SchemaCrawlerInclusionRule grepDefinitionInclusionRule) {
		this.grepDefinitionInclusionRule = grepDefinitionInclusionRule;
	}

}
