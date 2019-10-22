/**
 * $Id:$
 * Copyright 2019-2019 ShiJiaZhuang ShanZhouWangLuo Technology Company Ltd. All rights reserved.
 */
package com.hzsparrow.framework.utils;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 
 * @author Heller.Zhang
 * @since 2019年7月1日 下午7:55:20
 */
public class Zip4AntUtils {

    /**
     * @param inputFileName 输入一个文件夹
     * @param zipFileName 输出一个压缩文件夹，打包后文件名字
     * @throws Exception
     */
    public static OutputStream zip(String inputFileName, String zipFileName) throws Exception {
        return zip(zipFileName, new File(inputFileName));
    }

    private static OutputStream zip(String zipFileName, File inputFile) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        out.setEncoding("UTF-8");//解决linux乱码  根据linux系统的实际编码设置,可以使用命令 vi/etc/sysconfig/i18n 查看linux的系统编码
        zip(out, inputFile, "");
        out.close();
        return out;
    }

    private static void zip(ZipOutputStream out, File f, String base) throws Exception {
        if (f.isDirectory()) { // 判断是否为目录
            File[] fl = f.listFiles();
            // out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
            //            out.putNextEntry(new ZipEntry(base + "/"));
            ZipEntry zipEntry = new ZipEntry(base + System.getProperties().getProperty("file.separator"));
            zipEntry.setUnixMode(755);//解决linux乱码
            out.putNextEntry(zipEntry);
            //            base = base.length() == 0 ? "" : base + "/";
            base = base.length() == 0 ? "" : base + System.getProperties().getProperty("file.separator");
            for (int i = 0; i < fl.length; i++) {
                zip(out, fl[i], base + fl[i].getName());
            }
        } else { // 压缩目录中的所有文件
                 // out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
            ZipEntry zipEntry = new ZipEntry(base);
            zipEntry.setUnixMode(644);//解决linux乱码
            out.putNextEntry(zipEntry);
            FileInputStream in = new FileInputStream(f);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            in.close();
        }
    }

    public static void unzip(String sourceZip, String destDir) throws Exception {

        Project p = new Project();

        Expand e = new Expand();

        e.setProject(p);

        e.setSrc(new File(sourceZip));

        e.setOverwrite(false);

        e.setDest(new File(destDir));

        /* 
        
        ant下的zip工具默认压缩编码为UTF-8编码， 
        
        而winRAR软件压缩是用的windows默认的GBK或者GB2312编码 
        
        所以解压缩时要制定编码格式 
        
        */

        e.setEncoding("UTF-8"); //根据linux系统的实际编码设置

        e.execute();

    }
}
