package com.hzsparrow.framework.excel.exception;

public class SheetNamesNotCorrespondException extends RuntimeException {

    public SheetNamesNotCorrespondException() {
        super("工作表与工作表名称不相对应，请检查参数是否正确！");
    }
}
