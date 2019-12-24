package com.hzsparrow.framework.utils.upload.uploader.impl;

import com.hzsparrow.framework.utils.upload.uploader.interfaces.ResponseContentTypeSetter;

import javax.servlet.http.HttpServletResponse;

/**
 * 默认响应内容类型设置器
 */
public class DefaultResponseContentTypeSetter implements ResponseContentTypeSetter {

    /**
     * 设置响应内容类型
     *
     * @param response
     * @param fileName
     */
    @Override
    public void setContentType(HttpServletResponse response, String fileName) {
        String fileSuffix = fileName.substring(fileName.indexOf('.')).toLowerCase();
        switch (fileSuffix) {
            case ".xml":
                response.setContentType("text/xml");
                break;
            case ".jpeg":
                response.setContentType("image/jpeg");
                break;
            case ".gif":
                response.setContentType("image/gif");
                break;
            case ".bmp":
                response.setContentType("image/bmp");
                break;
            case ".xls":
            case ".xlsx":
                response.setContentType("application/vnd.ms-excel");
                break;
            case ".doc":
            case ".docx":
                response.setContentType("application/msword");
                break;
            case ".pdf":
                response.setContentType("application/pdf");
                break;
            default:
                response.setContentType("text/plain");
                break;
        }
    }
}
