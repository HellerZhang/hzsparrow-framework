/**
 * $Id:$
 * Copyright 2019-2019 ShiJiaZhuang ShanZhouWangLuo Technology Company Ltd. All rights reserved.
 */
package com.hzsparrow.framework.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ip工具类
 *
 * @author HellerZhang
 * @since 2019年7月4日 上午9:44:45
 */
public class IpUtils {

    /**
     * 获取客户端真实IP地址(可以获取反向代理的以及多级代理的)
     *
     * @param request 请求对象
     * @return ip地址
     * @author HellerZhang
     * @since 2019年7月4日 上午9:47:57
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP  
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
        if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15  
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**
     * 获取请求的域名
     *
     * @param request 请求对象
     * @return 域名
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
