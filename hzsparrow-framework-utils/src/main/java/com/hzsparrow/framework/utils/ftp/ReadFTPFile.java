/**
 * $Id:$
 * Copyright 2019 HellerZhang. All rights reserved.
 */
package com.hzsparrow.framework.utils.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

/**
 * FTP 工具类 从FTP服务器读取文件
 * 
 * @author leon
 * @since 2017年3月30日 上午10:30:00
 */
public class ReadFTPFile {

    private Logger logger = LoggerFactory.getLogger(ReadFTPFile.class);

    private FTPClient ftpClient;

    /**
     * ftp地址
     */
    private String ftpHost = "";

    /**
     * ftp端口
     */
    private Integer ftpPort = 21;

    /**
     * ftp账号
     */
    private String ftpUserName = "";

    /**
     * ftp密码
     */
    private String ftpPassword = "";

    private void openConnect() throws FTPException {

        try {
            ftpClient = FTPUtil.getFTPClient(ftpHost, ftpPort, ftpUserName, ftpPassword);
        } catch (Exception e) {
            throw new FTPException("打开FTP链接失败，请检查ftp配置", e);
        }

    }

    private void disConnect() throws FTPException {
        try {
            if (ftpClient != null) {
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            throw new FTPException("上传链接关闭失败，请检查ftp配置", e);
        }
    }

    /**
     * 去 服务器的FTP路径下上读取文件
     * 
     * @param ftpPath
     * @return InputStream
     * @author EmilyWang
     * @since 2017年10月25日 下午2:43:08
     */
    public InputStream readFileForFTP(String ftpPath) {
        String ftpFolderPath = ftpPath.substring(0, ftpPath.lastIndexOf(FTPUtil.strSeparator));
        String ftpFileName = ftpPath.substring(ftpPath.lastIndexOf(FTPUtil.strSeparator) + 1, ftpPath.length());
        if (logger.isDebugEnabled()) {
            logger.debug("========================ftpFolderPath:{}===========================", ftpFolderPath);
            logger.debug("========================ftpFileName:{}===========================", ftpFileName);
        }
        return readFileForFTP(ftpFolderPath, ftpFileName);
    }

    /**
     * 去 服务器的FTP路径下上读取文件
     *
     * @return
     */
    private InputStream readFileForFTP(String strFtpPath, String fileName) {

        logger.info("strFtpPath=" + strFtpPath);
        logger.info("fileName=" + fileName);
        fileName = fileName.replace(File.separator, FTPUtil.strSeparator);
        strFtpPath = strFtpPath.replace(File.separator, FTPUtil.strSeparator);

        if (fileName.contains(FTPUtil.strSeparator)) {
            fileName = fileName.substring(fileName.lastIndexOf(FTPUtil.strSeparator) + 1);
        }

        logger.info("开始读取相对路径" + strFtpPath + "文件!");
        try {
            openConnect();
            // strFtpPath = new
            // String(strFtpPath.getBytes("UTF-8"),"iso-8859-1");
            // fileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(strFtpPath);
            return ftpClient.retrieveFileStream(fileName);
        } catch (FileNotFoundException e) {
            logger.error("没有找到" + strFtpPath + "文件");
            throw new FTPException("没有找到" + strFtpPath + "文件", e);
        } catch (SocketException e) {
            logger.error("连接FTP失败,FTP的IP地址可能错误。");
            throw new FTPException("连接FTP失败,FTP的IP地址可能错误，请检查ftp配置", e);
        } catch (IOException e) {
            logger.error("文件读取错误。");
            throw new FTPException("文件读取错误。，请检查ftp配置", e);
        } catch (Exception e) {
            logger.error("连接FTP失败,FTP的端口错误。");
            throw new FTPException("连接FTP失败,FTP的端口错误，请检查ftp配置", e);
        }finally {
            disConnect();
        }
    }

    public String getFtpHost() {
        return ftpHost;
    }

    public void setFtpHost(String ftpHost) {
        this.ftpHost = ftpHost;
    }

    public Integer getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(Integer ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getFtpUserName() {
        return ftpUserName;
    }

    public void setFtpUserName(String ftpUserName) {
        this.ftpUserName = ftpUserName;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }
}
