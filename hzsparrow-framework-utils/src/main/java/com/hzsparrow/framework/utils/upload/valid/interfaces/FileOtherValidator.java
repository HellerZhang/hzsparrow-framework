package com.hzsparrow.framework.utils.upload.valid.interfaces;

import org.springframework.web.multipart.MultipartFile;

/**
 * 其他文件校验器
 */
public interface FileOtherValidator {

    /**
     * 校验
     *
     * @param file
     * @return 校验成功返回true, 否则返回false
     */
    boolean validate(MultipartFile file);
}
