package com.hzsparrow.business.generate.common;

import java.util.HashMap;
import java.util.Map;

public class MysqlJavaTypeMap implements GenerateTypeMap {

    private Map<String, String> map;

    private Map<String, String> jdbcTypeMap;

    public MysqlJavaTypeMap() {
        map = new HashMap<>();
        jdbcTypeMap = new HashMap<>();
        map.put("bigint", "Long");
        map.put("tinyblob", "byte[]");
        map.put("blob", "byte[]");
        map.put("mediumblob", "byte[]");
        map.put("longblob", "byte[]");
        map.put("varbinary", "byte[]");
        map.put("binary", "byte[]");
        map.put("int", "Integer");
        map.put("mediumint", "Integer");
        map.put("bit", "Boolean");
        map.put("enum", "String");
        map.put("set", "String");
        map.put("char", "String");
        map.put("varchar", "String");
        map.put("tinytext", "String");
        map.put("text", "String");
        map.put("date", "Date");
        map.put("year", "Date");
        map.put("time", "Date");
        map.put("timestamp", "Date");
        map.put("datetime", "Date");
        map.put("decimal", "BigDecimal");
        map.put("numeric", "BigDecimal");
        map.put("double", "Double");
        map.put("real", "Double");
        map.put("float", "Float");
        map.put("smallint", "Short");
        map.put("tinyint", "byte");

        jdbcTypeMap.put("bigint", "BIGINT");
        jdbcTypeMap.put("tinyblob", "BINARY");
        jdbcTypeMap.put("blob", "BLOB");
        jdbcTypeMap.put("mediumblob", "BINARY");
        jdbcTypeMap.put("longblob", "BINARY");
        jdbcTypeMap.put("varbinary", "BINARY");
        jdbcTypeMap.put("binary", "BINARY");
        jdbcTypeMap.put("int", "INTEGER");
        jdbcTypeMap.put("mediumint", "INTEGER");
        jdbcTypeMap.put("bit", "BOOLEAN");
        jdbcTypeMap.put("enum", "VARCHAR");
        jdbcTypeMap.put("set", "VARCHAR");
        jdbcTypeMap.put("char", "CHAR");
        jdbcTypeMap.put("varchar", "VARCHAR");
        jdbcTypeMap.put("tinytext", "VARCHAR");
        jdbcTypeMap.put("text", "CLOB");
        jdbcTypeMap.put("date", "DATE");
        jdbcTypeMap.put("year", "DATE");
        jdbcTypeMap.put("time", "TIME");
        jdbcTypeMap.put("timestamp", "TIMESTAMP");
        jdbcTypeMap.put("datetime", "TIMESTAMP");
        jdbcTypeMap.put("decimal", "DECIMAL");
        jdbcTypeMap.put("numeric", "NUMERIC");
        jdbcTypeMap.put("double", "DOUBLE");
        jdbcTypeMap.put("real", "REAL");
        jdbcTypeMap.put("float", "FLOAT");
        jdbcTypeMap.put("smallint", "SMALLINT");
        jdbcTypeMap.put("tinyint", "TINYINT");
    }

    @Override
    public String getJavaType(String columnType) {
        return map.get(columnType);
    }

    @Override
    public String getJdbcType(String columnType) {
        return jdbcTypeMap.get(columnType);
    }
}
