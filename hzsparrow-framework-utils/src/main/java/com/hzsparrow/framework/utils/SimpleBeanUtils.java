/*
 * $Id:$
 * Copyright 2017 ecarpo.com All rights reserved.
 */
package com.hzsparrow.framework.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.springframework.util.ReflectionUtils;

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
     * @param ex the reflection exception to handle
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
     * @param orig Origin bean whose properties are retrieved
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
