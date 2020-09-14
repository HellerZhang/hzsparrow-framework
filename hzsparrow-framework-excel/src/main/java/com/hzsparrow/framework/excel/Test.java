package com.hzsparrow.framework.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        try {
            // 合并xls
            Workbook workbook = WorkbookFactory.create(new File("D:\\test\\test\\test1.xls"));
            Sheet sheet1 = workbook.getSheetAt(0);
            Workbook workbook2 = WorkbookFactory.create(new File("D:\\test\\test\\test2.xls"));
            Sheet sheet2 = workbook2.getSheetAt(0);
            if (!workbook.getClass().getName().equals(workbook2.getClass().getName())) {
                throw new RuntimeException("不同类型的文件不能相互转换！");
            }
            ExcelOptionsUtils.copySheetTo(new Sheet[]{sheet1, sheet2}, new String[]{"sheet1", "sheet2"}, "D:\\test\\test\\test3.xls");
            // 合并xlsx
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
