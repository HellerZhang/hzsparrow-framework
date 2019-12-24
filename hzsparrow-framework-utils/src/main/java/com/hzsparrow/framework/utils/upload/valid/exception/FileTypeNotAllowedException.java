package com.hzsparrow.framework.utils.upload.valid.exception;

import org.apache.commons.lang3.StringUtils;

public class FileTypeNotAllowedException extends RuntimeException {

    private String fileType;

    public FileTypeNotAllowedException(String fileType) {
        super();
        this.fileType = fileType;
    }

    public FileTypeNotAllowedException(String fileType, String message) {
        super(message);
        this.fileType = fileType;
    }

    public FileTypeNotAllowedException(String fileType, String message, Throwable cause) {
        super(message, cause);
        this.fileType = fileType;
    }

    public FileTypeNotAllowedException(String fileType, Throwable cause) {
        super(cause);
        this.fileType = fileType;
    }

    protected FileTypeNotAllowedException(String fileType, String message, Throwable cause,
                                          boolean enableSuppression,
                                          boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        if (StringUtils.isBlank(message)) {
            return fileType + "格式的文件禁止上传！";
        }
        return message;
    }
}
