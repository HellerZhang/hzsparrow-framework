package com.hzsparrow.business.generate.utils;

import com.hzsparrow.business.generate.common.GenerateTypeMap;
import com.hzsparrow.business.generate.common.MysqlJavaTypeMap;
import com.hzsparrow.business.generate.entity.ColumnInfo;
import com.hzsparrow.business.generate.entity.GenConfig;
import com.hzsparrow.business.generate.entity.TableInfo;
import com.hzsparrow.framework.utils.CalendarUtils;
import com.hzsparrow.framework.utils.StringFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GenerateUtils {

    /**
     * 项目空间路径
     */
    private static final String PROJECT_PATH = "d:/test/test";

    private static GenerateTypeMap generateTypeMap = new MysqlJavaTypeMap();

    /**
     * 设置列信息
     */
    public static List<ColumnInfo> transColumns(List<ColumnInfo> columns) {
        // 列信息
        List<ColumnInfo> columsList = new ArrayList<>();
        for (ColumnInfo column : columns) {
            // 列名转换成Java字段名
            String fieldName = StringFormatUtils.camelName4Lower(column.getColumnName());
            column.setFieldName(fieldName);

            // 列的数据类型，转换成Java类型
            String fieldType = generateTypeMap.getJavaType(column.getDataType());
            column.setFieldType(fieldType);

            columsList.add(column);
        }
        return columsList;
    }

    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static VelocityContext getVelocityContext(GenConfig genConfig, TableInfo table) {
        // java对象数据传递到模板文件vm
        VelocityContext velocityContext = new VelocityContext();
        String packageName = genConfig.getPackageName();
        velocityContext.put("tableName", table.getTableName());
        velocityContext.put("tableComment", replaceKeyword(table.getTableComment()));
        velocityContext.put("primaryKey", table.getPrimaryKey());
        velocityContext.put("className", table.getClassName());
        velocityContext.put("classname", table.getClassName2Lower());
        velocityContext.put("moduleName", GenerateUtils.getModuleName(packageName));
        velocityContext.put("columns", table.getColumnInfoList());
        velocityContext.put("package", packageName);
        velocityContext.put("author", genConfig.getAuthor());
        velocityContext.put("datetime", CalendarUtils.formatDateTime(new Date()));
        return velocityContext;
    }

    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<String>();
        templates.add("templates/vm/controller/Controller.java.vm");
        templates.add("templates/vm/service/Service.java.vm");
        templates.add("templates/vm/entity/Entity.java.vm");
        templates.add("templates/vm/mapper/Mapper.java.vm");
        templates.add("templates/vm/mapper/Mapper.xml.vm");
        return templates;
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName) {
        return StringFormatUtils.camelName(tableName);
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, TableInfo table, String moduleName) {
        // 小写类名
        String classname = table.getClassName2Lower();
        // 大写类名
        String className = table.getClassName();
        String javaPath = PROJECT_PATH + "/" + moduleName + "/";

        if (StringUtils.isNotEmpty(classname)) {
            javaPath += classname.replace(".", "/") + "/";
        }
        return javaPath + className + template.substring(0, template.lastIndexOf("."));
    }

    /**
     * 获取模块名
     *
     * @param packageName 包名
     * @return 模块名
     */
    public static String getModuleName(String packageName) {
        int lastIndex = packageName.lastIndexOf(".");
        int nameLength = packageName.length();
        String moduleName = StringUtils.substring(packageName, lastIndex + 1, nameLength);
        return moduleName;
    }

    public static String replaceKeyword(String keyword) {
        String keyName = keyword.replaceAll("(?:表|信息)", "");
        return keyName;
    }

    public static void main(String[] args) {

    }
}
