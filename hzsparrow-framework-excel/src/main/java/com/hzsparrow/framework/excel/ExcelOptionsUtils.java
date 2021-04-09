package com.hzsparrow.framework.excel;


import com.hzsparrow.framework.excel.exception.SheetNamesNotCorrespondException;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * excel操作工具
 */
public class ExcelOptionsUtils {

    /**
     * 合并工作表到新的文件，完整合并（将多个sheet合并到一个sheet中，只合并数据，不合并样式）
     *
     * @param sheets    要合并的sheet
     * @param sheetName 合并后的sheet名
     * @param outPath   输出文件路径，例如:d:/123.xlsx
     */
    public static void mergeSheetTo(Sheet[] sheets, String sheetName, String outPath) throws IOException {
        mergeSheetTo(sheets, sheetName, outPath, null, false, null, null);
    }

    /**
     * 合并工作表到新的文件（将多个sheet合并到一个sheet中，只合并数据，不合并样式）
     *
     * @param sheets        要合并的sheet
     * @param sheetName     合并后的sheet名
     * @param outPath       输出文件路径，例如:d:/123.xlsx
     * @param beginRowNum   开始行数，第一个sheet固定从第一行开始，之后的从指定行开始。数字从0开始。
     * @param isEmptyEnd    是否空行结束
     * @param endChar       结束行字符标识
     * @param stateCallback 合并进度回调，以sheet数量为准
     */
    public static void mergeSheetTo(Sheet[] sheets, String sheetName, String outPath, Integer beginRowNum, boolean isEmptyEnd, String endChar,
                                    CallBack<Integer> stateCallback) throws IOException {
        // 创建新的工作簿
        Workbook newBook = createNewWorkbookByPath(outPath);
        // 复制
        Sheet newSheet = newBook.createSheet(sheetName);
        int rowNum = 0;
        for (int i = 0; i < sheets.length; i++) {
            if (i == 0) {
                rowNum += mergeSheet(sheets[i], newSheet, 0, rowNum, isEmptyEnd, endChar);
            } else {
                rowNum += mergeSheet(sheets[i], newSheet, beginRowNum, rowNum, isEmptyEnd, endChar);
            }
            if (stateCallback != null) {
                stateCallback.callBack(i + 1);
            }
        }
        FileOutputStream outputStream = new FileOutputStream(outPath);
        newBook.write(outputStream);
        newBook.close();
        outputStream.close();
    }

    /**
     * 将指定sheet合并到新sheet，该方法不负责关闭sheet的资源占用
     *
     * @param fromSheet     源sheet
     * @param newSheet      新sheet
     * @param beginRowNum   源sheet的起始行，为空时将自动获取源sheet的第一行
     * @param toBeginRowNum 新sheet的起始行,为空时默认为0，第一行。
     * @param isEmptyEnd    是否空行结束
     * @param endChar       结束行字符标识
     * @return 本次复制了多少行数据
     */
    public static int mergeSheet(Sheet fromSheet, Sheet newSheet, Integer beginRowNum, Integer toBeginRowNum, boolean isEmptyEnd, String endChar) {
        int firstRow;
        if (beginRowNum == null) {
            firstRow = fromSheet.getFirstRowNum();
        } else {
            firstRow = beginRowNum;
        }
        if (toBeginRowNum == null) {
            toBeginRowNum = 0;
        }

        int lastRow = fromSheet.getLastRowNum();
        if ((firstRow == -1) || (lastRow == -1) || lastRow < firstRow) {
            return 0;
        }
        // 复制一个单元格样式到新建单元格
        // 复制合并的单元格
        CellRangeAddress region = null;
        for (int i = 0; i < fromSheet.getNumMergedRegions(); i++) {
            region = fromSheet.getMergedRegion(i);
            if ((region.getFirstRow() >= firstRow) && (region.getLastRow() <= lastRow)) {
                region.setFirstRow(region.getFirstRow() + toBeginRowNum);
                region.setLastRow(region.getLastColumn() + toBeginRowNum);
                newSheet.addMergedRegion(region);
            }
        }
        String[] endChars = null;
        if (endChar != null && endChar.trim() != "") {
            endChars = endChar.split(",");
        }
        // 设置列宽
        if (toBeginRowNum == 0) {
            setColumnWidth(fromSheet, newSheet, firstRow, lastRow);
        }
        // 复制行并填充数据
        for (int i = firstRow; i <= lastRow; i++) {
            Row fromRow = fromSheet.getRow(i);

            if (fromRow == null) {
                if (isEmptyEnd) {
                    lastRow = i - 1;
                    break;
                }
                continue;
            }
            if (isEmptyRow(fromRow)) {
                if (isEmptyEnd) {
                    lastRow = i - 1;
                    break;
                }
            } else {
                Cell firstCell = fromRow.getCell(0);
                boolean isFirstEmpty = isEmptyCell(firstCell);
                if (!isFirstEmpty) {
                    // 当第一个单元格不为空时，判断是否以结束标志结束
                    boolean isString = firstCell.getCellType().compareTo(CellType.STRING) == 0;
                    if (isString) {
                        if (endChars != null) {
                            // 当结束标识不为空，并且第一个单元格不为空，并且单元格类型为字符串时，判断单元格内容是否标识，如果匹配则跳出
                            boolean isEnd = false;
                            for (String endCharItem : endChars) {
                                if (endCharItem.equals(firstCell.getRichStringCellValue().getString())) {
                                    isEnd = true;
                                    break;
                                }
                            }
                            if (isEnd) {
                                lastRow = i - 1;
                                break;
                            }
                        }
                    }
                }
            }
            Row newRow = newSheet.createRow(i - firstRow + toBeginRowNum);
            copyRow(fromRow, newRow, false);
        }
        return lastRow + 1 - beginRowNum;
    }

    /**
     * 判断是否空行，前20个单元格为空也算空行
     *
     * @param row
     * @return
     */
    private static boolean isEmptyRow(Row row) {
        if (row == null) {
            return true;
        }
        Cell firstCell = row.getCell(0);
        if (isEmptyCell(firstCell)) {
            // 当第一个是空的时候，判断后19个是否全部为空
            boolean isAllEmpty = true;
            for (int cellIndex = 1; cellIndex < 20; cellIndex++) {
                Cell nextCell = row.getCell(cellIndex);
                if (!isEmptyCell(nextCell)) {
                    isAllEmpty = false;
                    break;
                }
            }
            return isAllEmpty;
        }
        // 第一个不为空则直接判断为不为空
        return false;
    }

    /**
     * 判断单元格是否空值
     *
     * @param cell
     * @return
     */
    private static boolean isEmptyCell(Cell cell) {
        if (cell == null) {
            return true;
        }
        boolean isString = cell.getCellType().compareTo(CellType.STRING) == 0;
        if (isString) {
            RichTextString richTextString = cell.getRichStringCellValue();
            String result = richTextString.getString();
            return result != null && result.trim() != "";
        } else {
            return false;
        }
    }

    /**
     * 复制列宽
     *
     * @param fromSheet 源sheet
     * @param newSheet  新sheet
     * @param firstRow  首列（包含）
     * @param lastRow   末列（不包含）
     */
    private static void setColumnWidth(Sheet fromSheet, Sheet newSheet, int firstRow, int lastRow) {
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
    }

    /**
     * 复制多个sheet到新的文件,只能复制相同的文件类型
     *
     * @param sheets  要复制的sheet
     * @param names   新sheet名
     * @param outPath 新文件输出路径，例如:d:/123.xlsx
     */
    public static void copySheetTo(Sheet[] sheets, String[] names, String outPath, boolean isCloneStyle, CallBack<Integer> stateCallback) throws IOException {
        copySheetTo(sheets, names, outPath, null, isCloneStyle, stateCallback);
    }

    /**
     * 复制多个sheet到新的文件,只能复制相同的文件类型
     *
     * @param sheets        要复制的sheet
     * @param names         新sheet名
     * @param outPath       新文件输出路径，例如:d:/123.xlsx
     * @param beginRowNum   起始行号，数据从0开始，当为空时，自动获取sheet的第一行
     * @param isCloneStyle  是否复制样式
     * @param stateCallback 进度回调（以sheet数量为准）
     */
    public static void copySheetTo(Sheet[] sheets, String[] names, String outPath, Integer beginRowNum, boolean isCloneStyle, CallBack<Integer> stateCallback) throws IOException {
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
        if (isCloneStyle) {
            // 检查文件类型是否相同
            for (Sheet sheet : sheets) {
                if (!sheet.getWorkbook().getClass().getName().equals(newBook.getClass().getName())) {
                    throw new RuntimeException("不同类型的文件不能相互转换！");
                }
            }
        }
        // 复制
        for (int i = 0; i < sheets.length; i++) {
            Sheet newSheet = newBook.createSheet(names[i]);
            copySheet(sheets[i], newSheet, beginRowNum, isCloneStyle);
            if (stateCallback != null) {
                stateCallback.callBack(i + 1);
            }
        }
        FileOutputStream outputStream = new FileOutputStream(outPath);
        newBook.write(outputStream);
        newBook.close();
        outputStream.close();
    }

    /**
     * 复制Sheet到新的Sheet
     *
     * @param fromSheet    源sheet
     * @param newSheet     新sheet
     * @param isCloneStyle 是否复制样式
     */
    public static void copySheet(Sheet fromSheet, Sheet newSheet, boolean isCloneStyle) {
        copySheet(fromSheet, newSheet, null, isCloneStyle);
    }

    /**
     * 复制Sheet到新的Sheet
     *
     * @param fromSheet    源sheet
     * @param newSheet     新sheet
     * @param beginRowNum  起始行号，数字从0开始，为空时自动获取源sheet的首行行号
     * @param isCloneStyle 是否复制样式
     */
    public static void copySheet(Sheet fromSheet, Sheet newSheet, Integer beginRowNum, boolean isCloneStyle) {
        int firstRow;
        if (beginRowNum == null) {
            firstRow = fromSheet.getFirstRowNum();
        } else {
            firstRow = beginRowNum;
        }

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
        setColumnWidth(fromSheet, newSheet, firstRow, lastRow);
        // 复制行并填充数据
        for (int i = firstRow; i <= lastRow; i++) {
            Row fromRow = fromSheet.getRow(i);
            if (fromRow == null) {
                continue;
            }
            Row newRow = newSheet.createRow(i - firstRow);
            copyRow(fromRow, newRow, isCloneStyle);
        }
    }

    /**
     * 复制行到新行
     *
     * @param fromRow      源行
     * @param newRow       新行
     * @param isCloneStyle 是否复制样式
     */
    public static void copyRow(Row fromRow, Row newRow, boolean isCloneStyle) {
        newRow.setHeight(fromRow.getHeight());
        for (int j = fromRow.getFirstCellNum(); j < fromRow.getLastCellNum(); j++) {
            Cell fromCell = fromRow.getCell((short) j);
            if (fromCell == null) {
                continue;
            }
            Cell newCell = newRow.createCell((short) j);
            copyCell(fromCell, newCell, isCloneStyle);
        }
    }

    /**
     * 复制单元格
     *
     * @param fromCell     源单元格
     * @param newCell      新单元格
     * @param isCloneStyle 是否复制样式
     */
    public static void copyCell(Cell fromCell, Cell newCell, boolean isCloneStyle) {
        if (isCloneStyle) {
            CellStyle style = newCell.getRow().getSheet().getWorkbook().createCellStyle();
            style.cloneStyleFrom(fromCell.getCellStyle());
            newCell.setCellStyle(style);
        }
        CellType cType = fromCell.getCellType();
        switch (cType) {
            case STRING:
                newCell.setCellValue(fromCell.getRichStringCellValue().getString());
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
                newCell.setCellValue(fromCell.getRichStringCellValue().getString());
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

    /**
     * 获取已存在文件的WorkBook对象
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbook(String path) throws IOException {
        Workbook workbook = WorkbookFactory.create(new File(path));
        return workbook;
    }
}
