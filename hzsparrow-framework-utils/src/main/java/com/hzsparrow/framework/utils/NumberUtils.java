/**
 * $Id:$
 * Copyright 2019 HellerZhang. All rights reserved.
 */
package com.hzsparrow.framework.utils;

/**
 * 数字转字符串工具类
 * 
 * @author Heller.Zhang
 * @since 2019年5月7日 上午9:16:05
 */
public class NumberUtils {

    /**
     * 数字转换为字符串
     * 
     * @param num 数字
     * @return 字符串,如果 num 为空, 返回空字符串
     * @author Heller.Zhang
     * @since 2019年5月7日 上午9:16:14
     */
    public static String num2Str(Object num) {
        String str = null;

        if (num == null) {
            str = null;
        } else {
            str = String.valueOf(num);
        }
        return str;
    }

    /**
     * 字符串转换为Integer
     * 
     * @param obj 字符串
     * @return Integer, str为null时返回0
     * @author Heller.Zhang
     * @since 2019年5月7日 上午9:16:42
     */
    public static Integer getInteger(Object obj) {
        return getInteger(obj, 0);
    }

    /**
     * 字符串转换为Integer
     * 
     * @param str 字符串
     * @param def 默认值
     * @return Integer, 字符串为null时返回def
     */
    public static Integer getInteger(Object obj, int def) {
        String str = obj == null ? "" : obj.toString();

        Integer i = null;

        if (str.trim().length() == 0) {
            i = new Integer(def);
        } else {
            try {
                i = Integer.valueOf(str);
            } catch (Exception e) {}
        }

        return i == null ? new Integer(def) : i;
    }

    /**
     * 字符串转换为Long
     * 
     * @param str 字符串
     * @return Long, str为null时返回0
     */
    public static Long getLong(Object obj) {
        return getLong(obj, 0);
    }

    /**
     * 字符串转换为Long
     * 
     * @param str 字符串
     * @param def 默认值
     * @return Long, 字符串为null时返回def
     */
    public static Long getLong(Object obj, long def) {
        String str = obj == null ? "" : obj.toString();

        Long l = null;

        if (str.trim().length() == 0) {
            l = new Long(def);
        } else {
            try {
                l = Long.valueOf(str);
            } catch (Exception e) {}
        }

        return l == null ? new Long(def) : l;
    }

    /**
     * 字符串转换为Integer
     * 
     * @param str 字符串
     * @return Integer, str为null时返回0
     */
    public static int getIntegerValue(Object obj) {
        return getIntegerValue(obj, 0);
    }

    /**
     * 字符串转换为Integer
     * 
     * @param str 字符串
     * @param def 默认值
     * @return Integer, 字符串为null时返回def
     */
    public static int getIntegerValue(Object obj, int def) {
        return getInteger(obj, def).intValue();
    }

    /**
     * 字符串转换为Long
     * 
     * @param str 字符串
     * @return Long, str为null时返回0
     */
    public static long getLongValue(Object obj) {
        return getLongValue(obj, 0);
    }

    /**
     * 字符串转换为Long
     * 
     * @param str 字符串
     * @param def 默认值
     * @return Long, 字符串为null时返回def
     */
    public static long getLongValue(Object obj, long def) {
        return getLong(obj, def).longValue();
    }
}
