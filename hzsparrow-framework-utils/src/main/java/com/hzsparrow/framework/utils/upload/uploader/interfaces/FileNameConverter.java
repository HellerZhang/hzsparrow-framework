package com.hzsparrow.framework.utils.upload.uploader.interfaces;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件名转换器
 */
@FunctionalInterface
public interface FileNameConverter {

    /**
     * 获取转换之后的文件名
     *
     * @param request
     * @param name
     * @return
     */
    String getConvertedFileName(HttpServletRequest request, String name);
}
