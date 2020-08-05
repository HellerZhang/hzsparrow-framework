package com.hzsparrow.business.generate.service;

import com.hzsparrow.business.generate.entity.ColumnInfo;
import com.hzsparrow.business.generate.entity.GenConfig;
import com.hzsparrow.business.generate.entity.TableInfo;
import com.hzsparrow.business.generate.mapper.GenMapper;
import com.hzsparrow.business.generate.utils.GenerateUtils;
import com.hzsparrow.business.generate.utils.VelocityInitializer;
import com.hzsparrow.framework.utils.StringFormatUtils;
import com.hzsparrow.framework.utils.TextWriterUtil;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.List;

@Service
public class GenerateService {

    @Autowired
    private GenMapper mysqlGenMapper;

    public void generateCode(String tableName, GenConfig genConfig) {
        TableInfo tableInfo = mysqlGenMapper.selectTableByName(tableName);
        List<ColumnInfo> columnInfoList = mysqlGenMapper.selectTableColumnsByName(tableName);
        generateCode(tableInfo, columnInfoList, genConfig);
    }

    public void generateCode(TableInfo tableInfo, List<ColumnInfo> columnInfoList, GenConfig genConfig) {
        String className = GenerateUtils.tableToJava(tableInfo.getTableName());
        tableInfo.setClassName(className);
        tableInfo.setClassName2Lower(StringFormatUtils.camelName4Lower(className));
        tableInfo.setClassName2AllLower(className.toLowerCase());
        tableInfo.setColumnInfoList(GenerateUtils.transColumns(columnInfoList));
        tableInfo.setPrimaryKey(tableInfo.getLastColumn());
        VelocityInitializer.initVelocity();
        String packageName = genConfig.getPackageName();
        String moduleName = GenerateUtils.getModuleName(packageName);
        VelocityContext context = GenerateUtils.getVelocityContext(genConfig, tableInfo);
        List<String> templates = GenerateUtils.getTemplates();
        for (String template : templates) {
            StringWriter sw = new StringWriter();
            Template temp = Velocity.getTemplate(template, "UTF-8");
            temp.merge(context, sw);
            String filePath = GenerateUtils.getFileName(template, tableInfo, moduleName);
            TextWriterUtil.wirteLineUTF8(filePath, sw.toString());
        }
    }
}
