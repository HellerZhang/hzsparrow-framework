package com.hzsparrow.business.generate.entity;

import java.util.Date;
import java.util.List;

public class TableInfo {
    /**
     * 表名
     */
    private String tableName;

    /**
     * 类名
     */
    private String className;

    /**
     * 小驼峰类名
     */
    private String className2Lower;

    /**
     * 全小写类名
     */
    private String className2AllLower;

    /**
     * 表注释
     */
    private String tableComment;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    private ColumnInfo primaryKey;

    private List<ColumnInfo> columnInfoList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName2Lower() {
        return className2Lower;
    }

    public void setClassName2Lower(String className2Lower) {
        this.className2Lower = className2Lower;
    }

    public String getClassName2AllLower() {
        return className2AllLower;
    }

    public void setClassName2AllLower(String className2AllLower) {
        this.className2AllLower = className2AllLower;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<ColumnInfo> getColumnInfoList() {
        return columnInfoList;
    }

    public void setColumnInfoList(List<ColumnInfo> columnInfoList) {
        this.columnInfoList = columnInfoList;
    }

    public ColumnInfo getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(ColumnInfo primaryKey) {
        this.primaryKey = primaryKey;
    }

    public ColumnInfo getLastColumn() {
        return getColumnInfoList().get(0);
    }
}
