/**
 * $Id:$
 * Copyright 2019-2019 ShiJiaZhuang ShanZhouWangLuo Technology Company Ltd. All rights reserved.
 */
package com.hzsparrow.framework.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 执行命令工具类
 * 
 * @author Mia.Guo
 * @since 2019年6月28日 下午3:50:14
 */
public class CMDUtil {

    /**
     * 执行一个cmd命令
     * 
     * @param cmdCommand cmd命令
     * @return 命令执行结果字符串，如出现异常返回null
     */
    public static String excuteCMDCommand(String cmdCommand) {
        StringBuilder stringBuilder = new StringBuilder();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmdCommand);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 执行bat文件，
     * 
     * @param file bat文件路径
     * @param isCloseWindow 执行完毕后是否关闭cmd窗口
     * @return bat文件输出log
     */
    public static String excuteBatFile(String file, boolean isCloseWindow) {
        String cmdCommand = null;
        if (isCloseWindow) {
            cmdCommand = "cmd.exe /c " + file;
        } else {
            cmdCommand = "cmd.exe /k " + file;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmdCommand);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 执行文件命令（Windows、Linux都可以）
     * 
     * @param file
     * @return
     * @author Mia.Guo
     * @since 2019年6月28日 下午4:12:06
     */
    public static String excuteFile(String file) {
        String cmdCommand = file;
        StringBuilder stringBuilder = new StringBuilder();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmdCommand);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 执行bat文件,新开窗口
     * 
     * @param file bat文件路径
     * @param isCloseWindow 执行完毕后是否关闭cmd窗口
     * @return bat文件输出log
     */
    public static String excuteBatFileWithNewWindow(String file, boolean isCloseWindow) {
        String cmdCommand = null;
        if (isCloseWindow) {
            cmdCommand = "cmd.exe /c start" + file;
        } else {
            cmdCommand = "cmd.exe /k start" + file;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmdCommand);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 一次性执行多条linux命令
     * 
     * @param commands
     * @return
     * @author Heller.Zhang
     * @since 2019年7月15日 上午8:57:50
     */
    public List<String> executeLinuxCmdList(List<String> commands) {
        List<String> rspList = new ArrayList<String>();
        Runtime run = Runtime.getRuntime();
        try {
            Process proc = run.exec("/bin/bash", null, null);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true);
            for (String line : commands) {
                out.println(line);
            }
            out.println("exit");// 这个命令必须执行，否则in流不结束。
            String rspLine = "";
            while ((rspLine = in.readLine()) != null) {
                System.out.println(rspLine);
                rspList.add(rspLine);
            }
            proc.waitFor();
            in.close();
            out.close();
            proc.destroy();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return rspList;
    }

    public static void main(String[] args) {
        String shellFile = "/szwlWeb/javacode/reload.sh";
        //String shellFile = "/szwlWeb/Tomcat8-ct/bin/startup.sh";
        //String shellFile = "D:\\a.bat";
        CMDUtil.excuteFile(shellFile);
    }
}
