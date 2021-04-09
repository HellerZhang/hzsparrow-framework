package com.hzsparrow.framework.utils.upload.valid.exception;

import org.apache.commons.lang3.StringUtils;

public class FileSizeMoreThanMaxSizeException extends RuntimeException {

    private Long byteMaxSize;

    public FileSizeMoreThanMaxSizeException(Long byteMaxSize) {
        super();
        this.byteMaxSize = byteMaxSize;
    }

    public FileSizeMoreThanMaxSizeException(Long byteMaxSize, String message) {
        super(message);
        this.byteMaxSize = byteMaxSize;
    }

    public FileSizeMoreThanMaxSizeException(Long byteMaxSize, String message, Throwable cause) {
        super(message, cause);
        this.byteMaxSize = byteMaxSize;
    }

    public FileSizeMoreThanMaxSizeException(Long byteMaxSize, Throwable cause) {
        super(cause);
        this.byteMaxSize = byteMaxSize;
    }

    protected FileSizeMoreThanMaxSizeException(Long byteMaxSize, String message, Throwable cause,
                                               boolean enableSuppression,
                                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.byteMaxSize = byteMaxSize;
    }

    public Long getByteMaxSize() {
        return byteMaxSize;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        if (StringUtils.isBlank(message)) {
            return "文件体积太大，以至于超过限制的最大值" + byteMaxSize + "Byte。";
        }
        return message;
    }
}
