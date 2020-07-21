package com.hzsparrow.business.generate.mapper;

import com.hzsparrow.business.generate.entity.ColumnInfo;
import com.hzsparrow.business.generate.entity.TableInfo;

import java.util.List;

public interface GenMapper {

    List<TableInfo> selectTableList(TableInfo param);

    TableInfo selectTableByName(String tableName);

    List<ColumnInfo> selectTableColumnsByName(String tableName);
}
