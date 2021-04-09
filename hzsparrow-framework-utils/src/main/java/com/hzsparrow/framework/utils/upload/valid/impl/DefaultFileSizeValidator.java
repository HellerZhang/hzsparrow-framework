package com.hzsparrow.framework.utils.upload.valid.impl;

import com.hzsparrow.framework.utils.upload.valid.exception.FileSizeMoreThanMaxSizeException;
import com.hzsparrow.framework.utils.upload.valid.interfaces.FileSizeValidator;
import org.springframework.web.multipart.MultipartFile;

/**
 * 默认文件大小检查器
 */
public class DefaultFileSizeValidator implements FileSizeValidator {

    private Long maxFileSize = 1073741824L;

    /**
     * 验证文件大小是否超过最大限制
     *
     * @param file
     * @return 超过时返回false，否则返回true
     */
    @Override
    public boolean validFileSize(MultipartFile file) {
        if (file.getSize() > maxFileSize) {
            throw new FileSizeMoreThanMaxSizeException(maxFileSize);
        }
        return true;
    }

    public Long getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(Long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }
}
