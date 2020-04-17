/*
 * $Id:$
 * Copyright 2017 ecarpo.com All rights reserved.
 */
package com.hzsparrow.framework.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Apache BeanUtils的等价类，只是将check exception改为uncheck exception
 *
 * @author badqiu
 * @author Jades.He
 * @since 2014.06.25
 */
public class SimpleBeanUtils {

    static {
        ConvertUtils.register(new DateConverter(null), java.util.Date.class);
        ConvertUtils.register(new SqlDateConverter(null), java.sql.Date.class);
    }

    /**
     * Handle the given reflection exception.
     *
     * @param e the reflection exception to handle
     */
    private static void handleReflectionException(Exception e) {
        ReflectionUtils.handleReflectionException(e);
    }

    /*
     * (non-Javadoc)
     */
    public static Object cloneBean(final Object bean) {
        try {
            return BeanUtils.cloneBean(bean);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    /**
     * Copy property values from the origin bean to the instance of the destination class for all
     * cases where the property names are the same.
     *
     * @param destClass Destination class
     * @param orig      Origin bean whose properties are retrieved
     * @return instance of the destination class
     * @author Jades.He
     */
    @SuppressWarnings("unchecked")
    public static <T> T copyProperties(Class<T> destClass, Object orig) {
        if (orig == null) {
            return null;
        }
        try {
            Object target = destClass.newInstance();
            copyProperties(target, orig);
            return (T) target;
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     */
    public static void copyProperties(final Object dest, final Object orig) {
        if (orig == null) {
            return;
        }
        try {
            BeanUtils.copyProperties(dest, orig);
        } catch (Exception e) {
            handleReflectionException(e);
        }
    }

    /*
     * (non-Javadoc)
     */
    public static void copyProperty(final Object bean, final String name, final Object value) {
        try {
            BeanUtils.copyProperty(bean, name, value);
        } catch (Exception e) {
            handleReflectionException(e);
        }
    }

    /*
     * (non-Javadoc)
     */
    public static Map<String, String> describe(final Object bean) {
        try {
            return BeanUtils.describe(bean);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     */
    public static String[] getArrayProperty(final Object bean, final String name) {
        try {
            return BeanUtils.getArrayProperty(bean, name);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     */
    public static String getIndexedProperty(final Object bean, final String name) {
        try {
            return BeanUtils.getIndexedProperty(bean, name);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     */
    public static String getIndexedProperty(final Object bean, final String name, final int index) {
        try {
            return BeanUtils.getIndexedProperty(bean, name, index);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     */
    public static String getMappedProperty(final Object bean, final String name) {
        try {
            return BeanUtils.getMappedProperty(bean, name);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     */
    public static String getMappedProperty(final Object bean, final String name, final String key) {
        try {
            return BeanUtils.getMappedProperty(bean, name, key);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     */
    public static String getNestedProperty(final Object bean, final String name) {
        try {
            return BeanUtils.getNestedProperty(bean, name);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     */
    public static String getProperty(final Object bean, final String name) {
        try {
            return BeanUtils.getProperty(bean, name);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     */
    public static String getSimpleProperty(final Object bean, final String name) {
        try {
            return BeanUtils.getSimpleProperty(bean, name);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     */
    public static void populate(final Object bean, final Map<String, ? extends Object> properties) {
        try {
            BeanUtils.populate(bean, properties);
        } catch (Exception e) {
            handleReflectionException(e);
        }
    }

    /*
     * (non-Javadoc)
     */
    public static void setProperty(final Object bean, final String name, final Object value) {
        try {
            BeanUtils.setProperty(bean, name, value);
        } catch (Exception e) {
            handleReflectionException(e);
        }
    }

    public static <T> List<T> cloneList(Class<T> type, List list) throws Exception {
        List<T> listResult = null;
        if (CollectionUtils.isNotEmpty(list)) {
            listResult = new ArrayList<>();
            for (Object u : list) {
                T modelBO = type.newInstance();
                BeanUtils.copyProperties(modelBO, u);
                listResult.add(modelBO);
            }
        }
        return listResult;
    }

    public static <T> T cloneBean(Class<T> type, Object bo) throws Exception {
        T result = null;
        if (bo != null) {
            result = type.newInstance();
            BeanUtils.copyProperties(result, bo);
        }
        return result;
    }

    public static Map<String, Object> bean2map(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    //if ( value != null ) {
                    map.put(key, value);
                    //}
                }
            }
        } catch (Exception e) {
            //System.out.println("transBean2Map Error " + e);
            throw new Exception(e);
        }
        return map;
    }

    public static <T> T map2bean(Class<T> type, Map<String, Object> map) throws Exception {
        T result = null;
        if (map != null) {
            result = type.newInstance();
            SimpleBeanUtils.populate(result, map);
        }
        return result;
    }

    /*
     * (non-Javadoc)
     */
    public static boolean initCause(final Throwable throwable, final Throwable cause) {
        return BeanUtils.initCause(throwable, cause);
    }

    /*
     * (non-Javadoc)
     */
    public static <K, V> Map<K, V> createCache() {
        return BeanUtils.createCache();
    }

    /*
     * (non-Javadoc)
     */
    public static boolean getCacheFast(final Map<?, ?> map) {
        return BeanUtils.getCacheFast(map);
    }

    /*
     * (non-Javadoc)
     */
    public static void setCacheFast(final Map<?, ?> map, final boolean fast) {
        BeanUtils.setCacheFast(map, fast);
    }

}
