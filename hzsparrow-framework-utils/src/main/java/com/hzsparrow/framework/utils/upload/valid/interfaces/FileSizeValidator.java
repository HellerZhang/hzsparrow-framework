package com.hzsparrow.framework.utils.upload.valid.interfaces;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件大小检查器
 */
@FunctionalInterface
public interface FileSizeValidator {

    /**
     * 验证文件大小
     *
     * @param file
     * @return 验证通过返回true, 否则返回false
     */
    boolean validFileSize(MultipartFile file);
}
