package com.hzsparrow.business.generate.entity;

public class ColumnInfo {

    /**
     * 列名
     */
    private String columnName;

    /**
     * java字段名
     */
    private String fieldName;

    /**
     * 列类型
     */
    private String dataType;

    /**
     * java字段类型
     */
    private String fieldType;

    /**
     * 列注释
     */
    private String columnComment;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }
}
