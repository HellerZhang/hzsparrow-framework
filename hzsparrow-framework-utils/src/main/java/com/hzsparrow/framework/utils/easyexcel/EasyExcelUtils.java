/**
 * $Id:$
 * Copyright 2019-2019 ShiJiaZhuang ShanZhouWangLuo Technology Company Ltd. All rights reserved.
 */
package com.hzsparrow.framework.utils.easyexcel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.hzsparrow.framework.utils.ResponseSetterUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * EasyExcel工具类
 * 
 * @author Heller.Zhang
 * @since 2019年5月28日 下午4:50:21
 */
public class EasyExcelUtils {

    /**
     * 导出Excel表格
     * 
     * @param fileName 文件名（需带后缀）
     * @param sheetName sheet名
     * @param response
     * @param listData
     * @author Heller.Zhang
     * @since 2019年5月28日 下午4:55:01
     */
    public static void exportExcel(String fileName, String sheetName, HttpServletResponse response,
            List<? extends BaseRowModel> listData) {
        try {
            ResponseSetterUtils.setUtf8ContentType(response, fileName);
            OutputStream out = response.getOutputStream();
            ExcelWriter writer = EasyExcelFactory.getWriter(out, ExcelTypeEnum.XLSX, true);
            Sheet sheet1 = new Sheet(1, 0, listData.get(0).getClass());
            sheet1.setSheetName(sheetName);
            writer.write(listData, sheet1);
            writer.finish();
        } catch (IOException e) {
            throw new RuntimeException("导出Excel失败！", e);
        }
    }

    /**
     * 导出excel文件到本地
     * 
     * @param fileName 文件名（需带后缀）
     * @param sheetName sheet名
     * @param exportPath 导出地址（具体到文件的父级目录）
     * @param listData 数据
     * @author Heller.Zhang
     * @since 2019年5月28日 下午5:00:36
     */
    public static String exportExcel(String fileName, String sheetName, String exportPath,
            List<? extends BaseRowModel> listData) {
        try {
            String fullPath = exportPath + File.separator + fileName;
            FileOutputStream out = new FileOutputStream(fullPath);
            ExcelWriter writer = EasyExcelFactory.getWriter(out, ExcelTypeEnum.XLSX, true);
            Sheet sheet1 = new Sheet(1, 0, listData.get(0).getClass());
            sheet1.setSheetName(sheetName);
            writer.write(listData, sheet1);
            writer.finish();
            return fullPath;
        } catch (IOException e) {
            throw new RuntimeException("导出Excel失败！", e);
        }
    }
}
