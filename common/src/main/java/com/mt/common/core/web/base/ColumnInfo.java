package com.mt.common.core.web.base;


import com.mt.common.core.annotation.DColumn;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 作者:吴桥伟
 * @version 创建时间：2015年1月28日 下午3:45:27
 * 类说明
 */
public class ColumnInfo {

    private Long index = 0L;

    private String label = "";

    private String fieldName = "";

    private String columnName = "";

    private String foreignEntity = "";

    private boolean Id = false;

    public String codeTable = "";

    public List<String> codeTableOptions;

    public String constrains = "";

    private String columnDefinition = "";

    private String definitionType = "";

    private int length;

    private boolean nullable = true;

    private int precision;

    private int scale;

    private boolean unique = false;

    private String uniqueName = "";

    private String comment = "";

    private Field field;

    private Method fieldGetter;

    private Method fieldSetter;

    private Class<?> fieldType;

    private DColumn dColumn;

    private Column column;

    private boolean pageShow = true;

    //TODO:YCS 2021年7月9日20:17:29
    private String component = "";//组件

    private boolean where=false;//条件

    //a-input 文本
    //a-input-number 数字 整数 小数 金额
    //a-textarea 文本域
    //tinymce-editor 富文本
    //a-rate    评分
    //a-date-picker 日期选择
    //a-range-picker 日期范围选择
    //a-time-picker 时间选择
    //a-slider 滑动条
    //a-switch 开关
    //m-dict-select 字典下拉单选
    //m-dict-select 字典下拉多选 mode="multiple"
    //m-dict-checkbox 字典多选
    //m-dict-radio  字典单选
    //m-dict-cascader 字典级联


    public boolean isWhere() {
        return where;
    }

    public void setWhere(boolean where) {
        this.where = where;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getForeignEntity() {
        return foreignEntity;
    }

    public void setForeignEntity(String foreignEntity) {
        this.foreignEntity = foreignEntity;
    }

    public boolean isId() {
        return Id;
    }

    public void setId(boolean id) {
        Id = id;
    }

    public String getCodeTable() {
        return codeTable;
    }

    public void setCodeTable(String codeTable) {
        this.codeTable = codeTable;
    }

    public String getConstrains() {
        return constrains;
    }

    public void setConstrains(String constrains) {
        this.constrains = constrains;
    }

    public String getColumnDefinition() {
        return columnDefinition;
    }

    public void setColumnDefinition(String columnDefinition) {
        this.columnDefinition = columnDefinition;
    }

    public Long getLength() {
        return length <= 0L ? 255L : length;
    }

    public void setLength(int length) {
        if (length <= 0) {
            length = 255;
        }
        this.length = length;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Method getFieldGetter() {
        return fieldGetter;
    }

    public void setFieldGetter(Method fieldGetter) {
        this.fieldGetter = fieldGetter;
    }

    public Method getFieldSetter() {
        return fieldSetter;
    }

    public void setFieldSetter(Method fieldSetter) {
        this.fieldSetter = fieldSetter;
    }

    public Class<?> getFieldType() {
        return fieldType;
    }

    public void setFieldType(Class<?> fieldType) {
        this.fieldType = fieldType;
    }

    public String getDefinitionType() {
        return definitionType;
    }

    public void setDefinitionType(String definitionType) {
        this.definitionType = definitionType;
    }

    public DColumn getdColumn() {
        return dColumn;
    }

    public void setdColumn(DColumn dColumn) {
        this.dColumn = dColumn;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public boolean isPageShow() {
        return pageShow;
    }

    public void setPageShow(boolean pageShow) {
        this.pageShow = pageShow;
    }

    public List<String> getCodeTableOptions() {
        return codeTableOptions;
    }

    public void setCodeTableOptions(List<String> codeTableOptions) {
        this.codeTableOptions = codeTableOptions;
    }
}
