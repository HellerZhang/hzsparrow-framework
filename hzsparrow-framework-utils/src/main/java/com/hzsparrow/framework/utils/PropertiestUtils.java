/**
 * $Id:$
 * Copyright 2019-2019 ShiJiaZhuang ShanZhouWangLuo Technology Company Ltd. All rights reserved.
 */
package com.hzsparrow.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * @author Heller.Zhang
 * @since 2019年8月9日 下午2:22:54
 */
public class PropertiestUtils {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 读取配置文件中的配置信息
     * 
     * @param fileName
     * @param properties
     * @return
     * @author Heller.Zhang
     * @since 2019年8月9日 下午2:28:32
     */
    public String[] readProperties(String fileName, String[] properties) {
        Properties property = new Properties();
        BufferedInputStream inBuff = null;
        String[] result = new String[properties.length];
        try {

            inBuff = new BufferedInputStream(new FileInputStream(FileUtils.getResourceFile(fileName)));
            property.load(inBuff);
            for (int i = 0; i < properties.length; i++) {
                result[i] = property.getProperty(properties[i]);
            }
        } catch (FileNotFoundException e1) {
            logger.debug("配置文件 " + fileName + " 不存在！");
        } catch (IOException e) {
            logger.debug("配置文件 " + fileName + " 无法读取！");
        }
        return result;
    }
}
