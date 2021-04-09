package com.hzsparrow.framework.utils.upload.valid.interfaces;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件类型校验器
 */
@FunctionalInterface
public interface FileTypeValidator {

    /**
     * 校验文件类型
     *
     * @param file 文件
     * @return 返回true为通过，false为不通过
     */
    boolean validFileType(MultipartFile file);
}
