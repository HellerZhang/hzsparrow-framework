/**
 * $Id:$
 * Copyright 2019-2019 ShiJiaZhuang ShanZhouWangLuo Technology Company Ltd. All rights reserved.
 */
package com.hzsparrow.framework.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * 
 * @author Heller.Zhang
 * @since 2019年6月12日 下午12:25:07
 */
public class RegexUtils {

    /**
     * 正则表达式获取匹配到的内容
     * 
     * @param regex
     * @param source
     * @return
     * @author Heller.Zhang
     * @since 2019年6月12日 下午12:25:53
     */
    public static String matcherGroup(String regex, String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return null;
        }
    }

    /**
     * 判断是否匹配
     * 
     * @param regex
     * @param source
     * @return
     * @author Heller.Zhang
     * @since 2019年6月13日 下午7:53:06
     */
    public static boolean isMatch(String regex, String source) {
        if (StringUtils.isBlank(source)) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        return matcher.find();
    }
}
