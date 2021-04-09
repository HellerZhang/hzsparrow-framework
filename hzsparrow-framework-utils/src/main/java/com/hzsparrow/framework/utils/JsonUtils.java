/**
 * $Id:$
 * Copyright 2014-2019 Hebei Sinounited Technology Company Ltd. All rights reserved.
 */
package com.hzsparrow.framework.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ReflectionUtils;

import java.util.Map;

/**
 * Json工具
 * 
 * @author Heller.Zhang
 * @since 2019年5月7日 上午9:13:17
 */
public class JsonUtils {

    /**
     * 将对象序列化为Json字符串
     * 
     * @param value
     * @return
     * @author Heller.Zhang
     * @since 2019年5月7日 上午9:13:14
     */
    public static String serialize(Object value) {
        String json = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(value);
        } catch (Exception e) {
            ReflectionUtils.handleReflectionException(e);
        }

        return json;
    }

    /**
     * 解析Json字符串到Map中
     * 
     * @param value
     * @return
     * @author Heller.Zhang
     * @since 2019年5月7日 上午9:13:43
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> readValue(String value) {
        Map<String, Object> map = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            map = mapper.readValue(value, Map.class);
        } catch (Exception e) {
            ReflectionUtils.handleReflectionException(e);
        }

        return map;
    }

    /**
     * 解析Json字符串为指定的对象类型
     * 
     * @param value
     * @param t
     * @return
     * @author Heller.Zhang
     * @since 2019年5月7日 上午9:14:07
     */
    public static <T> T readValueCustom(String value, Class<T> t) {
        T map = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            map = mapper.readValue(value, t);
        } catch (Exception e) {
            ReflectionUtils.handleReflectionException(e);
        }

        return map;
    }

}
