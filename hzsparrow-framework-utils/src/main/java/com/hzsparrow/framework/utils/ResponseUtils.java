package com.hzsparrow.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 响应信息操作工具类
 * 
 * @author Heller.Zhang
 * @since 2019年3月6日 上午9:35:16
 */
public final class ResponseUtils {

    public static final Logger LOG = LoggerFactory.getLogger(ResponseUtils.class);

    /**
     * 写入文字
     * 
     * @param response
     * @param text
     * @author Heller.Zhang
     * @since 2019年3月6日 上午9:35:34
     */
    public static void renderText(HttpServletResponse response, String text) {
        render(response, "text/plain;charset=UTF-8", text);
    }

    /**
     * 写入json串
     * 
     * @param response
     * @param text
     * @author Heller.Zhang
     * @since 2019年3月6日 上午9:35:43
     */
    public static void renderJson(HttpServletResponse response, String text) {
        render(response, "application/json;charset=UTF-8", text);
    }

    /**
     * 写入xml
     * 
     * @param response
     * @param text
     * @author Heller.Zhang
     * @since 2019年3月6日 上午9:35:57
     */
    public static void renderXml(HttpServletResponse response, String text) {
        render(response, "text/xml;charset=UTF-8", text);
    }

    /**
     * 写入信息到response
     * 
     * @param response 响应对象
     * @param contentType 数据类型
     * @param text 数据
     * @author Heller.Zhang
     * @since 2019年3月6日 上午9:36:06
     */
    public static void render(HttpServletResponse response, String contentType, String text) {
        response.setContentType(contentType);
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        try {
            response.getWriter().write(text);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
