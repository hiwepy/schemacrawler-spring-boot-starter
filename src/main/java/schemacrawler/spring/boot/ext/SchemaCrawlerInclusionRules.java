package schemacrawler.spring.boot.ext;

import java.util.Collection;

import schemacrawler.schema.RoutineType;

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

	public SchemaCrawlerInclusionRule getSchemaInclusionRule() {
		return schemaInclusionRule;
	}

	public void setSchemaInclusionRule(SchemaCrawlerInclusionRule schemaInclusionRule) {
		this.schemaInclusionRule = schemaInclusionRule;
	}

	public SchemaCrawlerInclusionRule getSynonymInclusionRule() {
		return synonymInclusionRule;
	}

	public void setSynonymInclusionRule(SchemaCrawlerInclusionRule synonymInclusionRule) {
		this.synonymInclusionRule = synonymInclusionRule;
	}

	public SchemaCrawlerInclusionRule getSequenceInclusionRule() {
		return sequenceInclusionRule;
	}

	public void setSequenceInclusionRule(SchemaCrawlerInclusionRule sequenceInclusionRule) {
		this.sequenceInclusionRule = sequenceInclusionRule;
	}

	public SchemaCrawlerInclusionRule getTableInclusionRule() {
		return tableInclusionRule;
	}

	public void setTableInclusionRule(SchemaCrawlerInclusionRule tableInclusionRule) {
		this.tableInclusionRule = tableInclusionRule;
	}

	public SchemaCrawlerInclusionRule getColumnInclusionRule() {
		return columnInclusionRule;
	}

	public void setColumnInclusionRule(SchemaCrawlerInclusionRule columnInclusionRule) {
		this.columnInclusionRule = columnInclusionRule;
	}

	public Collection<RoutineType> getRoutineTypes() {
		return routineTypes;
	}

	public void setRoutineTypes(Collection<RoutineType> routineTypes) {
		this.routineTypes = routineTypes;
	}

	public SchemaCrawlerInclusionRule getRoutineInclusionRule() {
		return routineInclusionRule;
	}

	public void setRoutineInclusionRule(SchemaCrawlerInclusionRule routineInclusionRule) {
		this.routineInclusionRule = routineInclusionRule;
	}

	public SchemaCrawlerInclusionRule getRoutineColumnInclusionRule() {
		return routineColumnInclusionRule;
	}

	public void setRoutineColumnInclusionRule(SchemaCrawlerInclusionRule routineColumnInclusionRule) {
		this.routineColumnInclusionRule = routineColumnInclusionRule;
	}

	public SchemaCrawlerInclusionRule getGrepColumnInclusionRule() {
		return grepColumnInclusionRule;
	}

	public void setGrepColumnInclusionRule(SchemaCrawlerInclusionRule grepColumnInclusionRule) {
		this.grepColumnInclusionRule = grepColumnInclusionRule;
	}

	public SchemaCrawlerInclusionRule getGrepRoutineColumnInclusionRule() {
		return grepRoutineColumnInclusionRule;
	}

	public void setGrepRoutineColumnInclusionRule(SchemaCrawlerInclusionRule grepRoutineColumnInclusionRule) {
		this.grepRoutineColumnInclusionRule = grepRoutineColumnInclusionRule;
	}

	public SchemaCrawlerInclusionRule getGrepDefinitionInclusionRule() {
		return grepDefinitionInclusionRule;
	}

	public void setGrepDefinitionInclusionRule(SchemaCrawlerInclusionRule grepDefinitionInclusionRule) {
		this.grepDefinitionInclusionRule = grepDefinitionInclusionRule;
	}

}
