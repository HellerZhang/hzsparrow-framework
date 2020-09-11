package com.hzsparrow.framework.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        try {
            // 合并xls
            Workbook workbook = WorkbookFactory.create(new File("D:\\test\\test\\test1.xlsx"));
            Sheet sheet1 = workbook.getSheetAt(0);
            Workbook workbook2 = WorkbookFactory.create(new File("D:\\test\\test\\test2.xls"));
            Sheet sheet2 = workbook2.getSheetAt(0);
            if (!workbook.getClass().getName().equals(workbook2.getClass().getName())) {
                throw new RuntimeException("不同类型的文件不能相互转换！");
            }

//            Workbook newBook = new XSSFWorkbook();
            Workbook newBook = new HSSFWorkbook();
            Sheet newSheet1 = newBook.createSheet(sheet1.getSheetName());
            Sheet newSheet2 = newBook.createSheet(sheet2.getSheetName());
            copySheet(sheet1, newSheet1);
            copySheet(sheet2, newSheet2);
            newBook.write(new FileOutputStream("D:\\test\\test\\test3.xls"));
            // 合并xlsx
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 新增sheet，并且复制sheet内容到新增的sheet里
     */
    private static void copySheet(Sheet fromsheet, Sheet newSheet) {
        int firstrow = fromsheet.getFirstRowNum();
        int lasttrow = fromsheet.getLastRowNum();
        if ((firstrow == -1) || (lasttrow == -1) || lasttrow < firstrow) {
            return;
        }
        // 复制一个单元格样式到新建单元格
        // 复制合并的单元格
        CellRangeAddress region = null;
        for (int i = 0; i < fromsheet.getNumMergedRegions(); i++) {
            region = fromsheet.getMergedRegion(i);
            if ((region.getFirstRow() >= firstrow) && (region.getLastRow() <= lasttrow)) {
                newSheet.addMergedRegion(region);
            }
        }
        Row fromRow = null;
        Row newRow = null;
        Cell newCell = null;
        Cell fromCell = null;
        // 设置列宽
        for (int i = firstrow; i < lasttrow; i++) {
            fromRow = fromsheet.getRow(i);
            if (fromRow != null) {
                for (int j = fromRow.getLastCellNum(); j >= fromRow.getFirstCellNum(); j--) {
                    int colnum = fromsheet.getColumnWidth((short) j);
                    if (colnum > 100) {
                        newSheet.setColumnWidth((short) j, (short) colnum);
                    }
                    if (colnum == 0) {
                        newSheet.setColumnHidden((short) j, true);
                    } else {
                        newSheet.setColumnHidden((short) j, false);
                    }
                }
                break;
            }
        }
        // 复制行并填充数据
        for (int i = 0; i <= lasttrow; i++) {
            fromRow = fromsheet.getRow(i);
            if (fromRow == null) {
                continue;
            }
            newRow = newSheet.createRow(i - firstrow);
            newRow.setHeight(fromRow.getHeight());
            for (int j = fromRow.getFirstCellNum(); j < fromRow.getLastCellNum(); j++) {
                fromCell = fromRow.getCell((short) j);
                if (fromCell == null) {
                    continue;
                }
                newCell = newRow.createCell((short) j);
                CellStyle style = newSheet.getWorkbook().createCellStyle();
                style.cloneStyleFrom(fromCell.getCellStyle());
                newCell.setCellStyle(style);
                CellType cType = fromCell.getCellType();
                switch (cType) {
                    case STRING:
                        newCell.setCellValue(fromCell.getRichStringCellValue());
                        break;
                    case NUMERIC:
                        newCell.setCellValue(fromCell.getNumericCellValue());
                        break;
                    case FORMULA:
                        newCell.setCellValue(fromCell.getCellFormula());
                        break;
                    case BOOLEAN:
                        newCell.setCellValue(fromCell.getBooleanCellValue());
                        break;
                    case ERROR:
                        newCell.setCellValue(fromCell.getErrorCellValue());
                        break;
                    default:
                        newCell.setCellValue(fromCell.getRichStringCellValue());
                        break;
                }
            }
        }
    }
}
