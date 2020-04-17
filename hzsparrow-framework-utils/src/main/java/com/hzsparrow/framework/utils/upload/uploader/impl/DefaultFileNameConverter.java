package com.hzsparrow.framework.utils.upload.uploader.impl;

import com.hzsparrow.framework.utils.upload.uploader.interfaces.FileNameConverter;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 默认文件名转换器
 */
public class DefaultFileNameConverter implements FileNameConverter {

    /**
     * 默认文件名转换，对IE和非IE浏览器做适配
     *
     * @param request
     * @param name
     * @return
     */
    @Override
    public String getConvertedFileName(HttpServletRequest request, String name) {
        String result;
        boolean isIe11 = false;
        boolean isIe = false;
        if (request != null) {
            String userAgent = request.getHeader("User-Agent");
            isIe11 = userAgent.indexOf("like Gecko") > 0;
            isIe = userAgent.indexOf("MSIE") > 0;
        }
        try {
            if (isIe || isIe11) {
                result = URLEncoder.encode(name, "UTF-8");
                result = result.replaceAll("\\+", "%20");
                result = result.replaceAll("\\%2b", "+");
            } else {
                result = new String(name.replaceAll(" ", "").getBytes("UTF-8"), "ISO8859-1");
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("文件名错误！");
        }
        return result;
    }
}
