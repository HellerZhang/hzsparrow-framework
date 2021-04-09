/**

 * $Id:$
 * Copyright 2019-2019 ShiJiaZhuang ShanZhouWangLuo Technology Company Ltd. All rights reserved.
 */
package com.hzsparrow.framework.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 汉字转拼音工具类
 * 
 * @author Heller.Zhang
 * @since 2019年6月12日 下午7:21:35
 */
public class PinyinUtils {

    private static final Logger logger = LoggerFactory.getLogger(PinyinUtils.class);

    private static final String regex = "[\\u4e00-\\u9fa5]";

    /**
     * 设置一个常用的输出格式
     */
    private static HanyuPinyinOutputFormat getDefaultOutputFormat() {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 小写
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 没有音调数字
        format.setVCharType(HanyuPinyinVCharType.WITH_V);// u显示
        return format;
    }

    /**
     * 首字母转大写
     */
    private static String upperCaseFirst(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 返回一个汉字的拼音，全部为小写无音调。如：绿（lv）
     * 
     * @param ch 汉字
     * @return 拼音
     * @author jades.he 2014-8-28
     */
    public static String getCharPinyin(char ch) {
        HanyuPinyinOutputFormat format = getDefaultOutputFormat();

        String[] py = null;
        try {
            py = PinyinHelper.toHanyuPinyinStringArray(ch, format);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        // 当转换不是中文字符时,返回null
        return (py == null) ? null : py[0];
    }

    /**
     * 获得一个汉语字符串的拼音，首字母大写，如：你好（NiHao）
     * 
     * @param chinese 汉语字符串
     * @return 汉语拼音
     * @author jades.he 2014-8-28
     */
    public static String getFull(String chinese) {
        if (StringUtils.isBlank(chinese)) {
            return chinese;
        }

        StringBuilder pinyin = new StringBuilder();

        for (int i = 0; i < chinese.length(); ++i) {
            char indexChar = chinese.charAt(i);
            String py = String.valueOf(indexChar);
            if (RegexUtils.isMatch(regex, py)) {
                py = upperCaseFirst(getCharPinyin(indexChar));
            }
            // 当转换不是中文字符时,返回null
            if (py != null) {
                pinyin.append(py);
            } else {
                pinyin.append(chinese.charAt(i));
            }
        }

        return pinyin.toString();
    }

    /**
     * 获得一个汉字字符串的简拼，如：你好（NH）
     * 
     * @param chinese 汉语字符串
     * @return 汉语拼音的大写首字母
     * @author jades.he 2014-8-28
     */
    public static String getSpell(String chinese) {
        if (StringUtils.isBlank(chinese)) {
            return chinese;
        }

        StringBuilder pinyin = new StringBuilder();

        for (int i = 0; i < chinese.length(); ++i) {
            char indexChar = chinese.charAt(i);
            String py = String.valueOf(indexChar);
            if (RegexUtils.isMatch(regex, py)) {
                py = upperCaseFirst(getCharPinyin(indexChar));
            }
            // 当转换不是中文字符时,返回null
            if (py != null) {
                pinyin.append(py.charAt(0));
            } else {
                pinyin.append(chinese.charAt(i));
            }
        }

        return pinyin.toString();
    }

    /**
     * 获取大写的首字母
     * 
     * @param chinese
     * @return
     * @author Heller.Zhang
     * @since 2019年6月12日 下午7:27:43
     */
    public static String getInitials(String chinese) {
        if (StringUtils.isBlank(chinese)) {
            return chinese;
        }
        char indexChar = chinese.charAt(0);
        String py = String.valueOf(indexChar);
        if (RegexUtils.isMatch(regex, py)) {
            py = upperCaseFirst(getCharPinyin(indexChar));
        }
        return String.valueOf(py.charAt(0));
    }

    public static void main(String[] args) {
        System.out.println(PinyinUtils.getFull("0Ab北京Ab0"));
        System.out.println(PinyinUtils.getSpell("0Ab北京Ab0"));
        System.out.println(PinyinUtils.getInitials("0Ab北京Ab0"));
    }
}
