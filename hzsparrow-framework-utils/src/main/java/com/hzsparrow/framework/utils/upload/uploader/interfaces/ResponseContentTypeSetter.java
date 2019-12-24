package com.hzsparrow.framework.utils.upload.uploader.interfaces;

import javax.servlet.http.HttpServletResponse;

/**
 * 响应表头内容类型设置器
 */
@FunctionalInterface
public interface ResponseContentTypeSetter {

    /**
     * 设置内容类型
     *
     * @param response
     * @param fileName
     */
    void setContentType(HttpServletResponse response, String fileName);
}
