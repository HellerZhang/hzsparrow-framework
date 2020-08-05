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
     * 大驼峰命名字段名
     */
    private String filedName4Upper;

    /**
     * 列类型
     */
    private String dataType;

    /**
     * java字段类型
     */
    private String fieldType;

    /**
     * jdbcType类型
     */
    private String jdbcType;

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

    public String getFiledName4Upper() {
        return filedName4Upper;
    }

    public void setFiledName4Upper(String filedName4Upper) {
        this.filedName4Upper = filedName4Upper;
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

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }
}
