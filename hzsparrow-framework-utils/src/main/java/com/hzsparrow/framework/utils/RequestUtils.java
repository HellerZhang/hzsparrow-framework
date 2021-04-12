/**
 * $Id:$
 * Copyright 2014-2018 Hebei Sinounited Technology Company Ltd. All rights reserved.
 */
package com.hzsparrow.framework.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 请求对象处理工具
 * 
 * @author Heller.Zhang
 * @since 2018年9月8日 下午8:40:26
 */
public class RequestUtils {

    /**
     * 获取Request中的所有参数
     * 
     * @param request 请求对象
     * @return 参数集
     * @author Heller.Zhang
     * @since 2018年9月8日 下午8:42:02
     */
    public static Map<String, String> getAllParam(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();

        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return params;
    }

    /**
     * 是否Ajax请求
     * 
     * @param request 请求对象
     * @return boolean
     * @author Heller.Zhang
     * @since 2019年3月6日 上午9:30:40
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("x-requested-with");
        if (StringUtils.isEmpty(requestedWith)) {
            return false;
        } else if (StringUtils.isNotEmpty(requestedWith) && requestedWith.equals("XMLHttpRequest")) {
            return true;
        }
        return false;
    }

    /**
     * 获取请求的URI参数字符串
     * 
     * @param request 请求对象
     * @return 将请求中的password字段替换为***后的参数字符串
     * @author Heller.Zhang
     * @since 2019年3月6日 上午9:31:14
     */
    public static String getQueryParam(HttpServletRequest request) {
        boolean hasPassword = WebUtils.hasSubmitParameter(request, "password");
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(request.getRequestURI())
                .query(request.getQueryString());
        if (hasPassword) {
            uriComponentsBuilder.replaceQueryParam("password", "********");
        }
        return uriComponentsBuilder.toUriString();
    }

}
