package com.ddd.dev.generator.codegenerator.entity;

import com.ddd.dev.generator.codegenerator.generator.CodeTableConfig;
import com.mt.common.core.web.base.EntityClass;

import java.util.List;

public class GenerationAttribute {

	private List<FieldAttribute> fieldAttributeList;

	private EntityClass entityClass ;

	private List<CodeTableConfig> CodeTableConfigs;

	public List<FieldAttribute> getFieldAttributeList() {
		return fieldAttributeList;
	}

	public void setFieldAttributeList(List<FieldAttribute> fieldAttributeList) {
		this.fieldAttributeList = fieldAttributeList;
	}

	public EntityClass getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(EntityClass entityClass) {
		this.entityClass = entityClass;
	}

	public List<CodeTableConfig> getCodeTableConfigs() {
		return CodeTableConfigs;
	}

	public void setCodeTableConfigs(List<CodeTableConfig> codeTableConfigs) {
		CodeTableConfigs = codeTableConfigs;
	}
}
