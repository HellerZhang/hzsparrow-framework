/**
 * $Id:$
 * Copyright 2019-2019 ShiJiaZhuang ShanZhouWangLuo Technology Company Ltd. All rights reserved.
 */
package com.hzsparrow.framework.utils;

import java.io.*;

/**
 * @author Einstein.Liu
 * @since 2019年7月11日 下午6:48:54
 */
public class TextReaderUtil {

    public static void main(String[] args) {
        String path = "C:\\Users\\Administrator\\Desktop\\账号密码.txt";
        String content = readAll(path);
        System.out.println(content);
    }

    /**
     * 读取一行
     *
     * @param file
     * @return
     */
    public static String readLine(String file) {
        String readLine = "";
        try {
            readLine = readLine(new BufferedReader(new FileReader(new File(file))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readLine;
    }

    /**
     * 读取一行
     *
     * @param file
     * @return
     */
    public static String readLine(BufferedReader file) {
        String readLine = "";
        try {
            readLine = file.readLine();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readLine;
    }

    /**
     * 读取全部内容
     *
     * @param file
     * @return
     */
    @SuppressWarnings("resource")
    public static String readAll(String file) {
        String readLine = "";
        try {
            BufferedReader bufferFile = new BufferedReader(new FileReader(new File(file)));
            String line = null;
            boolean isFirstLine = true;
            while ((line = bufferFile.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                } else {
                    readLine += "\n";
                }
                readLine += line;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readLine;
    }

}
