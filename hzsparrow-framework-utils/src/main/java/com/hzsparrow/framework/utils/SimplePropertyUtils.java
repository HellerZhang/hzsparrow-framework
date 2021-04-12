/*
 * $Id:$
 * Copyright 2017 ecarpo.com All rights reserved.
 */
package com.hzsparrow.framework.utils;

import org.apache.commons.beanutils.BeanIntrospector;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Apache PropertyUtils的等价类，只是将check exception改为uncheck exception
 * 
 * @author badqiu
 * @author HellerZhang
 * @since 2014.06.25
 */
public class SimplePropertyUtils {

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
    public static void clearDescriptors() {
        PropertyUtils.clearDescriptors();
    }

    /*
     * (non-Javadoc)
     */
    public static void resetBeanIntrospectors() {
        PropertyUtils.resetBeanIntrospectors();
    }

    /*
     * (non-Javadoc)
     */
    public static void addBeanIntrospector(final BeanIntrospector introspector) {
        PropertyUtils.addBeanIntrospector(introspector);
    }

    /*
     * (non-Javadoc)
     */
    public static boolean removeBeanIntrospector(final BeanIntrospector introspector) {
        return PropertyUtils.removeBeanIntrospector(introspector);
    }

    /*
     * (non-Javadoc)
     */
    public static void copyProperties(final Object dest, final Object orig) {
        try {
            PropertyUtils.copyProperties(dest, orig);
        } catch (Exception e) {
            handleReflectionException(e);
        }
    }

    /*
     * (non-Javadoc)
     */
    public static Map<String, Object> describe(final Object bean) {
        try {
            return PropertyUtils.describe(bean);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     */
    public static Object getIndexedProperty(final Object bean, final String name) {
        try {
            return PropertyUtils.getIndexedProperty(bean, name);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     */
    public static Object getIndexedProperty(final Object bean, final String name, final int index) {
        try {
            return PropertyUtils.getIndexedProperty(bean, name, index);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     */
    public static Object getMappedProperty(final Object bean, final String name) {
        try {
            return PropertyUtils.getMappedProperty(bean, name);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     */
    public static Object getMappedProperty(final Object bean, final String name, final String key) {
        try {
            return PropertyUtils.getMappedProperty(bean, name, key);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     */
    public static Object getNestedProperty(final Object bean, final String name) {
        try {
            return PropertyUtils.getNestedProperty(bean, name);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     */
    public static Object getProperty(final Object bean, final String name) {
        try {
            return PropertyUtils.getProperty(bean, name);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     */
    public static PropertyDescriptor getPropertyDescriptor(final Object bean, final String name) {
        try {
            return PropertyUtils.getPropertyDescriptor(bean, name);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     */
    public static PropertyDescriptor[] getPropertyDescriptors(final Class<?> beanClass) {
        return PropertyUtils.getPropertyDescriptors(beanClass);
    }

    /*
     * (non-Javadoc)
     */
    public static PropertyDescriptor[] getPropertyDescriptors(final Object bean) {
        return PropertyUtils.getPropertyDescriptors(bean);
    }

    /*
     * (non-Javadoc)
     */
    public static Class<?> getPropertyEditorClass(final Object bean, final String name) {
        try {
            return PropertyUtils.getPropertyEditorClass(bean, name);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     */
    public static Class<?> getPropertyType(final Object bean, final String name) {
        try {
            return PropertyUtils.getPropertyType(bean, name);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     */
    public static Method getReadMethod(final PropertyDescriptor descriptor) {
        return PropertyUtils.getReadMethod(descriptor);
    }

    /*
     * (non-Javadoc)
     */
    public static Object getSimpleProperty(final Object bean, final String name) {
        try {
            return PropertyUtils.getSimpleProperty(bean, name);
        } catch (Exception e) {
            handleReflectionException(e);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     */
    public static Method getWriteMethod(final PropertyDescriptor descriptor) {
        return PropertyUtils.getWriteMethod(descriptor);
    }

    /*
     * (non-Javadoc)
     */
    public static boolean isReadable(final Object bean, final String name) {
        return PropertyUtils.isReadable(bean, name);
    }

    /*
     * (non-Javadoc)
     */
    public static boolean isWriteable(final Object bean, final String name) {
        return PropertyUtils.isWriteable(bean, name);
    }

    /*
     * (non-Javadoc)
     */
    public static void setIndexedProperty(final Object bean, final String name, final Object value) {
        try {
            PropertyUtils.setIndexedProperty(bean, name, value);
        } catch (Exception e) {
            handleReflectionException(e);
        }
    }

    /*
     * (non-Javadoc)
     */
    public static void setIndexedProperty(final Object bean, final String name, final int index, final Object value) {
        try {
            PropertyUtils.setIndexedProperty(bean, name, index, value);
        } catch (Exception e) {
            handleReflectionException(e);
        }
    }

    /*
     * (non-Javadoc)
     */
    public static void setMappedProperty(final Object bean, final String name, final Object value) {
        try {
            PropertyUtils.setMappedProperty(bean, name, value);
        } catch (Exception e) {
            handleReflectionException(e);
        }
    }

    /*
     * (non-Javadoc)
     */
    public static void setMappedProperty(final Object bean, final String name, final String key, final Object value) {
        try {
            PropertyUtils.setMappedProperty(bean, name, key, value);
        } catch (Exception e) {
            handleReflectionException(e);
        }
    }

    /*
     * (non-Javadoc)
     */
    public static void setNestedProperty(final Object bean, final String name, final Object value) {
        try {
            PropertyUtils.setNestedProperty(bean, name, value);
        } catch (Exception e) {
            handleReflectionException(e);
        }
    }

    /*
     * (non-Javadoc)
     */
    public static void setProperty(final Object bean, final String name, final Object value) {
        try {
            PropertyUtils.setProperty(bean, name, value);
        } catch (Exception e) {
            handleReflectionException(e);
        }
    }

    /*
     * (non-Javadoc)
     */
    public static void setSimpleProperty(final Object bean, final String name, final Object value) {
        try {
            PropertyUtils.setSimpleProperty(bean, name, value);
        } catch (Exception e) {
            handleReflectionException(e);
        }
    }

}
