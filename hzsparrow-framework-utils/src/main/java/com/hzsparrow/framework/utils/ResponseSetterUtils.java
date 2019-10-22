/**
 * $Id:$
 * Copyright 2019-2019 ShiJiaZhuang ShanZhouWangLuo Technology Company Ltd. All rights reserved.
 */
package com.hzsparrow.framework.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 响应信息设置类
 * 
 * @author Heller.Zhang
 * @since 2019年5月27日 下午6:52:36
 */
public class ResponseSetterUtils {

    public static final String TYPE_PDF = "application/pdf";

    public static final String TYPE_AFP = "application/afp";

    public static final String TYPE_RTF = "application/rtf";

    public static final String TYPE_MSWORD = "application/msword";

    public static final String TYPE_MSWORDX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

    public static final String TYPE_MSEXCEL = "application/vnd.ms-excel";

    public static final String TYPE_MSEXCELX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static final String TYPE_MSPOWERPOINT = "application/vnd.ms-powerpoint";

    public static final String TYPE_MSPOWERPOINTX = "application/vnd.openxmlformats-officedocument.presentationml.presentation";

    public static final String TYPE_WORDPERFECT = "application/wordperfect5.1";

    public static final String TYPE_WORDPRO = "application/vnd.lotus-wordpro";

    public static final String TYPE_VISIO = "application/vnd.visio";

    public static final String TYPE_FRAMEMAKER = "application/vnd.framemaker";

    public static final String TYPE_LOTUS123 = "application/vnd.lotus-1-2-3";

    public static final String TYPE_XML = "text/xml";

    public static final String TYPE_TXT = "text/plain";

    public static final String TYPE_HTML = "text/html";

    public static final String TYPE_JSON = "text/javascript";

    public static final String TYPE_PCX = "image/x-pcx";

    public static final String TYPE_DCX = "image/x-dcx";

    public static final String TYPE_TIFF = "image/tiff";

    public static final String TYPE_JPEG = "image/jpeg";

    public static final String TYPE_GIF = "image/gif";

    public static final String TYPE_BMP = "image/bmp";

    public static final String CHARSET_UTF8 = "UTF-8";

    public static final String CHARSET_GBK = "GBK";

    /**
     * 设置响应信息的contentType和charsetEncoding
     * 
     * @param response
     * @param contentType 可从静态变量中选取
     * @param charsetEncoding 可从静态变量中选取
     * @author Heller.Zhang
     * @since 2019年5月27日 下午6:53:39
     */
    public static void setContentType(HttpServletResponse response, String contentType, String charsetEncoding) {
        response.setContentType(contentType);
        if (charsetEncoding != null) response.setCharacterEncoding(charsetEncoding);
    }

    /**
     * 设置响应信息的contentType，charsetEncoding为UTF-8
     * 
     * @param response
     * @param fileName 文件名称（需带后缀）
     * @author Heller.Zhang
     * @since 2019年5月27日 下午6:55:35
     */
    public static void setUtf8ContentType(HttpServletResponse response, String fileName) {
        try {
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("设置文件名称失败！", e);
        }
        String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
        if (fileSuffix.equals("txt")) {
            response.setContentType(TYPE_TXT);
        } else if (fileSuffix.equals("xml")) {
            response.setContentType(TYPE_XML);
        } else if (fileSuffix.equals("jpeg") || fileSuffix.equals("jpg")) {
            response.setContentType(TYPE_JPEG);
        } else if (fileSuffix.equals("gif")) {
            response.setContentType(TYPE_GIF);
        } else if (fileSuffix.equals("bmp")) {
            response.setContentType(TYPE_BMP);
        } else if (fileSuffix.equals("xls") || fileSuffix.equals("xlsx")) {
            response.setContentType(TYPE_MSEXCEL);
        } else if (fileSuffix.equals("doc") || fileSuffix.equals("docx")) {
            response.setContentType(TYPE_MSWORD);
        } else if (fileSuffix.equals("pdf")) {
            response.setContentType(TYPE_PDF);
        } else {
            response.setContentType(TYPE_MSEXCEL);
        }
        response.setCharacterEncoding(CHARSET_UTF8);
    }

    /**
     * 将文件流写入到响应中
     * 
     * @param response
     * @param filePath
     * @author Heller.Zhang
     * @since 2019年5月27日 下午6:56:58
     */
    public static void writeFileStream2Response(HttpServletResponse response, String filePath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(filePath);
            int len = 0;
            byte[] buffer = new byte[1024 * 1024];
            out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
