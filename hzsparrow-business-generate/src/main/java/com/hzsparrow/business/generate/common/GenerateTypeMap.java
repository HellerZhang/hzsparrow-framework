package com.hzsparrow.business.generate.common;

import java.util.Map;

public interface GenerateTypeMap {


    public String getJavaType(String columnType);

    public String getJdbcType(String columnType);
}
