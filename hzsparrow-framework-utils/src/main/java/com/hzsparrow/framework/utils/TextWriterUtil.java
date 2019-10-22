/**
 * $Id:$
 * Copyright 2019-2019 ShiJiaZhuang ShanZhouWangLuo Technology Company Ltd. All rights reserved.
 */
package com.hzsparrow.framework.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

/**
 * 
 * @author Einstein.Liu
 * @since 2019年7月11日 下午6:55:02
 */
public class TextWriterUtil {

    /**
     * 将字符串写入到文件中，此方法将覆盖原文件内容
     * 
     * @param file
     * @param text
     * @return
     */
    @SuppressWarnings("resource")
    public static boolean wirteLine(String file, String text) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));
            writer.write(text);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 写入UTF8格式的内容到文件，此方法将覆盖原文件内容
     * 
     * @param filePath
     * @param text
     * @return
     */
    public static boolean wirteLineUTF8(String filePath, String text) {
        try {
            File file = new File(filePath);
            File root = new File(file.getParent());
            if (!root.exists()) {
                root.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(text.getBytes("UTF-8"));
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 将字符串写入到文件中，此方法将覆盖原文件内容
     * 
     * @param file
     * @param text
     * @return
     */
    public static boolean wirteLine(FileOutputStream fos, String text) {
        try {
            fos.write(text.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
