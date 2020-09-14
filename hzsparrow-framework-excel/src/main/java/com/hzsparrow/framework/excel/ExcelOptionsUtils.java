package com.hzsparrow.framework.excel;

import com.hzsparrow.framework.excel.exception.SheetNamesNotCorrespondException;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * excel操作工具
 */
public class ExcelOptionsUtils {

    /**
     * 复制多个sheet到新的文件
     *
     * @param sheets
     * @param names
     * @param outPath
     */
    public static void copySheetTo(Sheet[] sheets, String[] names, String outPath) throws IOException {
        if (names != null) {
            // 检查工作表和名称是否对应
            if (sheets.length != names.length) {
                throw new SheetNamesNotCorrespondException();
            }
        } else {
            names = new String[sheets.length];
            for (int i = 0; i < sheets.length; i++) {
                Sheet sheet = sheets[i];
                String name = sheet.getSheetName();
                names[i] = name;
            }
        }
        // 检查名称重复
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            for (int j = 0; j < i; j++) {
                if (StringUtils.equals(name, names[j])) {
                    name = name + "_1";
                    // 检查到名称重复时，修改名称，再查一遍
                    j = 0;
                }
            }
            names[i] = name;
        }
        // 创建新的工作簿
        Workbook newBook = createNewWorkbookByPath(outPath);
        // 检查文件类型是否相同
        for (Sheet sheet : sheets) {
            if (!sheet.getWorkbook().getClass().getName().equals(newBook.getClass().getName())) {
                throw new RuntimeException("不同类型的文件不能相互转换！");
            }
        }
        // 复制
        for (int i = 0; i < sheets.length; i++) {
            Sheet newSheet = newBook.createSheet(names[i]);
            copySheet(sheets[i], newSheet);
        }
        newBook.write(new FileOutputStream(outPath));
        newBook.close();
    }

    /**
     * 复制Sheet到新的Sheet
     */
    public static void copySheet(Sheet fromSheet, Sheet newSheet) {
        int firstRow = fromSheet.getFirstRowNum();
        int lastRow = fromSheet.getLastRowNum();
        if ((firstRow == -1) || (lastRow == -1) || lastRow < firstRow) {
            return;
        }
        // 复制一个单元格样式到新建单元格
        // 复制合并的单元格
        CellRangeAddress region = null;
        for (int i = 0; i < fromSheet.getNumMergedRegions(); i++) {
            region = fromSheet.getMergedRegion(i);
            if ((region.getFirstRow() >= firstRow) && (region.getLastRow() <= lastRow)) {
                newSheet.addMergedRegion(region);
            }
        }
        // 设置列宽
        for (int i = firstRow; i < lastRow; i++) {
            Row fromRow = fromSheet.getRow(i);
            if (fromRow != null) {
                for (int j = fromRow.getLastCellNum(); j >= fromRow.getFirstCellNum(); j--) {
                    int columnWidth = fromSheet.getColumnWidth((short) j);
                    if (columnWidth > 100) {
                        newSheet.setColumnWidth((short) j, (short) columnWidth);
                    }
                    if (columnWidth == 0) {
                        newSheet.setColumnHidden((short) j, true);
                    } else {
                        newSheet.setColumnHidden((short) j, false);
                    }
                }
                break;
            }
        }
        // 复制行并填充数据
        for (int i = 0; i <= lastRow; i++) {
            Row fromRow = fromSheet.getRow(i);
            if (fromRow == null) {
                continue;
            }
            Row newRow = newSheet.createRow(i - firstRow);
            copyRow(fromRow, newRow);
        }
    }

    /**
     * 复制行到新行
     *
     * @param fromRow
     * @param newRow
     */
    public static void copyRow(Row fromRow, Row newRow) {
        newRow.setHeight(fromRow.getHeight());
        for (int j = fromRow.getFirstCellNum(); j < fromRow.getLastCellNum(); j++) {
            Cell fromCell = fromRow.getCell((short) j);
            if (fromCell == null) {
                continue;
            }
            Cell newCell = newRow.createCell((short) j);
            copyCell(fromCell, newCell);
        }
    }

    /**
     * 复制单元格
     *
     * @param fromCell
     * @param newCell
     */
    public static void copyCell(Cell fromCell, Cell newCell) {
        CellStyle style = newCell.getRow().getSheet().getWorkbook().createCellStyle();
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

    /**
     * 创建新的工作簿
     *
     * @param path
     * @return
     */
    public static Workbook createNewWorkbookByPath(String path) {
        String suffix = path.substring(path.lastIndexOf(".")).toLowerCase();
        if (StringUtils.equals(suffix, ".xls")) {
            return new HSSFWorkbook();
        } else if (StringUtils.equals(suffix, ".xlsx")) {
            return new XSSFWorkbook();
        } else {
            return null;
        }
    }
}
