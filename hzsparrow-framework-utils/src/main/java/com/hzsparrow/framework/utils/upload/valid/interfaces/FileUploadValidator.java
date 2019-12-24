package com.hzsparrow.framework.utils.upload.valid.interfaces;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传校验器
 */
@FunctionalInterface
public interface FileUploadValidator {

    /**
     * 校验
     *
     * @param file
     * @return 校验成功时返回true, 否则返回false
     */
    boolean validator(MultipartFile file);
}
