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
     * @param request
     * @return
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
     * @param request
     * @return
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
     * @param request
     * @return
     * @author Heller.Zhang
     * @since 2019年3月6日 上午9:31:14
     */
    public static String getQueryParm(HttpServletRequest request) {
        boolean hasPassword = WebUtils.hasSubmitParameter(request, "password");
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(request.getRequestURI())
                .query(request.getQueryString());
        if (hasPassword) {
            uriComponentsBuilder.replaceQueryParam("password", "********");
        }
        return uriComponentsBuilder.toUriString();
    }

    /**
     * 获取访问者IP
     * 
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     * 
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)， 如果还不存在则调用Request
     * 
     * @param request
     * @return
     * @author Heller.Zhang
     * @since 2019年3月6日 上午9:32:30
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip) && StringUtils.contains(ip, ",")) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            ip = StringUtils.substringBefore(ip, ",");
        }
        // 处理localhost访问
        if (StringUtils.isBlank(ip) || "unkown".equalsIgnoreCase(ip) || StringUtils.split(ip, ".").length != 4) {
            try {
                InetAddress inetAddress = InetAddress.getLocalHost();
                ip = inetAddress.getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return ip;
    }

    /**
     * 获取请求的域名
     * 
     * @param request
     * @return
     * @author Heller.Zhang
     * @since 2019年3月6日 上午9:33:30
     */
    public static String getDomain(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        if (StringUtils.isNotEmpty(contextPath)) {
            return contextPath;
        } else {
            if (request.getServerPort() == 80 || request.getServerPort() == 443) {
                return request.getScheme() + "://" + request.getServerName() + request.getContextPath();
            } else {
                return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                        + request.getContextPath();
            }
        }
    }

}
